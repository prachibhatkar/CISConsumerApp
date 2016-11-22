/*
' History Header:      Version         - Date        - Developer Name   - Work Description
' History       :        1.0           - Aug-2016    - Amol Chavan  - Provide methods to handle database interactions
 */

/*
 ##############################################################################################
 #####                                                                                    #####                                                                        
 #####     FILE              : DatabaseManager.Java 	       						      #####                 
 #####     CREATED BY        : Amol Chavan                                                #####
 #####     CREATION DATE     : Aug-2016                                                   #####
 #####                                                                                    #####                                                                              
 #####     MODIFIED  BY      : Amol Chavan                                                #####
 #####     MODIFIED ON       :                                                   	      #####                          
 #####                                                                                    #####                                                                              
 #####     CODE BRIEFING     : DatabaseManager Class.          			   			      #####          
 #####                         Provide methods to handle database interactions			  #####
 #####                                                                                    #####                                                                              
 #####     COMPANY           : Bynari.                                                   #####
 #####                                                                                    #####                                                                              
 ##############################################################################################
 */
package com.essel.smartutilities.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.essel.smartutilities.activity.ManageAccountsActivity;
import com.essel.smartutilities.db.tables.AboutUsTable;
import com.essel.smartutilities.db.tables.FAQTable;
import com.essel.smartutilities.db.tables.LoginTable;
import com.essel.smartutilities.db.tables.ManageAccountsTable;
import com.essel.smartutilities.db.tables.TipsTable;
import com.essel.smartutilities.models.AboutUs;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.Faq;
import com.essel.smartutilities.models.Tips;
import com.essel.smartutilities.models.User;
import com.essel.smartutilities.utility.SharedPrefManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * This class acts as an interface between database and UI. It contains all the
 * methods to interact with device database.
 */
public class DatabaseManager {


    /**
     * Save User to UserLogin table
     *
     * @param context Context
     * @param user    User
     */

    private static Context context;

    public static void saveUser(Context context, User user) {
        if (user != null) {
            ContentValues values = getContentValuesUserLoginTable(context, user);
            String condition = LoginTable.Cols.ID + "='" + user.id + "'";
            saveValues(context, LoginTable.CONTENT_URI, values, condition);
        }
    }


    public static void saveAboutUs(Context context, String aboutUs) {
        if (aboutUs != null) {
            ContentValues values = getContentValuesAboutUsTable(context, aboutUs);
            //String condition = AboutUsTable.Cols.ID + "='" + aboutUs.id + "'";
            saveAboutUs(context,AboutUsTable.CONTENT_URI, values, null);
        }
    }

    public static void saveFAQ(Context context, Faq faq) {
        if (faq!= null) {
            ContentValues values = getContentValuesFAQTable(context, faq);
            //String condition = AboutUsTable.Cols.ID + "='" + aboutUs.id + "'";
            saveFAQ(context, FAQTable.CONTENT_URI, values, null);
        }
    }

    public static void saveTips(Context context, Tips tips) {
        if (tips!= null) {
            ContentValues values = getContentValuesTipsTable(context, tips);
            //String condition = AboutUsTable.Cols.ID + "='" + aboutUs.id + "'";
            saveTips(context,FAQTable.CONTENT_URI, values, null);
        }
    }

    public static void saveManageAccounts(Context context, ArrayList<Consumer> consumer) {
        if (consumer != null && consumer.size() > 0) {
            for (Consumer consumer1 : consumer) {

                ContentValues values = getContentValuesManageAccountsTable(context, consumer1);

                saveValues(context, ManageAccountsTable.CONTENT_URI, values, null);

            }
        }
    }

    private static ContentValues getContentValuesManageAccountsTable(Context context, Consumer consumers) {
        ContentValues values = new ContentValues();
        try {
            values.put(ManageAccountsTable.Cols.CONSUMER_ID, consumers.consumer_no);
            values.put(ManageAccountsTable.Cols.CONSUMER_NAME, consumers.consumer_name);
            values.put(ManageAccountsTable.Cols.ADDRESS, consumers.address);
//            values.put(ManageAccountsTable.Cols.IS_PRIMARY,consumers.is_primary);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    private static void saveValues(Context context, Uri table, ContentValues values, String condition) {
       /* ContentResolver resolver = context.getContentValuesTipsTableontentResolver();
        Cursor cursor = resolver.query(table, null,
                condition, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            resolver.update(table, values, condition, null);
        } else {
            resolver.insert(table, values);
        }

        if (cursor != null) {
            cursor.close();
        }*/

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(ManageAccountsTable.TABLE_NAME, null, values);
        Log.i("Tag", "saveValues:" + newRowId);
    }

    private static void saveAboutUs(Context context, Uri table, ContentValues values, String condition) {
       /* ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(table, null,
                condition, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            resolver.update(table, values, condition, null);
        } else {
            resolver.insert(table, values);
        }

        if (cursor != null) {
            cursor.close();
        }*/

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(AboutUsTable.TABLE_NAME, null, values);
        Log.i("Tag", "saveAboutUs:" + newRowId);
    }


    private static void saveFAQ(Context context, Uri table, ContentValues values, String condition) {
       /* ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(table, null,
                condition, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            resolver.update(table, values, condition, null);
        } else {
            resolver.insert(table, values);
        }

        if (cursor != null) {
            cursor.close();
        }*/

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(FAQTable.TABLE_NAME, null, values);
        Log.i("Tag", "savefaq:" + newRowId);
    }

    private static void saveTips(Context context, Uri table, ContentValues values, String condition) {
       /* ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(table, null,
                condition, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            resolver.update(table, values, condition, null);
        } else {
            resolver.insert(table, values);
        }

        if (cursor != null) {
            cursor.close();
        }*/
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(TipsTable.TABLE_NAME, null, values);
        Log.i("Tag", "savetips:" + newRowId);
    }

    public static User getCurrentLoggedInUser(Context context) {
        String condition = LoginTable.Cols.CONSUMER_EMAIL_ID + "='" + SharedPrefManager.getStringValue(context, SharedPrefManager.USER_NAME) + "' and " + LoginTable.Cols.CONSUMER_ID + "='" + SharedPrefManager.getStringValue(context, SharedPrefManager.USER_ID) + "'";
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(LoginTable.CONTENT_URI, null,
                condition, null, null);
        // ArrayList<User> userList = getUserListFromCurser(cursor);
        User user = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            // userList = new ArrayList<User>();
            while (!cursor.isAfterLast()) {
                user = getUserFromCursor(cursor);

                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return user;
    }

    /**
     * @param context
     * @param uname
     */
    public static ArrayList<User> getUser(Context context,
                                          String uname) {
        String condition = LoginTable.Cols.CONSUMER_EMAIL_ID + "='" + uname + "'";
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(LoginTable.CONTENT_URI, null,
                condition, null, null);
        ArrayList<User> userList = getUserListFromCurser(cursor);
        if (cursor != null) {
            cursor.close();
        }
        return userList;
    }

    private static ArrayList<User> getUserListFromCurser(Cursor cursor) {
        ArrayList<User> userList = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            User user;
            userList = new ArrayList<User>();
            while (!cursor.isAfterLast()) {
                user = getUserFromCursor(cursor);
                userList.add(user);
                cursor.moveToNext();
            }
        }
        return userList;
    }

    @NonNull
    private static User getUserFromCursor(Cursor cursor) {
        User user;
        user = new User();
        user.id = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.CONSUMER_ID));
        user.userName = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.CONSUMER_NAME));
        user.emailId = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.CONSUMER_EMAIL_ID));
        user.activeFlag = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.ACTIVE_FLAG));
        // user.password = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.PASSWORD));
        user.lastsyncedon = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.LAST_SYNCED_ON));
        return user;
    }

    /**
     * Get ContentValues from the Contact to insert it into UserLogin Table
     *
     * @param context Context
     * @param user    User
     */
    private static ContentValues getContentValuesUserLoginTable(Context context, User user) {
        ContentValues values = new ContentValues();
        try {
            values.put(LoginTable.Cols.CONSUMER_ID, user.id);
            values.put(LoginTable.Cols.CONSUMER_NAME, user.userName);
            values.put(LoginTable.Cols.CONSUMER_EMAIL_ID, user.emailId);
            values.put(LoginTable.Cols.ACTIVE_FLAG, user.activeFlag);
            values.put(LoginTable.Cols.LAST_SYNCED_ON, user.lastsyncedon);
            values.put(LoginTable.Cols.LOGIN_ATTEMPTS, "0");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    private static ContentValues getContentValuesAboutUsTable(Context context, String about_us_msg) {
        ContentValues values = new ContentValues();
        try {
            values.put(AboutUsTable.Cols.ABOUT_US_MSG, about_us_msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    private static ContentValues getContentValuesFAQTable(Context context, Faq faq) {
        ContentValues values = new ContentValues();
        try {
           values.put(FAQTable.Cols.FAQ_QUESTION, faq.question);
            values.put(FAQTable.Cols.FAQ_ANSWER, faq.answer);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    private static ContentValues getContentValuesTipsTable(Context context, Tips tips) {
        ContentValues values = new ContentValues();
        try {
            values.put(TipsTable.Cols.TIPS_IMAGE, tips.tip_images);
            values.put(TipsTable.Cols.TIPS_MESSAGE, tips.tip_message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * Clear all the table contents
     *
     * @param context Context
     */
    public static void clearAllCache(Context context) {
        deleteAllUserLoginDetails(context);
    }

    /**
     * Clear contents from UserLogin Table
     *
     * @param context
     */
    public static void deleteAllUserLoginDetails(Context context) {
        try {
            context.getContentResolver().delete(LoginTable.CONTENT_URI, null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public  static AboutUs getAboutUs(Context context){

        AboutUs aboutUs= new AboutUs();
        SQLiteDatabase db  = DatabaseHelper.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM datatable"+"from"+AboutUsTable.TABLE_NAME, null);
        if(cursor!=null){

            aboutUs.about_us_msg=cursor.getString(cursor.getColumnIndex("about_us_msg"));
        }

        return aboutUs;


    }


   /* private static void saveSegement(Context context, String project_id, Segement segement) {
        ContentValues values=getContentValuesUserSegement(context,project_id, segement);
        String condition = SegementTable.Cols.SEGEMENT_ID + "='" + segement.segmentId + "'";
        saveValues(context, SegementTable.CONTENT_URI, values, condition);
    }*/
}