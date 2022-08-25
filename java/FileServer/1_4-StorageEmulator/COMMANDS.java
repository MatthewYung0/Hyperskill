package server;

import java.util.HashMap;

public enum COMMANDS {

    ADD,
    GET,
    DELETE,
    EXIT;

    static final HashMap<String, COMMANDS> commandMap = new HashMap<>();

    static {
        commandMap.put("add", ADD);
        commandMap.put("get", GET);
        commandMap.put("delete", DELETE);
        commandMap.put("exit", EXIT);
    }

    public static COMMANDS forString(String command) {
        return commandMap.get(command);
    }

}
