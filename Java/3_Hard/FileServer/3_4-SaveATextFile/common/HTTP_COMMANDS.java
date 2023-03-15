package common;

import java.util.HashMap;

public enum HTTP_COMMANDS {
    PUT,
    GET,
    DELETE,
    EXIT;
    static final HashMap<String, HTTP_COMMANDS> commandMap = new HashMap<>();
    static {
        commandMap.put("1", GET);
        commandMap.put("2", PUT);
        commandMap.put("3", DELETE);
        commandMap.put("exit", EXIT);
    }
    public static HTTP_COMMANDS forString(String command) {
        return commandMap.get(command);
    }
}