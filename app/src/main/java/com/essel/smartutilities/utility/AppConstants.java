package com.essel.smartutilities.utility;

/**
 * Created by Sandeep on 11/22/15.
 */
public class AppConstants {


    public static final String BASE_URL=  "http://192.168.10.114:8000/mobileapi/";
    // All static app constants are here except String , array of strings, color, dimentions etc


    public static final String REQEST_FAQ = "get-faq/";
    public static final String URL_GET_FAQ=BASE_URL+REQEST_FAQ;

    public static final String REQEST_ABOUT_US = "get-about-us/";
    public static final String URL_GET_ABOUT_US=BASE_URL+REQEST_ABOUT_US;

    public static final String REQEST_TIPS = "get-tips/";
    public static final String URL_GET_TIPS=BASE_URL+REQEST_TIPS;

    public static final String SHARED_PREF = "SmartUtilities";
    public static final String SCREEN_ID = "screen_id";
    String REQEST_LOGIN ="login_call";

    public static String CONSUMER_ID = "consumer_id";
    public static String CONSUMER_PASSWORD = "password";
    public static String CONSUMER_LOGGED_IN_DATE = "user_logged_in_date";
    public static final int ALL_PERMISSIONS_RESULT = 107;

    public static String CONSUMER_NAME = "consumer_name";
    public static String CONSUMER_NO = "consumer_no";
    public static String CONSUMER_CITY = "consumer_city";

}
