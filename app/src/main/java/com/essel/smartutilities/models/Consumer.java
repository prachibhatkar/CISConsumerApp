package com.essel.smartutilities.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Admin on 24-08-2016.
 */
public class Consumer implements Serializable {
    public String consumer_name;
    public String consumer_id;
    public String meter_no;
    public String dt_code;
    public String bil_cycle_code;
    public String pol_no;
    public String route_id;
    public String status;
    public String phone_no;
    public String address;
    public String meter_reading;
    public String meter_status;
    public String reader_status;
    public String comments;
    public  boolean isSuspicious;
    public String remark;

    public Consumer()
    {

    }
    public Consumer(String consumer_name, String consumer_id, String meter_no, String dt_code, String bil_cycle_code, String pol_no, String route_id, String status) {
        this.consumer_name = consumer_name;
        this.consumer_id = consumer_id;
        this.meter_no = meter_no;
        this.dt_code = dt_code;
        this.bil_cycle_code = bil_cycle_code;
        this.pol_no = pol_no;
        this.route_id = route_id;
        this.status = status;
    }

    public static ArrayList<Consumer> createConsumersList(int numContacts) {
        ArrayList<Consumer> contacts = new ArrayList<Consumer>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Consumer("Amol Chavan"+i,""+(14580+i),"M"+(1240+i),""+(1111+i),""+(14+i),""+(19+i),""+(100+i),"OPEN"));
        }
        return contacts;
    }
}
