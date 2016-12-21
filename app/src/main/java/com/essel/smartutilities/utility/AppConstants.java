package com.essel.smartutilities.utility;


public class AppConstants {

   public static final String BASE_URL = "http://192.168.10.102:9999/mobileapi/";
  //  public static final String BASE_URL = "http://192.168.10.119:9999/mobileapi/";

//      public static final String BASE_URL=  "http://192.168.10.115:9999/mobileapi/";
    // All static app constants are here except String , array of strings, color, dimentions etc

    public static final String REQUEST_LOGIN = "login/";
    public static final String URL_LOGIN = BASE_URL + REQUEST_LOGIN;

    public static final String REQUEST_CONTACT_INFO= "edit-contact-info/";
    public static final String URL_CONTACT_INFO= BASE_URL + REQUEST_CONTACT_INFO;

    public static final String REQUEST_CHANGE_PASS= "change-password/";
    public static final String URL_CHANGE_PASS= BASE_URL + REQUEST_CHANGE_PASS;

    public static final String REQUEST_LOGOUT = "log-out/";
    public static final String URL_LOGOUT = BASE_URL + REQUEST_LOGOUT;

    public static final String REQUEST_FAQ = "get-faq/";
    public static final String URL_GET_FAQ = BASE_URL + REQUEST_FAQ;

    public static final String REQUEST_GET_CONTACT_DETAILS = "get-contactus-details/";
    public static final String URL_GET_CONTACT_DETAILS = BASE_URL + REQUEST_GET_CONTACT_DETAILS;

    public static final String REQEST_ABOUT_US = "get-about-us/";
    public static final String URL_GET_ABOUT_US = BASE_URL + REQEST_ABOUT_US;

    public static final String REQUEST_SERVICE_TYPE = "get-service-type/";
    public static final String URL_GET_SERVICE_TYPE = BASE_URL + REQUEST_SERVICE_TYPE;

    public static final String REQUEST_TARIFF = "get-tarif/";
    public static final String URL_GET_TARIFF = BASE_URL + REQUEST_TARIFF;


    public static final String REQEST_TIPS = "get-tips/";
    public static final String URL_GET_TIPS = BASE_URL + REQEST_TIPS;

    public static final String REQUEST_GET_COMPLAINT_TYPE = "get-complaint-type/";
    public static final String URL_GET_COMPLAINT_TYPE = BASE_URL + REQUEST_GET_COMPLAINT_TYPE;

    public static final String REQUEST_GET_LOCATE_US= "get-complaint-type/";
    public static final String URL_GET_LOCATE_US = BASE_URL + REQUEST_GET_LOCATE_US;


    public static final String REQUEST_GET_CONNECTION_TYPE = "get-service-subtype/";
    public static final String URL_GET_CONNECTION_TYPE = BASE_URL + REQUEST_GET_CONNECTION_TYPE;

    public static final String REQUEST_OTP = "create-app-user/";
    public static final String URL_GET_OTP = BASE_URL + REQUEST_OTP;

    public static final String REQUEST_ADD_ACCOUNT = "add-account/";
    public static final String URL_ADD_ACCOUNT = BASE_URL + REQUEST_ADD_ACCOUNT;

    public static final String REQUEST_RESEND_OTP = "resend-otp/";
    public static final String URL_GET_RESEND_OTP = BASE_URL + REQUEST_RESEND_OTP;

    public static final String REQUEST_DELETE_ACCOUNT = "delete-account/";
    public static final String URL_DELETE_ACCOUNT = BASE_URL + REQUEST_DELETE_ACCOUNT;

    public static final String REQUEST_NEW_CONNECTION = "new-consumer/";
    public static final String URL_NEW_CONNECTION = BASE_URL + REQUEST_NEW_CONNECTION;

    public static final String REQUEST_CREATE = "registration/";
    public static final String URL_REGISTER = BASE_URL + REQUEST_CREATE;

    public static final String REQUEST_REGISTER = "consumer-registration/";
    public static final String URL_GET_REGISTER = BASE_URL + REQUEST_REGISTER;

    public static final String REQUEST_GET_ACCOUNTS = "get-accounts/";
    public static final String URL_GET_ACCOUNTS = BASE_URL + REQUEST_GET_ACCOUNTS;

    public static final String REQUEST_FEEDBACK = "add-feedback/";
    public static final String URL_POST_FEEDBACK = BASE_URL + REQUEST_FEEDBACK;

    public static final String REQUEST_FORGOT_PASSWORD = "forgot-password/";
    public static final String URL_POST_FORGOT_PASSWORD = BASE_URL + REQUEST_FORGOT_PASSWORD;


    public static final String REQUEST_POST_ADD_COMPLAINT = "add-complaint/";
    public static final String URL_POST_ADD_COMPLAINT = BASE_URL + REQUEST_POST_ADD_COMPLAINT;

    public static final String REQUEST_POST_SERVICE_REQUEST = "service-request/";
    public static final String URL_POST_SERVICE_REQUEST = BASE_URL + REQUEST_POST_SERVICE_REQUEST;


    public static final String REQUEST_BRANDING_IMAGES = "get-branding-images/";
    public static final String URL_BRANDING_IMAGES = BASE_URL + REQUEST_BRANDING_IMAGES;

    public static final String SHARED_PREF = "SmartUtilities";
    public static final String SCREEN_ID = "screen_id";

    String REQEST_LOGIN = "login_call";

    public static String CONSUMER_ID = "consumer_id";
    public static String CONSUMER_PASSWORD = "password";
//    public static String CONSUMER_LOGGED ="false" ;
    public static final int ALL_PERMISSIONS_RESULT = 107;

    public static String CONSUMER_NAME = "consumer_name";
    public static String CONSUMER_NO = "consumer_no";
    public static String CONSUMER_CITY = "consumer_city";

}
