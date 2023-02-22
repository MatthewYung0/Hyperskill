package server;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database {

    private static final HashMap<String, String> database = new HashMap<>();
    private static final File fileDb = new File(Main.serverDataPath);

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Lock readLock = lock.readLock();
    private static final Lock writeLock = lock.writeLock();

    public static void set(String key, String value) throws IOException {
        writeLock.lock();
        synchronized (writeLock) {
            database.put(key,value);
            updateJsonFile();
            writeLock.unlock();
        }
    }

    public static void delete(String key) throws IOException {
        writeLock.lock();
        synchronized (writeLock) {
            database.put(key, null);
            updateJsonFile();
            writeLock.unlock();
        }
    }

    public static String get(String key) {
        readLock.lock();
        synchronized (readLock) {
            String data = database.get(key);
            readLock.unlock();
            return data;
        }
    }

    public static void updateJsonFile() throws IOException {
        writeLock.lock();
        synchronized (writeLock) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(database);
            FileWriter writer;
            try {
                if (!fileDb.exists()) {
                    fileDb.createNewFile();
                }
                writer = new FileWriter(fileDb);
                writer.write(jsonString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writer.close();
        }
        writeLock.unlock();
    }


}
