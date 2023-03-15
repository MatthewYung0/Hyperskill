package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {

        String allContactsPath = "C:\\Users\\matth\\OneDrive\\Java Projects\\Hyperskill\\Phone Book\\Phone Book\\A needle in the hay\\directory.txt";
        String findContactsPath = "C:\\Users\\matth\\OneDrive\\Java Projects\\Hyperskill\\Phone Book\\Phone Book\\A needle in the hay\\find.txt";

        List<String> allContacts = createAllContacts(allContactsPath);
        List<String> entryContacts = createFindContacts(findContactsPath);
        List<String> unsortedEntryContacts = createFindContacts(findContactsPath);

        //Linear Search Section
        long linearStartTime = getTime();
        int entries = linearSearch(allContacts, entryContacts);
        System.out.print("Found " + entries + " / " + entryContacts.size() + " entries. Time taken: ");
        long linearEndTime = getTime();
        printTimeTaken(linearStartTime, linearEndTime);
        long linearSearchTime = getTotalTime(linearStartTime, linearEndTime);

        //Bubble + Jump Search Section
        runBubbleSortAndJumpSearch(allContacts, entryContacts, unsortedEntryContacts, linearSearchTime);

        //Reseting
        entryContacts = createFindContacts(findContactsPath);

        //Quick Sort + Binary Search
        quickSort(entryContacts, 0, entryContacts.size() - 1);
        runJumpSortAndBinarySearch(entryContacts, unsortedEntryContacts);

        //Hashing
        createHashTableAndHashSearch(entryContacts, unsortedEntryContacts);
    }

    public static void createHashTableAndHashSearch(List<String> entryContacts, List<String> unsortedEntryContacts) {
        long createHashTableStartTime = getTime();
        Hashtable<String, String> entryContactsHashTable = createEntryContacts(entryContacts);
        Hashtable<String, String> unsortedEntryContactsHashTable = createEntryContacts(unsortedEntryContacts);
        long createHashTableEndTime = getTime();

        int entries = entryContactsHashTable.size();

        long searchHashTableStartTime = getTime();
        boolean isSameTable = entryContactsHashTable.equals(unsortedEntryContactsHashTable);
        long searchHashTableEndTime = getTime();

        if (isSameTable) {
            System.out.println("\nStart searching (hash table)...");
            System.out.print("Found " + entries + " / " + entries + ". Time taken: ");
            printTimeTaken(createHashTableStartTime + searchHashTableStartTime, createHashTableEndTime + searchHashTableEndTime);
            System.out.print("Creating time: ");
            printTimeTaken(createHashTableStartTime, createHashTableEndTime);
            System.out.print("Searching time: ");
            printTimeTaken(searchHashTableStartTime, searchHashTableEndTime);
        }
    }

    public static Hashtable<String, String> createEntryContacts (List<String> entryContacts) {
        Hashtable<String, String> entryContactsHashTable = new Hashtable<>();
        for (int i = 0; i < entryContacts.size(); i++) {
            entryContactsHashTable.put(entryContacts.get(i), entryContacts.get(i));
        }
        return entryContactsHashTable;
    }

    public static void runJumpSortAndBinarySearch(List<String> entryContacts, List<String> unsortedEntryContacts) {

        int entries = 0;

        System.out.println("\nStart searching (quick sort + binary search)...");
        long quickSortStartTime = getTime();
        List<String> sortedList = quickSort(entryContacts, 0, entryContacts.size() - 1);
        long quickSortEndTime = getTime();

        long binarySearchStartTime = getTime();
        for (int i = 0; i != entryContacts.size(); i++) {
            entries += binarySearch(entryContacts, unsortedEntryContacts.get(i));
        }
        long binarySearchEndTime = getTime();
        printSortAndSearchTimes(quickSortStartTime, quickSortEndTime, binarySearchStartTime, binarySearchEndTime, entries, sortedList, false);
    }

    public static int binarySearch(List<String> entryContacts, String name) {
        int left = 0;
        int right = entryContacts.size();

        while (left <= right) {
            int middle = (left + right) / 2;
            if (entryContacts.get(middle).equals(name)) {
                return 1;
            } else if (entryContacts.get(middle).compareTo(name) > 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return 0;
    }

    public static List<String> quickSort(List<String> entryContacts, int start, int end) {
        if (start < end) {
            int partitionIndex = partition(entryContacts, start, end);
            quickSort(entryContacts, start, partitionIndex - 1);
            quickSort(entryContacts, partitionIndex + 1, end);
        }
        return entryContacts;
    }

    public static int partition(List<String> entryContacts, int start, int end) {
        String pivot = entryContacts.get(end);
        int i = (start - 1);
        for (int j = start; j < end; j++) {
            if (entryContacts.get(j).compareTo(pivot) < 0) {
                i++;
                String temporary = entryContacts.get(i);
                entryContacts.set(i, entryContacts.get(j));
                entryContacts.set(j, temporary);
            }
        }
        String temporary = entryContacts.get(i + 1);
        entryContacts.set(i + 1, entryContacts.get(end));
        entryContacts.set(end, temporary);
        return i + 1;
    }

    public static void runBubbleSortAndJumpSearch(List<String> allContacts, List<String> entryContacts, List<String> unsortedEntryContacts, long linearSearchTime) {

        int entries = 0;
        long linearSearchStartTime = 0;
        long linearSearchEndTime = 0;
        long jumpSearchStartTime = 0;
        long jumpSearchEndTime = 0;
        boolean wasTooLong = false;

        System.out.println("\nStart searching (bubble sort + jump search)...");
        long bubbleSortStartTime = getTime();
        List<String> sortedList = bubbleSort(entryContacts, linearSearchTime);
        long bubbleSortEndTime = getTime();

        if (sortedList == null) {
            wasTooLong = true;
            linearSearchStartTime = getTime();
            entries = linearSearch(allContacts, entryContacts);
            linearSearchEndTime = getTime();
        } else {
            jumpSearchStartTime = getTime();
            for (int i = 0; i != unsortedEntryContacts.size(); i++) {
                entries += jumpSearch(sortedList, unsortedEntryContacts.get(i));
            }
            jumpSearchEndTime = getTime();
        }
        printSortAndSearchTimes(bubbleSortStartTime, bubbleSortEndTime, (linearSearchStartTime + jumpSearchStartTime), (linearSearchEndTime + jumpSearchEndTime), entries, entryContacts, wasTooLong);
    }

    public static List<String> bubbleSort(List<String> entryContacts, long linearSearchTime) {
        long startTime = getTime();
        for (int i = 0; i < entryContacts.size(); i++) {
            for (int j = i + 1; j < entryContacts.size(); j++) {
                if (entryContacts.get(i).compareTo(entryContacts.get(j)) > 0) {
                    String temporary = entryContacts.get(i);
                    entryContacts.set(i, entryContacts.get(j));
                    entryContacts.set(j, temporary);
                }
            }
            long endTime = getTime();
            long bubbleSortTime = getTotalTime(startTime, endTime);
            if (bubbleSortTime > (linearSearchTime * 10)) {
                return null;
            }
        }
        return entryContacts;
    }

    public static int jumpSearch(List<String> sortedEntryContacts, String entryToBeFound) {
        int step = (int) Math.floor(Math.sqrt(sortedEntryContacts.size()));
        int currentLastIndex = step - 1;
        int listLength = sortedEntryContacts.size();
        while (currentLastIndex < sortedEntryContacts.size() && entryToBeFound.compareTo(sortedEntryContacts.get(currentLastIndex)) > 0) {
            currentLastIndex += step;
        }
        for (int currentSearchIndex = currentLastIndex - step + 1; currentSearchIndex <= currentLastIndex && currentSearchIndex < listLength; currentSearchIndex++) {
            if (entryToBeFound.equals(sortedEntryContacts.get(currentSearchIndex))) {
                return 1;
            }
        }
        return 0;
    }

    public static int linearSearch(List<String> allContacts, List<String> entryContacts) {
        System.out.println("\nStart searching (linear search)...");
        int entries = 0;
        for (int i = 0; i != entryContacts.size(); i++) {
            for (int j = 0; j != allContacts.size(); j++) {
                if (entryContacts.get(i).equals(allContacts.get(j))) {
                    entries++;
                }
            }
        }
        return entries;
    }

    public static void printSortAndSearchTimes(long sortStartTime, long sortEndTime, long searchStartTime, long searchEndTime, int entries, List<String> entryContacts, boolean wasTooLong) {
        System.out.print("Found " + entries + " / " + entryContacts.size() + " entries. Time taken: ");
        printTimeTaken(sortStartTime + searchStartTime, sortEndTime + searchEndTime);
        System.out.print("Sorting time: ");
        printTimeTaken(sortStartTime, sortEndTime);
        if (wasTooLong) {
            System.out.print("\033[F\r" + " - STOPPED, moved to linear search");
        }
        System.out.print("Searching time: ");
        printTimeTaken(searchStartTime, searchEndTime);
    }

    public static void printTimeTaken(long startTime, long endTime) {
        long seconds = convertToSeconds(startTime, endTime);
        long minutes = convertToMinutes(startTime, endTime);
        long milliseconds = convertToMilliseconds(startTime, endTime);
        System.out.print(minutes + " min. " + seconds + " sec. " + milliseconds + " ms. \n");
    }

    public static long getTime() {
        return System.currentTimeMillis();
    }

    public static long convertToSeconds(long startTime, long endTime) {
        return TimeUnit.MILLISECONDS.toSeconds(endTime - startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(startTime - endTime));
    }

    public static long convertToMinutes(long startTime, long endTime) {
        return TimeUnit.MILLISECONDS.toMinutes(endTime - startTime) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(endTime - startTime));
    }

    public static long convertToMilliseconds(long startTime, long endTime) {
        return TimeUnit.MILLISECONDS.toMillis(endTime - startTime) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(endTime - startTime));
    }

    public static long getTotalTime(long startTime, long endTime) {
        return endTime - startTime;
    }

    public static File createFile(String path) {
        return new File(path);
    }

    public static List<String> createAllContacts(String path) throws FileNotFoundException {
        File allContactsFile = createFile(path);
        Scanner scanAllContacts = new Scanner(allContactsFile);
        List<String> allContacts = new ArrayList<>();
        while (scanAllContacts.hasNext()) {
            String line = scanAllContacts.nextLine();
            int firstSpaceIndex = line.indexOf(' ');
            String name = line.substring(firstSpaceIndex).trim();
            allContacts.add(name);
        }
        scanAllContacts.close();
        return allContacts;
    }

    public static List<String> createFindContacts(String path) throws FileNotFoundException {
        File findContactsFile = createFile(path);
        Scanner scanFindContacts = new Scanner(findContactsFile);
        List<String> findContacts = new ArrayList<>();
        while (scanFindContacts.hasNext()) {
            String line = scanFindContacts.nextLine();
            findContacts.add(line);
        }
        scanFindContacts.close();
        return findContacts;
    }
}