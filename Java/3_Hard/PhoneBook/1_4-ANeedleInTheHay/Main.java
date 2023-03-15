package phonebook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws IOException {

        File allContactsFile = new File("C:\\Users\\matth\\OneDrive\\Java Projects\\Hyperskill\\Phone Book\\Phone Book\\A needle in the hay\\directory.txt");
        File findContactsFile = new File("C:\\Users\\matth\\OneDrive\\Java Projects\\Hyperskill\\Phone Book\\Phone Book\\A needle in the hay\\find.txt");

        Scanner scanAllContacts = new Scanner(allContactsFile);
        Scanner scanFindContacts = new Scanner(findContactsFile);

        ArrayList<String> allContacts = new ArrayList<>();
        ArrayList<String> findContacts = new ArrayList<>();

        int counter = 0;

        while (scanAllContacts.hasNext()) {
            String line = scanAllContacts.nextLine();
            int firstSpaceIndex = line.indexOf(' ');
            String name = line.substring(firstSpaceIndex).trim();
            allContacts.add(name);
        }

        while (scanFindContacts.hasNext()) {
            String line = scanFindContacts.nextLine();
            findContacts.add(line);
        }

        System.out.println("Start searching...");
        long startTime = System.currentTimeMillis();

//        Linear Search
        for (int i = 0; i != findContacts.size(); i++) {
            for (int j = 0; j != allContacts.size(); j++) {
                if (findContacts.get(i).equals(allContacts.get(j))) {
                    counter++;
                }
            }
        }
//        *Around 3 seconds to finish

//        //Sort before Linear Search
//        Collections.sort(allContacts);
//        Collections.sort(findContacts);
//        for (int i = 0; i != findContacts.size(); i++) {
//            for (int j = 0; j != allContacts.size(); j++) {
//                if (findContacts.get(i).equals(allContacts.get(j))) {
//                    counter++;
//                }
//            }
//        }
//        *Around 17 seconds to finish

        long endTime = System.currentTimeMillis();
        getTimeDifference(startTime,endTime, counter);

    }

    public static void getTimeDifference(long startTime, long endTime, int counter) {
        long difference = endTime - startTime;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(difference) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(difference));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(difference) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(difference));
        long milliseconds = TimeUnit.MILLISECONDS.toMillis(difference)  - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(difference));
        System.out.println("Found " + counter + " / 500 entries. Time taken: " + minutes + " min. " + seconds + " sec. " + milliseconds + " ms.");
    }
}