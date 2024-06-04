package dev.mega.megacore.util;

import java.util.HashMap;

public class ThreadUtil {

    private static final HashMap<Integer, Thread> threads = new HashMap<>();

    private ThreadUtil() {

    }

    public static void start(int id, Runnable runnable) {
        stop(id);
        Thread thread = new Thread(runnable);
        thread.start();
        threads.put(id, thread);
    }

    public static boolean isAlive(int id) {
        return threads.containsKey(id);
    }

    public static void stop(int id) {
        Thread thread = threads.remove(id);
        if (thread != null) thread.interrupt();
    }

}
