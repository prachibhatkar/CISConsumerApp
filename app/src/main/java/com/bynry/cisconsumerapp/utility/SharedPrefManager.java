

package com.bynry.cisconsumerapp.utility;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String MY_PREFERENCES = "MyPrefs";
    public static String CONSUMER_NAME = "consumer_name";
    public static String CONSUMER_NO = "consumer_no";
    public static String CONSUMER_NO_ADD = "consumer_no_add";
    public static String CONSUMER_NAME_ADD = "consumer_no_name";

    public static String CONSUMER_CITY = "consumer_city";
    public static String ID = "id";
    public static  String AUTH_TOKEN = "auth_token";
    public static  String ADDRESS1 = "address1";
    public static  String HELPLINENO = "helplineno";
    public static  String ADDRESS2 = "address2";
    public static  String ADDRESS3 = "address3";
    public static String USER_NAME = "user_name";
    public static String EMAIL_ID = "email_id";
    public static String ALTERNATE_MOB_NO = "alternate_mob_no";
    public static  String CONNECTION_TYPE = "connection_type";
    public static  String MOBILE = "mobile";
    public static String CONSUMER_LOGGED ="";
    public static String CON_NO = "con_no";
    public static String POSTAL = "postal";
    public static String FCM_PREF = "fcm_pref";
    public static String FCM_TOKEN = "fcm_token";

    public static String USER_KEY = "user_key";


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


    public static boolean shouldWeAskPermission(Context context, String permission) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(permission, true);
    }

    public static long getLongValue(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        return sharedPref.getLong(key, 0);
    }
}
