package org.example.domain.system;

import org.example.Command;
import org.example.ParseCmd;
import org.example.domain.wiseSaying.service.WiseSayingService;

import java.util.Scanner;

public class SystemController {
    public Scanner sc = new Scanner(System.in);

    public Command getCommand() {
        String cmd = sc.nextLine().trim();
        ParseCmd parse = new ParseCmd(cmd);

        return parse.getCommand();
        /*
        if (cmd.equals("종료")) {
            return  Command.EXIT;
        } else if (cmd.equals("등록")) {
            return Command.REGISTER;
        } else if (cmd.equals("목록")) {
            return Command.LIST;
        } else if (cmd.startsWith("삭제?id=")) {
            String[] parts = cmd.split("=");
            if (parts.length != 2) {
                System.out.println("잘못된 명령어입니다.");
                return Command.ERROR;
            }

            int id = Integer.parseInt(parts[1]);

            Command command = Command.DELETE;
            command.id = id;
            return command;

        } else if (cmd.startsWith("수정?id=")) {
            String[] parts = cmd.split("=");
            if (parts.length != 2) {
                System.out.println("잘못된 명령어입니다.");
                return Command.ERROR;
            }

            int id = Integer.parseInt(parts[1]);
            Command command = Command.UPDATE;
            command.id = id;
            return command;

        } else if (cmd.equals("빌드")) {
            return Command.BUILD;
        }
//        else if (cmd.equals("all-clear")) {
//            return Command.ALL_CLEAR;
//        }

        return Command.ERROR;

         */
    }
}
