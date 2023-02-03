package client;

import com.beust.jcommander.Parameter;
import common.COMMANDS;

public class CommandLine {
        @Parameter(names = "-t", description = "Type of Request")
        private String type;

        @Parameter(names = "-i", description = "Index of the cell")
        private String index;

        @Parameter(names = "-m", description = "Value to save in database")
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
}
