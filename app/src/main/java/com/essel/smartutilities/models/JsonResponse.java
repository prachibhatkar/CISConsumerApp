package com.essel.smartutilities.models;

import java.security.Provider;
import java.util.ArrayList;

/**
 * Created by Admin on 22-08-2016.
 */
public class JsonResponse {

    public static String SUCCESS="success";
    public static String FAILURE="failure";
    public Response responsedata;
    public ArrayList<Faq> faqs;
    public ArrayList<Services> type;
    public  AboutUs aboutus1;
    public  ArrayList<Services> connectiontype;
    public  String aboutus;
    public  String feedbackmessage;
    public Consumer user_info;
    public String consumer_no;
    public String id;
    public String otp;
    public String address;
    public String password;
    public String name;
    public String alternate_email;
    public String alternate_mobile;
    public String connection_type;
    public String mobile_no;
    //public ArrayList<AboutUs> about_us;
    public ContactUs contactus;
    public String result;//success/failure
    public String message;
    public String authorization;


    public String error_code;
}
