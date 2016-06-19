package de.verkehr.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raffael on 18.06.2016.
 */
public class SensorManager {

    private static List<Sensor> sensorList = new ArrayList<>();

    public static Sensor getSensor(String id) {
        for (Sensor sensor : sensorList) {
            if (sensor.getId().equals(id)) {
                return sensor;
            }
        }
        return null;
    }

    public static List<Sensor> getSensorList() {
        return sensorList;
    }

    public static void setSensor(Sensor sensor) {
        if (getSensor(sensor.getId()) != null) {
            removeSensor(getSensor(sensor.getId()));
            sensorList.add(sensor);
            return;
        }
        sensorList.add(sensor);
    }

    public static void removeSensor(Sensor sensor) {
        if (sensorList.contains(sensor))
            sensorList.remove(sensor);
    }
}
