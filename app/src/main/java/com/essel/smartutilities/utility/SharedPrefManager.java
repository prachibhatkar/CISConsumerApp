
/*
' History Header:      Version         - Date        - Developer Name   - Work Description
' History       :        1.0           - Aug-2016    - Amol Chavan  - class describes all necessary info about the UserLogin Table Table
 */

/*
 ##############################################################################################
 #####                                                                                    #####
 #####     FILE              : UserLoginTable.Java 	       								  #####
 #####     CREATED BY        : Amol Chavan                                                #####
 #####     CREATION DATE     : Aug-2016                                                   #####
 #####                                                                                    #####
 #####     MODIFIED  BY      : Amol Chavan                                                #####
 #####     MODIFIED ON       :                                                   	      #####
 #####                                                                                    #####
 #####     CODE BRIEFING     : SharedPrefManager Class.         		 			   	  #####
 #####                         class describes all necessary info about UserLogin Table   #####
 #####                                                                                    #####
 #####     COMPANY           : Bynary.                                                    #####
 #####                                                                                    #####
 ##############################################################################################
 */
package com.essel.smartutilities.utility;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String MY_PREFERENCES = "MyPrefs";
    public static String CONSUMER_NAME = "consumer_name";
    public static String CONSUMER_NO = "consumer_no";
    public static String CONSUMER_CITY = "consumer_city";
    public static String ID = "id";
    public static  String AUTH_TOKEN = "auth_token";
    public static  String ADDRESS = "address";
    public static String USER_NAME = "user_name";
    public static String USER_ID = "id";
    public static  String CONNECTION_TYPE = "connection_type";
    public static  String MOBILE = "mobile";

    public static String PASSWORD = "pasword";
    public static String USER_KEY = "user_key";



    public static void saveUserCredentials(Context context, String userKey, String userName, String password, String userid) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_KEY, userKey).putString(USER_NAME, userName).putString(PASSWORD, password).putString(USER_ID,userid);
        editor.commit();
    }

    public static void saveValue(Context context, String key, Object value) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (value instanceof Integer) {
            editor.putInt(key, (int) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (boolean) value);
        }
        editor.commit();
    }

    public static String getStringValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public static int getIntValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, 0);
    }

    public static float getFloatValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getFloat(key, 0);
    }


    public static boolean getBooleanValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }

    /**
     * method to determine whether we have asked
     * for this permission before.. if we have, we do not want to ask again.
     * They either rejected us or later removed the permission.
     * @param permission
     * @return
     */
    public static boolean shouldWeAskPermission(Context context, String permission) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(permission, true);
    }

    public static long getLongValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getLong(key, 0);
    }
}
