package tracker;

import java.util.HashMap;

public enum MAIN_COMMANDS {

    ADD_STUDENTS("add students"),
    ADD_POINTS("add points"),
    FIND("find"),
    LIST("list"),
    STATISTICS("statistics");
    private final String command;

    MAIN_COMMANDS(String command) {
        this.command = command;
    }


    @Override
    public String toString() {
        return command;
    }

    static final HashMap<String, MAIN_COMMANDS> mainCommandMap = new HashMap<>();
    static {
        mainCommandMap.put("add students", ADD_STUDENTS);
        mainCommandMap.put("add points", ADD_POINTS);
        mainCommandMap.put("find", FIND);
        mainCommandMap.put("list", LIST);
        mainCommandMap.put("statistics", STATISTICS);
    }

    public static MAIN_COMMANDS findByName(String name) {
        for (MAIN_COMMANDS command : MAIN_COMMANDS.class.getEnumConstants()) {
            if (command.toString().equals(name)) {
                return command;
            }
        }
        return null;
    }



}
