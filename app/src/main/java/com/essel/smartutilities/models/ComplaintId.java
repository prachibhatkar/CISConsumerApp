package com.essel.smartutilities.models;

import java.io.Serializable;

/**
 * Created by hp on 11/21/2016.
 */

public class ComplaintId implements Serializable  {
    public String id;
    public String remark;
    public String raised_date;
    public String complaint_no;


    public String getComplaint_no() {
        return complaint_no;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setComplaint_no(String complaint_no) {
        this.complaint_no = complaint_no;
    }

    public String getId() {

        return id;

    }
}
