package org.example.domain.system;

import org.example.Command;
import org.example.ParseCmd;
import org.example.domain.wiseSaying.service.WiseSayingService;

import java.util.Scanner;

public class SystemController {
    public Scanner sc = new Scanner(System.in);

    public ParseCmd getCommand() {
        String cmd = sc.nextLine().trim();
        return new ParseCmd(cmd);
//        ParseCmd parse = new ParseCmd(cmd);
//        return parse.getCommand();
    }
}
