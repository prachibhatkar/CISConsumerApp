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

import com.essel.smartutilities.activity.LoginActivity;
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
import java.util.List;

import static com.essel.smartutilities.db.DatabaseHelper.dbHelper;


/**
 * This class acts as an interface between database and UI. It contains all the
 * methods to interact with device database.
 */
public class DatabaseManager {

    public static void saveAboutUs(Context context, String aboutUs) {
        if (aboutUs != null) {
            ContentValues values = getContentValuesAboutUsTable(context, aboutUs);
            //String condition = AboutUsTable.Cols.ID + "='" + aboutUs.id + "'";
            saveAboutUs(context, AboutUsTable.CONTENT_URI, values, null);

        }
    }

    public static void saveFAQ(Context context, Faq faq) {
        if (faq != null) {
            ContentValues values = getContentValuesFAQTable(context, faq);
            //String condition = AboutUsTable.Cols.ID + "='" + aboutUs.id + "'";
            saveFAQ(context, FAQTable.CONTENT_URI, values, null);
        }
    }

    public static void saveTips(Context context, Tips tips) {
        if (tips != null) {
            ContentValues values = getContentValuesTipsTable(context, tips);
            //String condition = AboutUsTable.Cols.ID + "='" + aboutUs.id + "'";
            saveTips(context, FAQTable.CONTENT_URI, values, null);
        }
    }

    public static void saveManageAccounts(Context context, ArrayList<Consumer> consumer) {
        if (consumer != null && consumer.size() > 0) {
            for (Consumer consumer1 : consumer) {
                ContentValues values = getContentValuesManageAccountsTable(context, consumer1);
                savemanageaccountsvalues(context, values);
            }
        }
    }

    private static ContentValues getContentValuesManageAccountsTable(Context context, Consumer consumers) {
        ContentValues values = new ContentValues();
        try {
            values.put(ManageAccountsTable.Cols.CONSUMER_ID, consumers.consumer_no);
            values.put(ManageAccountsTable.Cols.CONSUMER_NAME, consumers.consumer_name);
            values.put(ManageAccountsTable.Cols.ADDRESS, consumers.address);
            values.put(ManageAccountsTable.Cols.IS_PRIMARY, consumers.is_primary);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    private static void savemanageaccountsvalues(Context context, ContentValues values) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(ManageAccountsTable.TABLE_NAME, null, values);
        Log.i("Tag", "saveValues:" + newRowId);
    }

    public static ArrayList<Consumer> getAllManageAccounts(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + ManageAccountsTable.TABLE_NAME;
        Cursor c = db.rawQuery(selectQuery, null);
        ArrayList<Consumer> consumers = new ArrayList<Consumer>();

        while (c.moveToNext()) {

            String consumername = c.getString(c.getColumnIndex("consumer_name"));
            String address = c.getString(c.getColumnIndex("address"));
            String consumerno = c.getString(c.getColumnIndex("consumer_id"));
            String is = c.getString(c.getColumnIndex("is_primary"));

            Consumer con = new Consumer(consumername, consumerno, address, is);

            consumers.add(con);
        }
        db.close();
        return consumers;
    }

    public static void deleteAccount(Context context, String Consumer_id) {
        try {
            String condition = ManageAccountsTable.Cols.CONSUMER_ID + "='" + Consumer_id + "'";
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("ManageAccountsTable", condition, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void saveAboutUs(Context context, Uri table, ContentValues values, String condition) {


        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(AboutUsTable.TABLE_NAME, null, values);
        // Log.i("Tag", "saveAboutUs:" + newRowId);
        Log.i("Tag", "saveAboutUs:" + newRowId);
    }

//


    private static void saveFAQ(Context context, Uri table, ContentValues values, String condition) {


        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(FAQTable.TABLE_NAME, null, values);
        // Log.i("Tag", "savefaq:" + newRowId);
    }

    private static void saveTips(Context context, Uri table, ContentValues values, String condition) {

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(TipsTable.TABLE_NAME, null, values);
        // Log.i("Tag", "savetips:" + newRowId);
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
            values.put(TipsTable.Cols.TIPS_IMAGE, tips.image);
            values.put(TipsTable.Cols.TIPS_MESSAGE, tips.message);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }


    public static void saveLoginDetails(Context context, Consumer user_info) {


        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LoginTable.Cols.CONSUMER_NAME, user_info.consumer_name);
        values.put(LoginTable.Cols.CONSUMER_ADDRESS, user_info.address);
        values.put(LoginTable.Cols.CONSUMER_CONTACT_NO, user_info.contact_no);
        values.put(LoginTable.Cols.CONSUMER_ID, user_info.consumer_no);
        values.put(LoginTable.Cols.CONSUMER_CONNECTION_TYPE, user_info.acctype);
        values.put(LoginTable.Cols.CONSUMER_EMAIL_ID, user_info.emailid);
        values.put(LoginTable.Cols.CITY, user_info.city);
        values.put(LoginTable.Cols.IMAGE, user_info.image);

        long v = db.insert(LoginTable.TABLE_NAME, null, values);

        Log.i("Dataaaddedddddddddddddd", String.valueOf(v));
        db.close(); // Closing database connection

    }


    public static AboutUs getAboutUs(Context context) {

        AboutUs aboutUs = new AboutUs();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + AboutUsTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Cursor cursor = db.rawQuery("SELECT * FROM AboutUsTable", null);
        while (cursor.moveToNext()) {

            aboutUs.about_us_msg = cursor.getString(cursor.getColumnIndex("about_us_msg"));
            Log.i("Tag", "valueselectdb" + cursor);

            //aboutUs.about_us_msg=cursor.getString(cursor.getColumnIndex("about_us_msg"));
        }

        return aboutUs;


    }


}