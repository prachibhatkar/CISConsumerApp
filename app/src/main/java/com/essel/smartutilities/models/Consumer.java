package com.essel.smartutilities.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Admin on 24-08-2016.
 */
public class Consumer implements Serializable {

    public String amount;
    public String payment_mode;
    public String transaction_id;

    public Consumer()
    {

    }
    public Consumer(String transaction_id, String amount, String payment_mode) {
        this.transaction_id = transaction_id;
        this.amount =amount ;
        this.payment_mode = payment_mode;

    }

    public static ArrayList<Consumer> createConsumersList(int numContacts) {
        ArrayList<Consumer> contacts = new ArrayList<Consumer>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Consumer("Transaction Id :- 5454367"+i,""+(14580+i),"Transaction Type:- CASH"));
        }
        return contacts;
    }
}