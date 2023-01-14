package tracker;

import java.util.HashMap;

public enum STATISTIC_COMMANDS {
    JAVA("JAVA"),
    DSA("DSA"),
    DATABASES("DATABASES"),
    SPRING("SPRING");

    private final String course;

    STATISTIC_COMMANDS(String course) {
        this.course = course;
    }


    @Override
    public String toString() {
        return course;
    }

    static final HashMap<String, STATISTIC_COMMANDS> commandMap = new HashMap<>();
    static {
        commandMap.put("JAVA", JAVA);
        commandMap.put("DSA", DSA);
        commandMap.put("DATABASES", DATABASES);
        commandMap.put("SPRING", SPRING);
    }

    public static STATISTIC_COMMANDS findByName(String name) {
        for (STATISTIC_COMMANDS command : STATISTIC_COMMANDS.class.getEnumConstants()) {
            if (command.toString().equals(name)) {
                return command;
            }
        }
        return null;
    }

}
