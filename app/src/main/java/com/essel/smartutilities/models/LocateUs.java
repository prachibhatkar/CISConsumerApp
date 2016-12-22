package com.essel.smartutilities.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hp on 11/21/2016.
 */

public class LocateUs {
    public String contact_no;
    public String  city;
    public String end_time;
    public String  address;
    public String  start_time;
    public String  latitude;
    public String  longitude;
    public String  center_name;



    public String getContact_no() {
        return contact_no;
    }
    public String getend_time() {
        return contact_no;
    }
    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String id) {
        this.city = id;
    }
    public String getCenter_name() {
        return center_name;
    }
    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;

    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address= address;
    }
    public String getStart_time() {
        return start_time;
    }
    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }
    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }



}


