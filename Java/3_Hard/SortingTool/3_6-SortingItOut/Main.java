package sorting;

import java.util.*;

public class Main {
    static ArrayList<String> list = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static String datatype;

    public static void main(final String[] args) {
        setDataTypeToProcess(args);
        while (scanner.hasNextLine()) {
            String text = scanner.nextLine();
            list.add(text);
        }
        processData();
    }

    public static void processData() {
        int frequency;
        int percent;
        switch (datatype) {
            case "long":
                ArrayList<Long> longList = createListLong();
                long totalNumbersLong = longList.size();
                long greatestNumber = Collections.max(longList);
                frequency = Collections.frequency(longList, greatestNumber);
                percent = (int) (frequency * 100 / totalNumbersLong);
                System.out.println("Total numbers: " + totalNumbersLong);
                System.out.println("The greatest number: " + greatestNumber + " (" + frequency + " time(s), " + percent + "%).");
                break;

            case "sortIntegers":
                ArrayList<Long> sortedList = createListLong();
                Collections.sort(sortedList);
                long totalNumbersSortedIntegers = sortedList.size();
                System.out.println("Total numbers: " + totalNumbersSortedIntegers);
                System.out.print("Sorted data: ");
                for (Long number: sortedList) {
                    System.out.print(number + " ");
                }
                System.out.print('\n');
                break;

            case "line":
                int longestStringIndex = 0;
                for (int index = 0; index < list.size(); index++) {
                    if (list.get(index).length() > list.get(longestStringIndex).length()) {
                        longestStringIndex = index;
                    }
                }
                frequency = Collections.frequency(list, list.get(longestStringIndex));
                percent = (frequency * 100 / list.size());
                System.out.println("Total lines: " + list.size());
                System.out.println("The longest line:");
                System.out.println(list.get(longestStringIndex));
                System.out.println("(" + frequency + " time(s), " + percent + "%).");
                break;

            default:
                ArrayList<String> words = new ArrayList<>();
                for (String line: list) {
                    String[] text = line.split(" ");
                    for (int index = 0; index < text.length; index++) {
                        if (!text[index].equals("")) {
                            words.add(text[index]);
                        }
                    }
                }
                String longestWord = Collections.max(words, Comparator.comparing(String::length));
                frequency = Collections.frequency(words, longestWord);
                percent = (frequency * 100 / words.size());
                System.out.println("Total words: " + words.size());
                System.out.println("The longest word: " + longestWord + " (" + frequency + " time(s), " + percent + "%).");
        }
    }

    public static ArrayList<Long> createListLong() {
        ArrayList<Long> longList = new ArrayList<>();
        for (String line: list) {
            String[] text = line.split(" ");
            for (int i = 0; i < text.length; i++) {
                if (!text[i].equals("")) {
                    longList.add(Long.parseLong(text[i]));
                }
            }
        }
        return longList;
    }

    public static void setDataTypeToProcess(String[] args) {
        if (Arrays.asList(args).contains("-sortIntegers")) {
            datatype = "sortIntegers";
        } else if (args.length == 0 || args[1].equals("word")) {
            datatype = "word";
        } else if (args[1].equals("line")) {
            datatype = "line";
        } else if (args[1].equals("long"))  {
            datatype = "long";
        }
    }
}
