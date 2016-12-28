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
    public ArrayList<Tips> tips;
    public ArrayList<Services> type;
    public ArrayList<Complaints> complaint_type;
    public ArrayList<LocateUs> locateus;
    public ArrayList<ComplaintId> complaints;
    public  AboutUs aboutus1;
    public  ArrayList<Services> connectiontype;
    public  ArrayList<TarifCatagory> tariffCategory;
    public  ArrayList<TariffEnergyCharge> tariffEnergyCharge;
    public  ArrayList<FixedEnergyCharge> fixedEnergyCharge;
    public  String aboutus;
    public  ArrayList<BrandingImages> images;
    public  String logout;
    public  String feedbackmessage;
    public String forgotpasswordmessage;
    public  String servicerequestmessage;
    public  String addcomplaintmessage;
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
    public ArrayList<Consumer> consumers;
    public ContactUs contactus;
    public Tariff tariff;
    public String result;//success/failure
    public String message;
    public String serviceid;
    public String authorization;


    public String error_code;
}
