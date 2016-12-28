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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.essel.smartutilities.db.tables.AboutUsTable;
import com.essel.smartutilities.db.tables.ComplaintsTable;
import com.essel.smartutilities.db.tables.ContactUsTable;
import com.essel.smartutilities.db.tables.FAQTable;
import com.essel.smartutilities.db.tables.LoginTable;
import com.essel.smartutilities.db.tables.ManageAccountsTable;
import com.essel.smartutilities.db.tables.TipsTable;
import com.essel.smartutilities.models.AboutUs;
import com.essel.smartutilities.models.Complaints;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.ContactUs;
import com.essel.smartutilities.models.Faq;
import com.essel.smartutilities.models.GetInfo;
import com.essel.smartutilities.models.Tips;
import com.essel.smartutilities.models.User;

import java.util.ArrayList;


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

    public static void saveContactDetail(Context context, ContactUs contactus) {
        if (contactus != null) {
            ContentValues values = getContentValuesContactUsTable(context, contactus);
            savecontactdetail(context, ContactUsTable.CONTENT_URI, values, null);

        }
    }

    public static void saveComplaint(Context context, Complaints complaint) {
        if (complaint != null) {
            ContentValues values = getContentValuesComplaintsTable(context, complaint);
            savecomplaint(context, ComplaintsTable.CONTENT_URI, values, null);

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
            saveTips(context, TipsTable.CONTENT_URI, values, null);
        }
    }

    public static void saveManageAccounts(Context context, ArrayList<Consumer> consumer) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ManageAccountsTable.TABLE_NAME, null, null);
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
            values.put(ManageAccountsTable.Cols.CITY, consumers.city);
            values.put(ManageAccountsTable.Cols.CONTACT_NO, consumers.contact_no);
            values.put(ManageAccountsTable.Cols.ALTERNATE_CONTACT_NO, consumers.alternet_contact_no);
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

//    public static void deleteAccount(Context context, String Consumer_id) {
//        try {
//            String condition = ManageAccountsTable.Cols.CONSUMER_ID + "='" + Consumer_id + "'";
//            DatabaseHelper dbHelper = new DatabaseHelper(context);
//            SQLiteDatabase db = dbHelper.getWritableDatabase();
//            db.delete("ManageAccountsTable", condition, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private static void saveAboutUs(Context context, Uri table, ContentValues values, String condition) {


        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(AboutUsTable.TABLE_NAME, null, values);
        // Log.i("Tag", "saveAboutUs:" + newRowId);
        Log.i("Tag", "saveAboutUs:" + newRowId);
    }


    private static void savecontactdetail(Context context, Uri table, ContentValues values, String condition) {


        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(ContactUsTable.TABLE_NAME, null, values);
        // Log.i("Tag", "saveAboutUs:" + newRowId);
        Log.i("Tag", "savecontactdetail:" + newRowId);
    }


    private static void savecomplaint(Context context, Uri table, ContentValues values, String condition) {


        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(ComplaintsTable.TABLE_NAME, null, values);
        // Log.i("Tag", "saveAboutUs:" + newRowId);
        Log.i("Tag", "savecomplaint:" + newRowId);
    }

//


    private static void saveFAQ(Context context, Uri table, ContentValues values, String condition) {


        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newRowId = db.insert(FAQTable.TABLE_NAME, null, values);
        Log.i("Tag", "savefaq:" + newRowId);
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

    private static ContentValues getContentValuesContactUsTable(Context context, ContactUs contactus) {
        ContentValues values = new ContentValues();
        try {
            values.put(ContactUsTable.Cols.HELPLINE_NUMBER, contactus.helpline_number);
            values.put(ContactUsTable.Cols.ANTI_BERIBERY_HELP, contactus.anti_bribery_help);
            values.put(ContactUsTable.Cols.ONLINE_COMPLAINTS, contactus.online_complaint);
            values.put(ContactUsTable.Cols.IGRC_EMAIL, contactus.igrc_email);
            values.put(ContactUsTable.Cols.CONSUMER_PORTAL, contactus.customer_portal);
            values.put(ContactUsTable.Cols.ELECTRICITY_THEFT_HELP, contactus.electricity_theft_help_no);
            values.put(ContactUsTable.Cols.IGRC_NUMBER, contactus.igrc_no);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }


    private static ContentValues getContentValuesComplaintsTable(Context context, Complaints complaints) {
        ContentValues values = new ContentValues();
        try {
            values.put(ComplaintsTable.Cols.COMPLAINT_TYPE, complaints.type);
            values.put(ComplaintsTable.Cols.COMPLAINT_REMARK, complaints.remark);
            values.put(ComplaintsTable.Cols.COMPLAINT_IMG, complaints.img);
            values.put(ComplaintsTable.Cols.COMPLAINT_Id, complaints.complaintid);


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
//            values.put(FAQTable.Cols.FAQ_QUESTION, String.valueOf(faq.arrayquestion));
//            values.put(FAQTable.Cols.FAQ_ANSWER, String.valueOf(faq.arrayanswer));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    private static ContentValues getContentValuesTipsTable(Context context, Tips tips) {
        ContentValues values = new ContentValues();
        try {
            // values.put(TipsTable.Cols.TIPS_IMAGE, tips.image);
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
        values.put(LoginTable.Cols.CONSUMER_ALTERNATE_CONTACT_NO, user_info.alternet_contact_no);
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

    public static Faq getFaq(Context context) {

        Faq faq = new Faq();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + FAQTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Cursor cursor = db.rawQuery("SELECT * FROM AboutUsTable", null);
        while (cursor.moveToNext()) {

            faq.answer = (cursor.getString(cursor.getColumnIndex("faq_answer")));
            faq.question = (cursor.getString(cursor.getColumnIndex("faq_question")));
            Log.i("Tag", "valueselectdb" + cursor);

            //aboutUs.about_us_msg=cursor.getString(cursor.getColumnIndex("about_us_msg"));
        }

        return faq;


    }


    public static ContactUs contactUs(Context context) {

        ContactUs con = new ContactUs();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + ContactUsTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Cursor cursor = db.rawQuery("SELECT * FROM AboutUsTable", null);
        while (cursor.moveToNext()) {

            con.helpline_number = (cursor.getString(cursor.getColumnIndex("helpline_number")));
            con.anti_bribery_help = (cursor.getString(cursor.getColumnIndex("anti_beribery_help")));
            con.online_complaint = (cursor.getString(cursor.getColumnIndex("online_complaint")));
            con.electricity_theft_help_no = (cursor.getString(cursor.getColumnIndex("electricity_theft_help")));
            con.igrc_email = (cursor.getString(cursor.getColumnIndex("igrc_email")));
            con.igrc_no = (cursor.getString(cursor.getColumnIndex("igrc_number")));
            con.customer_portal = (cursor.getString(cursor.getColumnIndex("consumer_portal")));
            Log.i("Tag", "valueselectdb" + cursor);

            //aboutUs.about_us_msg=cursor.getString(cursor.getColumnIndex("about_us_msg"));
        }

        return con;


    }

    public static Tips getTips(Context context) {

        Tips tips = new Tips();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TipsTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Cursor cursor = db.rawQuery("SELECT * FROM AboutUsTable", null);
        while (cursor.moveToNext()) {

            tips.message = cursor.getString(cursor.getColumnIndex("tips_message"));
            Log.i("Tag", "valueselectdb" + cursor);

            //aboutUs.about_us_msg=cursor.getString(cursor.getColumnIndex("about_us_msg"));
        }

        return tips;


    }

    public static GetInfo getinfo(Context context, String consumerno) {

        GetInfo getinfo = new GetInfo();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //  String name = getinfo.consumerno.toString();
        String selectQuery = "SELECT contact_no FROM ManageAccountsTable WHERE consumer_id = '" + consumerno + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Cursor cursor = db.rawQuery("SELECT * FROM AboutUsTable", null);
        while (cursor.moveToNext()) {

            // getinfo.consumerno = cursor.getString(cursor.getColumnIndex("consumer_id"));
            getinfo.mobileno = cursor.getString(cursor.getColumnIndex("contact_no"));
            Log.i("Tag", "valueselectdb" + cursor);

            //aboutUs.about_us_msg=cursor.getString(cursor.getColumnIndex("about_us_msg"));
        }

        return getinfo;


    }


    public static GetInfo getProfileinfo(Context context, String isprimary) {

        GetInfo getinfo = new GetInfo();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //  String name = getinfo.consumerno.toString();
        String selectQuery = "SELECT * FROM ManageAccountsTable WHERE is_primary = '" + isprimary + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Cursor cursor = db.rawQuery("SELECT * FROM AboutUsTable", null);
        while (cursor.moveToNext()) {

            // getinfo.consumerno = cursor.getString(cursor.getColumnIndex("consumer_id"));
            getinfo.mobileno = cursor.getString(cursor.getColumnIndex("contact_no"));
            getinfo.consumerno = cursor.getString(cursor.getColumnIndex("consumer_id"));
            getinfo.consumername = cursor.getString(cursor.getColumnIndex("consumer_name"));
            getinfo.consumeraddress = cursor.getString(cursor.getColumnIndex("address"));
            Log.i("Tag", "valueselectdb" + cursor);

            //aboutUs.about_us_msg=cursor.getString(cursor.getColumnIndex("about_us_msg"));
        }

        return getinfo;


    }

    public static ContactUs getContactDetail(Context context) {

        ContactUs contactus = new ContactUs();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + ContactUsTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()) {

            contactus.helpline_number = cursor.getString(cursor.getColumnIndex("helpline_number"));
            contactus.anti_bribery_help = cursor.getString(cursor.getColumnIndex("anti_beribery_help"));
            contactus.online_complaint = cursor.getString(cursor.getColumnIndex("online_complaint"));
            contactus.igrc_email = cursor.getString(cursor.getColumnIndex("igrc_email"));
            contactus.igrc_no = cursor.getString(cursor.getColumnIndex("igrc_number"));
            contactus.customer_portal = cursor.getString(cursor.getColumnIndex("consumer_portal"));
            contactus.electricity_theft_help_no = cursor.getString(cursor.getColumnIndex("electricity_theft_help"));
            Log.i("Tag", "valueselectdb" + cursor);

            //aboutUs.about_us_msg=cursor.getString(cursor.getColumnIndex("about_us_msg"));
        }

        return contactus;


    }


}