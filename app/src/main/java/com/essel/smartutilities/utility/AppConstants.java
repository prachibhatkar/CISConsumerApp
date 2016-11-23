package com.essel.smartutilities.utility;

/**
 * Created by Sandeep on 11/22/15.
 */
public class AppConstants {


    public static final String BASE_URL=  "http://192.168.10.120:9999/mobileapi/";
    // All static app constants are here except String , array of strings, color, dimentions etc

    public static final String REQUEST_LOGIN="login/";
    public static final String URL_LOGIN=BASE_URL+REQUEST_LOGIN;

    public static final String REQUEST_FAQ = "get-faq/";
    public static final String URL_GET_FAQ=BASE_URL+REQUEST_FAQ;

    public static final String REQUEST_GET_CONTACT_DETAILS = "get-contactus-details/";
    public static final String URL_GET_CONTACT_DETAILS=BASE_URL+REQUEST_GET_CONTACT_DETAILS;

    public static final String REQEST_ABOUT_US = "get-about-us/";
    public static final String URL_GET_ABOUT_US=BASE_URL+REQEST_ABOUT_US;

    public static final String REQEST_TIPS = "get-tips/";
    public static final String URL_GET_TIPS=BASE_URL+REQEST_TIPS;


    public static final String REQUEST_NEW_CONNECTION = "new-consumer/";
    public static final String URL_NEW_CONNECTION =BASE_URL+REQUEST_NEW_CONNECTION;



    public static final String REQUEST_FEEDBACK = "add-feedback/";
    public static final String URL_POST_FEEDBACK=BASE_URL+REQUEST_FEEDBACK;

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
