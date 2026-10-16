package org.example;

import java.util.HashMap;
import java.util.Map;

public class ParseCmd {
    String rawCmd;
    String prefix;
    String suffix;
    Map<String,String> param = new HashMap<>();

    public ParseCmd(String cmd) {
        this.rawCmd = cmd;

        String[] cmdParts = cmd.split("\\?");
        prefix = cmdParts[0].trim();
        suffix = cmdParts.length > 1 ? cmdParts[1].trim() : "";

        String[] query = suffix.split("&");
        for (String q : query) {
            String [] parts = q.split("=");
            if (parts.length > 1) {
                param.put(parts[0], parts[1]);
            }
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public String getRawCmd() {
        return rawCmd;
    }

    public Command getCommand() {
        Command command = Command.ERROR;
        switch (prefix) {
            case "종료":
                return Command.EXIT;
            case "등록":
                return Command.REGISTER;
            case "목록":
                return Command.LIST;
            case "삭제":
                command = Command.DELETE;
                command.id = Integer.parseInt(param.get("id"));
                return command;
            case "수정":
                command = Command.UPDATE;
                command.id = Integer.parseInt(param.get("id"));
                return command;
            case "빌드":
                return Command.BUILD;
            default:
                return Command.ERROR;
        }
    }

    public String getParam(String key) {
        return param.get(key);
    }
}