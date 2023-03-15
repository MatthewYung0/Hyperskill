package sorting;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Main {
    static List<String> list = new ArrayList<>();
    static Scanner scanner;
    static String dataType;
    static String sortType;
    static String outputFile = null;
    static String inputFileName = null;

    public static void main(final String[] args) throws IOException {
        if (checkValidArguments(args)) {
            setDataType(args);
            setSortingType(args);
            if (inputFileName != null) {
                File myFile = new File(inputFileName);
                scanner = new Scanner(myFile);
                while (scanner.hasNextLine()) {
                    String textFromFile = scanner.nextLine();
                    list.add(textFromFile);
                }
                scanner.close();
            } else {
                scanner = new Scanner(System.in);
                while (scanner.hasNextLine()) {
                    String textFromUserInput = scanner.nextLine();
                    if (dataType.equals(DATATYPE.NUMBERS.getDataType())) {
                        textFromUserInput = checkValidInput(textFromUserInput);
                    }
                    list.add(textFromUserInput);
                }
            }
            processData();
        }
    }

    public static void processData() throws IOException {
        if (sortType.equals((SORTTYPE.COUNT.getSortingType()))) {
            sortCount();
        } else {
            sortNatural();
        }
    }

    public static void sortNatural() throws IOException {
        if (!dataType.equals(DATATYPE.LINES.getDataType())) {
            List<String> wordList = trimAndSplitArrayList();
            if (dataType.equals(DATATYPE.NUMBERS.getDataType())) {
                List<Long> unsortedLongList = wordList.stream().map(Long::parseLong).sorted().collect(toList());
                printArrayList(unsortedLongList);
            } else {
                Collections.sort(wordList);
                printArrayList(wordList);
            }
        } else {
            Collections.sort(list);
            printArrayList(list);
        }
    }

    public static void sortCount() throws IOException {
        // Checking if the datatype is a Long or Word
        if (!dataType.equals(DATATYPE.LINES.getDataType())) {
            // Creating and adding elements to map.
            Map<String, Integer> unsortedMapWithSplitElements = trimAndSplitHashMap();
            if (dataType.equals(DATATYPE.WORDS.getDataType())) {
                Map<String, Integer> sortedWordsMap = dataWordSortCount(unsortedMapWithSplitElements);
                printMap(sortedWordsMap);
            } else {
                Map<Long, Integer> numbersSortedMap = dataLongSortCount(unsortedMapWithSplitElements);
                printMap(numbersSortedMap);
            }
        } else {
            Map<String, Integer> linesTreeMap = new TreeMap<>();
            for (String line : list) {
                if (!line.equals("")) {
                    linesTreeMap.merge(line, 1, Integer::sum);
                }
            }
            printMap(linesTreeMap);
        }
    }

    public static Map<String, Integer> dataWordSortCount(Map<String, Integer> unsortedMapWithSplitElements) {
        Map<String, Integer> wordsMap = unsortedMapWithSplitElements.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        Comparator<Map.Entry<String, Integer>> comparator = (o1, o2) -> {
            int i = o1.getValue().compareTo(o2.getValue());
            if (i == 0) {
                return o1.getKey().compareTo(o2.getKey());
            } else {
                return i;
            }
        };
        return wordsMap.
                entrySet().
                stream().
                sorted(comparator).
                collect((Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));
    }

    public static Map<Long, Integer> dataLongSortCount(Map<String, Integer> unsortedMapWithSplitElements) {
        Map<Long, Integer> longMap = unsortedMapWithSplitElements.entrySet().stream().collect(Collectors.toMap(e -> Long.parseLong(e.getKey()), Map.Entry::getValue));
        Comparator<Map.Entry<Long, Integer>> comparator = (o1, o2) -> {
            int i = o1.getValue().compareTo(o2.getValue());
            if (i == 0) {
                return o1.getKey().compareTo(o2.getKey());
            } else {
                return i;
            }
        };
        return longMap.
                entrySet().
                stream().
                sorted(comparator).
                collect((Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new)));
    }

    public static Map<String, Integer> trimAndSplitHashMap() {
        Map<String, Integer> unsortedMap = new HashMap<>();
        for (String line : list) {
            // If datatype is numbers or words, split string into elements.
            if (dataType.equals(DATATYPE.NUMBERS.getDataType()) || dataType.equals(DATATYPE.WORDS.getDataType())) {
                String[] elements = line.split(" ");
                for (String element : elements) {
                    if (!element.equals("")) {
                        unsortedMap.merge(element, 1, Integer::sum);
                    }
                }
                // Else check that strings are not empty and add to Map.
            } else {
                if (!line.equals("")) {
                    unsortedMap.merge(line, 1, Integer::sum);
                }
            }
        }
        return unsortedMap;
    }

    public static List<String> trimAndSplitArrayList() {
        ArrayList<String> wordList = new ArrayList<>();
        for (String line : list) {
            // If datatype is numbers or words, split string into elements.
            if (dataType.equals(DATATYPE.NUMBERS.getDataType()) || dataType.equals(DATATYPE.WORDS.getDataType())) {
                String[] elements = line.split(" ");
                for (String element : elements) {
                    if (!element.equals("")) {
                        wordList.add(element);
                    }
                }
                // Else check that strings are not empty and add to Map.
            } else {
                if (!line.equals("")) {
                    wordList.add(line);
                }
            }
        }
        return wordList;
    }

    public static void printMap(Map<?, Integer> map) throws IOException {
        int sum = map.values().stream().mapToInt(Integer::intValue).sum();
        String typeToPrint = "number";
        if (dataType.equals(DATATYPE.WORDS.getDataType())) {
            typeToPrint = DATATYPE.WORDS.getDataType();
        } else if (dataType.equals(DATATYPE.LINES.getDataType())) {
            typeToPrint = DATATYPE.LINES.getDataType();
        }
        if (outputFile != null) {
            PrintWriter writer = new PrintWriter("out.txt", StandardCharsets.UTF_8);
            writer.println("Total " + typeToPrint + "s : " + sum);
            map.forEach((key, value) -> writer.println(key + ": " + value + " time(s), " + ((value * 100) / sum) + "%"));
            writer.close();
        } else {
            System.out.println("Total " + typeToPrint + "s : " + sum);
            map.forEach((key, value) -> System.out.println(key + ": " + value + " time(s), " + ((value * 100) / sum) + "%"));
        }
    }

    public static void printArrayList(List<?> arrayList) throws IOException {
        String typeToPrint = "number";
        if (dataType.equals(DATATYPE.WORDS.getDataType())) {
            typeToPrint = DATATYPE.WORDS.getDataType();
        } else if (dataType.equals(DATATYPE.LINES.getDataType())) {
            typeToPrint = DATATYPE.LINES.getDataType();
        }
        boolean printSpace = true;
        if (outputFile != null) {
            PrintWriter writer = new PrintWriter("out.txt", StandardCharsets.UTF_8);
            writer.println("Total " + typeToPrint + "s : " + arrayList.size());
            writer.println("Sorted data: ");
            for (Object item : arrayList) {
                if (dataType.equals(DATATYPE.LINES.getDataType())) {
                    if (printSpace) {
                        writer.print('\n');
                        printSpace = false;
                    }
                    writer.print(item);
                } else {
                    writer.print(item + " ");
                }
            }
            writer.close();
        } else {
            System.out.println("Total " + typeToPrint + "s : " + arrayList.size());
            System.out.print("Sorted data: ");
            for (Object item : arrayList) {
                if (dataType.equals(DATATYPE.LINES.getDataType())) {
                    if (printSpace) {
                        System.out.print('\n');
                        printSpace = false;
                    }
                    System.out.println(item);
                } else {
                    System.out.print(item + " ");
                }
            }
        }

    }

    public static String checkValidInput(String input) {
        try {
            Long.parseLong(input);
            return input;
        } catch (NumberFormatException inputIsNotLong) {
            String[] inputs = input.split(" ");
            StringBuilder newInput = new StringBuilder();
            for (String element : inputs) {
                if (!element.equals("")) {
                    try {
                        Long.parseLong(element);
                        newInput.append(element).append(" ");
                    } catch (NumberFormatException elementIsNotLong) {
                        System.out.println("\"" + element + "\" is not a long. It will be skipped.");
                    }
                }
            }
            return newInput.toString().trim();
        }
    }

    public static boolean checkValidArguments(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList(args));
        for (int index = 0; index < list.size(); index++) {
            // Checking if data type has been declared
            if (list.get(index).equals(DATATYPE.DATATYPE.getDataType())) {
                if (index == list.size() - 1 ||
                        !list.get(index + 1).equals(DATATYPE.NUMBERS.getDataType()) &&
                                !list.get(index + 1).equals(DATATYPE.WORDS.getDataType()) &&
                                !list.get(index + 1).equals(DATATYPE.LINES.getDataType())) {
                    System.out.println("No data type defined!");
                    return false;
                } else {
                    list.remove(index + 1);
                    list.remove(index);
                    index = -1;
                    continue;
                }
            }
            // Checking if sort type has been declared
            if (list.get(index).equals(SORTTYPE.SORTINGTYPE.getSortingType())) {
                if (index == list.size() - 1 ||
                        !list.get(index + 1).equals(SORTTYPE.COUNT.getSortingType()) &&
                                !list.get(index + 1).equals(SORTTYPE.NATURAL.getSortingType())) {
                    System.out.println("No sorting type defined!");
                    return false;
                } else {
                    list.remove(index + 1);
                    list.remove(index);
                    index = -1;
                    continue;
                }
            }
            if (list.get(index).equals("-inputFile") && index < list.size() - 1) {
                inputFileName = list.get(index + 1);
                list.remove(index + 1);
                list.remove(index);
                index = -1;
                continue;
            }
            if (list.get(index).equals("-outputFile") && index < list.size() - 1) {
                outputFile = list.get(index + 1);
                list.remove(index + 1);
                list.remove(index);
                index = -1;
            }
        }
        // Print out invalid commands
        if (list.size() > 0) {
            for (String command : list) {
                System.out.println("\"" + command + "\" is not a valid parameter. It will be skipped.");
            }
        }
        return true;
    }

    public static void setDataType(String[] args) {
        String argsToString = String.join(",", args);
        if (argsToString.contains(DATATYPE.LINES.getDataType())) {
            dataType = DATATYPE.LINES.getDataType();
        } else if (argsToString.contains(DATATYPE.NUMBERS.getDataType())) {
            dataType = DATATYPE.NUMBERS.getDataType();
        } else {
            dataType = DATATYPE.WORDS.getDataType();
        }
    }

    public static void setSortingType(String[] args) {
        String argsToString = String.join(",", args);
        if (!argsToString.contains(SORTTYPE.COUNT.getSortingType())) {
            sortType = SORTTYPE.NATURAL.getSortingType();
        } else {
            sortType = SORTTYPE.COUNT.getSortingType();
        }
    }

}
