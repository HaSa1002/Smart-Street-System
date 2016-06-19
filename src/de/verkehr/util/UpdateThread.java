package de.verkehr.util;

import de.verkehr.data.Sensor;
import de.verkehr.data.SensorData;
import de.verkehr.data.SensorManager;
import de.verkehr.diagram.Diagram;
import org.jfree.ui.RefineryUtilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

/**
 * Created by Raffael on 18.06.2016.
 */
public class UpdateThread implements Runnable {

    private Thread thread = new Thread(this);
    private boolean running = false;


    public void start() {
        this.running = true;
        if (running)
            this.thread.start();
    }

    public void stop() {
        System.out.println("Thread stopped:");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        System.out.println(dateFormat.format(new Date()));
        this.running = false;
        this.thread.interrupt();
    }

    @Override
    public void run() {
        while (running) {
            //get lines
            ArrayList<String[]> lines = getLines();
            if (lines.size() < 1) {
                System.out.println("found no line");
                break;
            }

            for (String[] line : lines) {

                if (line.length < 3) {
                    System.out.println("found wrong data");
                    break;
                }
                Sensor sensor = SensorManager.getSensor(line[0]);
                if (sensor == null) {
                    sensor = new Sensor(line[0]);
                }
                //yyyyMMddhhmmss
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
                Date date = new Date();
                try {
                    date = dateFormat.parse(line[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat dateMessageFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                System.out.println(dateMessageFormat.format(date) + " " + line[2]);
                SensorData data = new SensorData(date, line[2]);
                sensor.addSensorData(data);
                SensorManager.setSensor(sensor);
            }

            final Diagram diagram = new Diagram("SSMS Diagram");
            diagram.pack();
            RefineryUtilities.centerFrameOnScreen(diagram);
            diagram.show(true);
            try {
                Thread.sleep(1000 * 5 /* * 30 */);
                diagram.show(false);
            } catch (InterruptedException e) {
                this.stop();
                System.out.println("Thread crashed:");
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                System.out.println(dateFormat.format(new Date()));
                e.printStackTrace();
            } finally {
                SensorManager.getSensorList().forEach(Sensor::clearSensorData);
            }
        }
    }

    private ArrayList<String[]> getLines() {
        ArrayList<String[]> lines = new ArrayList<>();
        InputStream inputStream = null;
        //FileInputStream inputStream = null;
        try {
            //open stream
            //inputStream = new FileInputStream("C:\\Users\\Raffael\\Desktop\\Daten.csv");

            SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
            long time = 1000 * 60 * 60 * 24 * 7;
            String from = dateFormat.format(System.currentTimeMillis() - time);
            String to = dateFormat.format(System.currentTimeMillis());
            System.out.println(from + " - " + to);


            inputStream = getInputStream(from, to, "DRUCKSENSOR");
            //read datas
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            bufferedReader.lines().forEach(new Consumer<String>() {
                @Override
                public void accept(String s) {
                    if (!s.trim().equals(""))
                        lines.add(s.split(","));
                }
            });
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    //Default: DRUCKSENSOR
    private InputStream getInputStream(String from, String to, String sensor) {
        System.out.println("get new sensordatas");
        HttpURLConnection connection = null;

        try {
            URL url = new URL("http://192.168.6.102/csv.php?von=" + from + "&bis=" + to + "&sengr=" + sensor + "&all=off&type=off");
            System.out.println(url);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}