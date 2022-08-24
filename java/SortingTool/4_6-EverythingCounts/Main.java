package sorting;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Main {
    static List<String> list = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static String dataType;
    static String sortType;

    public static void main(final String[] args) {
        setDataType(args);
        setSortingType(args);
        while (scanner.hasNextLine()) {
            String text = scanner.nextLine();
            list.add(text);
        }
        processData();
    }

    public static void processData() {
        if (sortType.equals((SORTTYPE.COUNT.getSortingType()))) {
            sortCount();
        } else {
            sortNatural();
        }
    }

    public static void sortNatural() {
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

    public static void sortCount() {
        // Checking if the datatype is a Long or Word
        if (!dataType.equals(DATATYPE.LINES.getDataType())) {
            // Creating and adding elements to map.
            Map<String,Integer> unsortedMapWithSplitElements = trimAndSplitHashMap();
            if (dataType.equals(DATATYPE.WORDS.getDataType())) {
                Map<String,Integer> sortedWordsMap = dataWordSortCount(unsortedMapWithSplitElements);
                printMap(sortedWordsMap);
            } else {
                Map<Long,Integer> numbersSortedMap = dataLongSortCount(unsortedMapWithSplitElements);
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

    public static Map <String,Integer> dataWordSortCount(Map<String,Integer> unsortedMapWithSplitElements) {
        Map <String, Integer> wordsMap = unsortedMapWithSplitElements.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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

    public static Map <Long,Integer> dataLongSortCount(Map<String,Integer> unsortedMapWithSplitElements) {
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

    public static Map<String,Integer> trimAndSplitHashMap() {
        Map<String, Integer> unsortedMap = new HashMap<>();
        for (String line: list) {
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
    public static void printMap(Map<?, Integer> map) {
        int sum = map.values().stream().mapToInt(Integer::intValue).sum();
        String typeToPrint = "number";
        if (dataType.equals(DATATYPE.WORDS.getDataType())) {
            typeToPrint = DATATYPE.WORDS.getDataType();
        } else if (dataType.equals(DATATYPE.LINES.getDataType())) {
            typeToPrint = DATATYPE.LINES.getDataType();
        }
        System.out.println("Total " + typeToPrint + "s : " + sum);
        map.forEach((key, value) -> System.out.println(key + ": " + value + " time(s), " + ((value * 100) / sum) + "%"));
    }

    public static void printArrayList(List<?> arrayList) {
        String typeToPrint = "number";

        if (dataType.equals(DATATYPE.WORDS.getDataType())) {
            typeToPrint = DATATYPE.WORDS.getDataType();
        } else if (dataType.equals(DATATYPE.LINES.getDataType())) {
            typeToPrint = DATATYPE.LINES.getDataType();
        }
        System.out.println("Total " + typeToPrint + "s : " + arrayList.size());

        System.out.print("Sorted data: ");
        boolean printSpace = true;
        for (Object item: arrayList) {
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

    public static void setDataType(String[] args) {
        String argsToString = String.join(",", args);
        if (argsToString.contains(DATATYPE.LINES.getDataType())) {
            dataType = DATATYPE.LINES.getDataType();
        } else if (argsToString.contains(DATATYPE.NUMBERS.getDataType()))  {
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
