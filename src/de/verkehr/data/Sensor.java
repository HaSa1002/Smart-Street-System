package de.verkehr.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Raffael on 18.06.2016.
 */
public class Sensor {

    private String id;
    private List<SensorData> sensorDatas = new ArrayList<>();
    private Date lastClear;

    public Sensor(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<SensorData> getSensorData() {
        return sensorDatas;
    }

    public void addSensorData(SensorData data) {
        System.out.println(sensorDatas.size());
        sensorDatas.add(data);
        System.out.println(sensorDatas.size());
    }

    public void clearSensorData() {
        sensorDatas.clear();
        this.lastClear = new Date();
    }

    public int getHighestSensorID() {
        return sensorDatas.size();
    }
}
