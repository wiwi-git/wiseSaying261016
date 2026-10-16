package org.example.domain.wiseSaying.controller;

import org.example.AppTestRunner;
import org.example.ParseCmd;
import org.example.TestUtil;
import org.example.domain.wiseSaying.repository.WiseSayingRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WiseSayingControllerTest {
    // WARNING: 테스트 시작전 저장된 데이터 삭제됨

    @BeforeEach
    void beforeEach() {
        new WiseSayingController(TestUtil.genScanner("")).allClear();
    }

    @Test
    @DisplayName("등록")
    void register() {
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        WiseSayingController wsController = new WiseSayingController(
                TestUtil.genScanner("""
                현재를 사랑하라.
                작자미상
                """)
        );
        wsController.register();

        TestUtil.clearSetOutToByteArray();

        assertThat(out.toString())
                .contains("명언 :")
                .contains("작가 :")
                .contains("명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("등록과 목록")
    void printList() {
        String rawCmd = """
                현재를 사랑하라.
                작자미상
                """;
        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        Scanner sc = TestUtil.genScanner(rawCmd);
        WiseSayingController wsController = new WiseSayingController(sc);
        wsController.register();
        //-- 등록

        wsController.printList();

        TestUtil.clearSetOutToByteArray();
        assertThat(out.toString())
                .contains("번호 / 작가 / 명언")
                .contains("작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("등록과 삭제")
    void delete() {
        String rawCmd = """
                현재를 사랑하라.
                작자미상
                삭제?id=1
                삭제?id=1
                """;

        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        Scanner sc = TestUtil.genScanner(rawCmd);
        WiseSayingController wsController = new WiseSayingController(sc);
        wsController.register();
        //-- 등록

        String cmd = sc.nextLine();
        ParseCmd parse = new ParseCmd(cmd);

        int id = parse.getCommand().id;

        wsController.delete(id);
        wsController.delete(id);

        TestUtil.clearSetOutToByteArray();

        assertThat(out.toString())
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("등록과 수정")
    void update() {
        String rawCmd = """
                현재를 사랑하라.
                작자미상
                수정?id=1
                과거에 집착하지 마라.
                작자미상
                수정?id=2
                """;

        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        Scanner sc = TestUtil.genScanner(rawCmd);
        WiseSayingController wsController = new WiseSayingController(sc);
        wsController.register();
        //-- 등록


        //-- 수정1
        String cmd = sc.nextLine();
        ParseCmd parse = new ParseCmd(cmd);
        int id = parse.getCommand().id;
        wsController.update(id);

        //-- 수정 실패
        cmd = sc.nextLine();
        parse = new ParseCmd(cmd);
        id = parse.getCommand().id;
        wsController.update(id);

        TestUtil.clearSetOutToByteArray();

        //-- 검사
        assertThat(out.toString())
                .doesNotContain("1번 명언은 존재하지 않습니다.")
                .contains("2번 명언은 존재하지 않습니다.");
    }

    @Test
    void build() {
        String rawCmd = """
                현재를 사랑하라.
                작자미상
                """;

        ByteArrayOutputStream out = TestUtil.setOutToByteArray();
        Scanner sc = TestUtil.genScanner(rawCmd);
        WiseSayingController wsController = new WiseSayingController(sc);
        wsController.register();
        //-- 등록
        wsController.build();

        WiseSayingRepository wsRepo = new WiseSayingRepository();

        TestUtil.clearSetOutToByteArray();

        //-- 검사
        File file = new File(wsRepo.basePath + "data.json");
        assertTrue(file.exists());

        assertThat(out.toString())
                .contains("data.json 파일의 내용이 갱신되었습니다.");


        FileReader fr = null;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = "";
            StringBuilder lines = new StringBuilder();
            while ((line = br.readLine()) != null) {
                lines.append(line);
            }
            assertThat(lines.toString())
                    .contains("\"id\": 1")
                    .contains("\"content\": \"현재를 사랑하라.\",")
                    .contains("\"author\": \"작자미상\"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}