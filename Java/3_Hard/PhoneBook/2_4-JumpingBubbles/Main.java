package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {

        String allContactsPath = "C:\\Users\\matth\\OneDrive\\Java Projects\\Hyperskill\\Phone Book\\Phone Book\\A needle in the hay\\directory.txt";
        String findContactsPath = "C:\\Users\\matth\\OneDrive\\Java Projects\\Hyperskill\\Phone Book\\Phone Book\\A needle in the hay\\find.txt";

        List<String> allContacts = createAllContacts(allContactsPath);
        List<String> unsortedEntryContacts = createFindContacts(findContactsPath);
        List<String> fixedUnsortedEntryContacts = createFindContacts(findContactsPath);

        //Linear Search Section
        System.out.println("\nStart searching (linear search)...");
        long linearStartTime = getTime();
        int entries = linearSearchEntries(allContacts, unsortedEntryContacts);
        System.out.print("Found " + entries + " / 500 entries. ");
        long linearEndTime = getTime();
        printTimeTaken(linearStartTime, linearEndTime);
        long linearSearchTime = getTotalTime(linearStartTime,linearEndTime);

        //Bubble + Jump Search Section
        printSortAndSearchEntries(allContacts, unsortedEntryContacts, fixedUnsortedEntryContacts, linearSearchTime);

    }

    public static int linearSearchEntries(List<String> allContacts, List<String> unsortedEntryContacts) {
        int entries = 0;
        for (int i = 0; i != unsortedEntryContacts.size(); i++) {
            for (int j = 0; j != allContacts.size(); j++) {
                if (unsortedEntryContacts.get(i).equals(allContacts.get(j))) {
                    entries++;
                }
            }
        }
        return entries;
    }

    public static List<String> bubbleSortFindContactsList(List<String> unsortedEntryContacts, long linearSearchTime) {
        long startTime = getTime();
            for (int i = 0; i < unsortedEntryContacts.size(); i++) {
                for (int j = i + 1; j < unsortedEntryContacts.size(); j++) {
                    if (unsortedEntryContacts.get(i).compareTo(unsortedEntryContacts.get(j)) > 0) {
                        String temporary = unsortedEntryContacts.get(i);
                        unsortedEntryContacts.set(i, unsortedEntryContacts.get(j));
                        unsortedEntryContacts.set(j, temporary);
                    }
                }
                long endTime = getTime();
                long bubbleSortTime = getTotalTime(startTime, endTime);
                if (bubbleSortTime > (linearSearchTime * 10)) {
                    return null;
                }
            }
        return unsortedEntryContacts;
    }

    public static int jumpSearch(List<String> sortedEntryContacts, String entryToBeFound) {

        int step = (int) Math.floor(Math.sqrt(sortedEntryContacts.size()));
        int currentLastIndex = step - 1;
        int listLength = sortedEntryContacts.size();

        while(currentLastIndex < sortedEntryContacts.size() && entryToBeFound.compareTo(sortedEntryContacts.get(currentLastIndex)) > 0) {
            currentLastIndex += step;
        }
        for (int currentSearchIndex = currentLastIndex - step + 1; currentSearchIndex <= currentLastIndex && currentSearchIndex < listLength; currentSearchIndex++) {
            if(entryToBeFound.equals(sortedEntryContacts.get(currentSearchIndex))) {
                return 1;
            }
        }
        return 0;
    }

    public static void printSortAndSearchEntries(List<String> allContacts, List<String> unsortedEntryContacts, List<String> fixedUnsortedEntryContacts, long linearSearchTime) {

        int entries = 0;
        long linearSearchStartTime = 0;
        long linearSearchEndTime = 0;
        long jumpSearchStartTime = 0;
        long jumpSearchEndTime = 0;

        System.out.println("\n\nStart searching (bubble sort + jump search)...");
        long bubbleSortStartTime = getTime();
        List<String> sortedList = bubbleSortFindContactsList(unsortedEntryContacts, linearSearchTime);
        long bubbleSortEndTime = getTime();

        if (sortedList == null) {
            linearSearchStartTime = getTime();
            entries = linearSearchEntries(allContacts, unsortedEntryContacts);
            linearSearchEndTime = getTime();
        } else {
            jumpSearchStartTime = getTime();

            for (int i = 0; i != fixedUnsortedEntryContacts.size(); i++) {
                entries += jumpSearch(sortedList, fixedUnsortedEntryContacts.get(i));
            }

            jumpSearchEndTime = getTime();
        }

        System.out.print("Found " + entries + " / 500 entries. ");
        printTimeTaken((linearSearchStartTime + jumpSearchStartTime + bubbleSortStartTime), (linearSearchEndTime + jumpSearchEndTime + bubbleSortEndTime));
        System.out.print("\nSorting time: ");
        printTimeTaken(bubbleSortStartTime, bubbleSortEndTime);

        if(linearSearchStartTime != 0 && linearSearchEndTime != 0) {
            System.out.print(" - STOPPED, move to linear search");
        }

        System.out.print("\nSearching time: ");
        printTimeTaken((linearSearchStartTime + jumpSearchStartTime), (linearSearchEndTime + jumpSearchEndTime));

    }

    public static void printTimeTaken(long startTime, long endTime) {
        long seconds = convertToSeconds(startTime, endTime);
        long minutes = convertToMinutes(startTime, endTime);
        long milliseconds = convertToMilliseconds(startTime, endTime);
        System.out.print("Time taken: " + minutes + " min. " + seconds + " sec. " + milliseconds + " ms.");
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