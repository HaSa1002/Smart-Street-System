package de.verkehr;

import de.verkehr.util.UpdateThread;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting!");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        System.out.println(dateFormat.format(new Date()));

        UpdateThread thread = new UpdateThread();
        thread.start();
    }
}
