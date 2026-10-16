package org.example.domain.wiseSaying.repository;

import org.example.domain.wiseSaying.entity.WiseSaying;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class WiseSayingRepository {
    private final String basePath = System.getProperty("user.dir") + "/db/wiseSaying/";

    private void writeFileForm(String filename, WiseSaying form) throws IOException {
        File folder = new File(basePath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(basePath + filename);
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(basePath + filename);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(form.toString());

        fw.close();
        pw.close();
    }

    public int writeLastId(int id) throws IOException {
        if (id < 1) {
            System.out.println("잘못된 아이디가 입력되었습니다.");
            return -1;
        }

        File folder = new File(basePath);
        File file = new File(basePath + "lastId.txt");

        if (!folder.exists()) {
            folder.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);
        pw.println(id);

        fw.close();
        pw.close();

        return id;
    }

    public void write(int id, WiseSaying newForm) throws IOException {
        writeFileForm(id + ".json", newForm);
        writeLastId(id);
    }

    public int getLastId() throws IOException{
        File file = new File(basePath + "lastId.txt");
        if (!file.exists()) {
            return 0;
        }

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine().trim();

        // TODO: id int 검사
        int lastId = Integer.parseInt(line);

        fr.close();
        br.close();

        return lastId;
    }

    public Map<Integer, WiseSaying> getSavedForms() throws IOException{
        File folder = new File(basePath);
        File[] files = folder.listFiles();
        Map<Integer, WiseSaying> map = new HashMap<>();

        if (files != null) {
            for (File file : files) {
                if (!file.isFile() || !file.getName().endsWith(".json")) {
                    continue;
                }
                if (file.getName().startsWith("data")) {
                    continue;
                }

                WiseSaying form = readFileForm(file);
                map.put(form.getId(), form);
            }// for
        }// files not null

        return map;
    }

    private WiseSaying readFileForm(File file) throws IOException{
        StringBuilder result = new StringBuilder();
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String line = "";
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        String rawForm = result.toString() ;
        String[] splited = rawForm.split("/");

        if (splited.length != 3) {
            System.out.println("파일 형식이 정상적이지 않습니다.");
            return new WiseSaying(-1);
        }
        // TODO: id가 int인지 검사?

        int id = Integer.parseInt(splited[0].trim());
        WiseSaying form = new WiseSaying(id, splited[1].trim(), splited[2].trim());

        fr.close();
        br.close();

        return form;
    }

    public void delete(String filename) {
        File file = new File(basePath + filename);
        if (!file.exists()) return;
        file.delete();
    }

    public void update(int id, WiseSaying newForm) throws IOException {
        String filename = id + ".json";
        writeFileForm(filename, newForm);
    }

    public void build(Map<Integer, WiseSaying> map) throws IOException {
        File file = new File(basePath + "data.json");
        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);

        WiseSaying[] formArr = map.values().toArray(new WiseSaying[0]);
        Arrays.sort(formArr, new Comparator<WiseSaying>() {
            @Override
            public int compare(WiseSaying o1, WiseSaying o2) {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });

        pw.println("[");

        int index = 0;
        for (WiseSaying form : formArr) {
            index++;

            pw.println("\t{");
            pw.println("\t\t\"id\": " + form.getId() + ",");
            pw.println("\t\t\"content\": \"" + form.getText() + "\",");
            pw.println("\t\t\"author\": \"" + form.getAuth() + "\"");

            if (index < formArr.length) {
                pw.println("\t},");
            } else {
                pw.println("\t}");
            }
        }

        pw.println("]");

        pw.close();
        fw.close();
    }

    public void allClear() {
        File folder = new File(basePath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                file.delete();
            }// for
        }// files not null
    }
}
