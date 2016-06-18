package de.verkehr.data;

import java.util.Date;

/**
 * Created by Raffael on 18.06.2016.
 */
public class SensorData {

    private Date time;
    private String value;

    public SensorData(Date time, String valu) {
        this.time = time;
        this.value = value;
    }

    public Date getTime() {
        return this.time;
    }

    public String getValue() {
        return this.value;
    }
}
