package tracker;

import java.util.HashMap;

public enum COMMANDS {
    ADD_STUDENT,
    UNKNOWN;

    static final HashMap<String, COMMANDS> commandMap = new HashMap<>();
    static {
        commandMap.put("add students", ADD_STUDENT);
        commandMap.put("unknown", UNKNOWN);
    }
}





