package org.example;

import org.example.domain.system.SystemController;
import org.example.domain.wiseSaying.controller.WiseSayingController;

import java.util.Scanner;

public class App {
    public static void run() {
        SystemController sysController = new SystemController();
        WiseSayingController wsController = new WiseSayingController(sysController.sc);

        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            ParseCmd cmd = sysController.getCommand();
            switch (cmd.getCommand()) {
                case EXIT:
                    return;
                case REGISTER:
                    wsController.register();
                    break;
                case LIST:
                    wsController.printList();
                    break;
                case DELETE:
                    wsController.delete(parseId(cmd.getParam("id").trim()));
                    break;
                case UPDATE:
                    wsController.update(parseId(cmd.getParam("id").trim()));
                    break;
                case BUILD:
                    wsController.build();
                    break;
                case SEARCH:
                    KeywordType type = KeywordType.create(cmd.getParam("keywordType").trim());
                    String keyword = cmd.getParam("keyword").trim();
                    wsController.search(type, keyword);
                    break;
                case ERROR:
                    break;
            }
        } //while

    }

    static int parseId(String rawId) {
        int id = -1;
        if (rawId.isEmpty()) return id;

        try {
            id = Integer.parseInt(rawId);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }

        return id;
    }
}
