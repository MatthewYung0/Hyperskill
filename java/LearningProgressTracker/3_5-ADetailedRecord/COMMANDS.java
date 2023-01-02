package tracker;

import java.util.HashMap;

public enum COMMANDS {
    ADD_STUDENT,
    ADD_POINTS,
    FIND,
    LIST,
    UNKNOWN;

    static final HashMap<String, COMMANDS> commandMap = new HashMap<>();
    static {
        commandMap.put("add students", ADD_STUDENT);
        commandMap.put("add points", ADD_POINTS);
        commandMap.put("find", FIND);
        commandMap.put("list", LIST);
        commandMap.put("unknown", UNKNOWN);
    }
}
