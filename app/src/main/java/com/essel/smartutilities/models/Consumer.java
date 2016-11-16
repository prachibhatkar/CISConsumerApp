package com.essel.smartutilities.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Admin on 24-08-2016.
 */
public class Consumer implements Serializable {

    public String paidamt;
    public String payment_mode;
    public String transaction_id;
    public String address;
    public String netamt;
    public String duedate;
    public String month;
    public String consumer_id;
    public String acctype;
    public String consumer_name;

    public Consumer() {

    }

    public Consumer(String transaction_id, String amount, String payment_mode, String acctype, String address, String duedate
            , String month, String consumer_id, String consumer_name, String netamt) {
        this.transaction_id = transaction_id;
        this.paidamt = amount;
        this.payment_mode = payment_mode;
        this.address = address;
        this.netamt = netamt;
        this.duedate = duedate;
        this.month = month;
        this.consumer_id = consumer_id;
        this.acctype = acctype;
        this.consumer_name = consumer_name;


    }

    public static ArrayList<Consumer> createConsumersList(int numContacts) {
        String pr = "", mode = "";
        ArrayList<Consumer> contacts = new ArrayList<Consumer>();

        for (int i = 1; i <= numContacts; i++) {
            if (i==1) {
                pr = "primary";
                mode = "cash";
            } else {
                pr = " ";
                mode = "online";
            }

            contacts.add(new Consumer("5454367" + i, "205" + i, mode, pr, "hoichiohiosdc sahsahcnslk", "Aug25,240" + i, "Aug201" + i
                    , "12344352" + i, "consumer" + i, "3164" + i));
        }
        return contacts;
    }


}