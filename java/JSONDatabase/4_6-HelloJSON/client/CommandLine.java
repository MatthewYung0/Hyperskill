package client;

import com.beust.jcommander.Parameter;
import common.COMMANDS;

public class CommandLine {

    public static final String getType = "-t";
    public static final String indexType = "-k";
    public static final String valueType = "-v";

    @Parameter(names = getType, description = "Type of Request")
    private String type;

    @Parameter(names = indexType, description = "Key")
    private String index;

    @Parameter(names = valueType, description = "Value")
    private String data;

    public String getInput() {
        String type_U = type.toUpperCase();
        if (type_U.equals(COMMANDS.SET.toString())) {
            return type + " " + index + " " + data;
        } else if (type_U.equals(COMMANDS.DELETE.toString()) || type_U.equals(COMMANDS.GET.toString())) {
            return type + " " + index;
        } else if (type_U.equals(COMMANDS.EXIT.toString())) {
            return COMMANDS.EXIT.toString();
        } else {
            return null;
        }
    }

    public String getType() {
        return type;
    }

    public String getIndex() {
        return index;
    }

    public String getData() {
        return data;
    }
}
