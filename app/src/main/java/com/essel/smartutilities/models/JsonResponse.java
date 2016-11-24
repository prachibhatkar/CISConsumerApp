package com.essel.smartutilities.models;

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

    public  String aboutus;
    public  String feedbackmessage;
    public Consumer user_info;
    //public ArrayList<AboutUs> about_us;
    public ContactUs contactus;
    public String result;//success/failure
    public String message;
    public String authorization;


}
