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
            Command command = sysController.getCommand();
            switch (command) {
                case EXIT:
                    return;
                case REGISTER:
                    wsController.register();
                    break;
                case LIST:
                    wsController.printList();
                    break;
                case DELETE:
                    wsController.delete(command.id);
                    break;
                case UPDATE:
                    wsController.update(command.id);
                    break;
                case BUILD:
                    wsController.build();
                    break;
//                case ALL_CLEAR:
//                    wsController.allClear();
//                    break;
                case ERROR:
                    break;
            }
        } //while
    }
}
