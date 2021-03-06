/*
' History Header:      Version         - Date        - Developer Name   - Work Description
' History       :        1.0           - Aug-2016    - Amol Chavan  - SqliteOpenHeler class for app database
 */

/*
 ##############################################################################################
 #####                                                                                    #####                                                                        
 #####     FILE              : DatabaseHelper.Java 	       						          #####                 
 #####     CREATED BY        : Amol Chavan                                                #####
 #####     CREATION DATE     : Aug-2016                                                   #####
 #####                                                                                    #####                                                                              
 #####     MODIFIED  BY      : Amol Chavan                                                #####
 #####     MODIFIED ON       :                                                   	      #####                          
 #####                                                                                    #####                                                                              
 #####     CODE BRIEFING     : DatabaseHelper Class.          			   			      #####          
 #####                         SqliteOpenHeler class for application database			  #####
 #####                                                                                    #####                                                                              
 #####     COMPANY           : Bynary.                                                   #####
 #####                                                                                    #####                                                                              
 ##############################################################################################
 */
package com.bynry.cisconsumerapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bynry.cisconsumerapp.db.tables.ComplaintsTable;
import com.bynry.cisconsumerapp.db.tables.ContactUsTable;
import com.bynry.cisconsumerapp.db.tables.LoginTable;
import com.bynry.cisconsumerapp.db.tables.ManageAccountsTable;
import com.bynry.cisconsumerapp.db.tables.TariffEnergyChargeTable;
import com.bynry.cisconsumerapp.db.tables.AboutUsTable;
import com.bynry.cisconsumerapp.db.tables.FAQTable;
import com.bynry.cisconsumerapp.db.tables.NotificationTable;
import com.bynry.cisconsumerapp.db.tables.TariffCatagoryTable;
import com.bynry.cisconsumerapp.db.tables.TariffFixedEnergyChargeTable;
import com.bynry.cisconsumerapp.db.tables.TipsTable;

import java.text.MessageFormat;


/**
 * SqliteOpenHeler class for application database
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String KEY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS {0} ({1})";
    public static final String KEY_DROP_TABLE = "DROP TABLE IF EXISTS {0}";

    public final static String SQL = "SELECT COUNT(*) FROM sqlite_master WHERE name=?";
    public static final String TAG = "DatabaseHelper";
    private static final int CURRENT_DB_VERSION = 1;
    private static final String DB_NAME = "MRBD.db";
    public static DatabaseHelper dbHelper;


    /**
     * Constructor using context for the class
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, CURRENT_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createLoginTable(db);
        createAboutUsTable(db);
        createManageAccountsTable(db);
        createFaqTable(db);
        createTipsTable(db);
        createContactUsTable(db);
        createComplaintsTable(db);
        createTariffTable(db);
        createTariffEnergyChargeTable(db);
        createTariffFixedEnergyChargeTable(db);
        createNotificationTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropTable(sqLiteDatabase, LoginTable.TABLE_NAME);
    }

    /**
     * creates UserLogin in device database
     *
     * @param db SqliteDatabase instance
     */
    private void createLoginTable(SQLiteDatabase db) {
        String loginTableFields = LoginTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                LoginTable.Cols.CONSUMER_ID + " VARCHAR, " +
                LoginTable.Cols.CONSUMER_NAME + " VARCHAR, " +
                LoginTable.Cols.CONSUMER_EMAIL_ID + " VARCHAR, " +
                LoginTable.Cols.CITY + " VARCHAR, " +
                LoginTable.Cols.IMAGE + " VARCHAR, " +
                LoginTable.Cols.CONSUMER_ADDRESS + " VARCHAR, " +
                LoginTable.Cols.CONSUMER_CONTACT_NO + " VARCHAR, " +
                LoginTable.Cols.CONSUMER_ALTERNATE_CONTACT_NO + " VARCHAR, " +
                LoginTable.Cols.CONSUMER_CONNECTION_TYPE + " VARCHAR, " +
                LoginTable.Cols.LAST_SYNCED_ON + " LONG, " +
                LoginTable.Cols.LOGIN_ATTEMPTS + " INTEGER";
        createTable(db, LoginTable.TABLE_NAME, loginTableFields);
    }

    private void createNotificationTable(SQLiteDatabase db) {
        String notificationTableFields = NotificationTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NotificationTable.Cols.TITLE+ " VARCHAR, " +
                NotificationTable.Cols.MSG + " VARCHAR, " +
                NotificationTable.Cols.DATE + " VARCHAR, " +
                NotificationTable.Cols.IS_READED + " VARCHAR " ;

        createTable(db, NotificationTable.TABLE_NAME, notificationTableFields);
    }

    private void createManageAccountsTable(SQLiteDatabase db) {
        String ManageAccountsTableFields = ManageAccountsTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ManageAccountsTable.Cols.CONSUMER_ID + " VARCHAR, " +
                ManageAccountsTable.Cols.CONSUMER_NAME + " VARCHAR, " +
                ManageAccountsTable.Cols.CONTACT_NO + " VARCHAR, " +
                ManageAccountsTable.Cols.ALTERNATE_CONTACT_NO + " VARCHAR, " +
                ManageAccountsTable.Cols.ALTERNATE_EMAIL_ID + " VARCHAR, " +
                ManageAccountsTable.Cols.CITY + " VARCHAR, " +
                ManageAccountsTable.Cols.IS_PRIMARY + " VARCHAR, " +
                ManageAccountsTable.Cols.ADDRESS + " VARCHAR";
        createTable(db, ManageAccountsTable.TABLE_NAME, ManageAccountsTableFields);
    }

    private void createAboutUsTable(SQLiteDatabase db) {
        String aboutusTableFields = AboutUsTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AboutUsTable.Cols.ABOUT_US_MSG + " VARCHAR ";
        createTable(db, AboutUsTable.TABLE_NAME, aboutusTableFields);
    }
    private void createFaqTable(SQLiteDatabase db) {
        String faqTableFields = FAQTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
               FAQTable.Cols.FAQ_QUESTION + " VARCHAR ," +
                 FAQTable.Cols.FAQ_ANSWER + " VARCHAR ";
        createTable(db, FAQTable.TABLE_NAME, faqTableFields);
    }
    private void createTipsTable(SQLiteDatabase db) {
        String tipsTableFields = TipsTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TipsTable.Cols.TIPS_MESSAGE + " VARCHAR ,"+
                TipsTable.Cols.TIPS_IMAGE + " VARCHAR ";
        createTable(db, TipsTable.TABLE_NAME, tipsTableFields);
    }


    private void createContactUsTable(SQLiteDatabase db) {
        String contactusTableFields = ContactUsTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContactUsTable.Cols.HELPLINE_NUMBER+ " VARCHAR, " +
                ContactUsTable.Cols.ANTI_BERIBERY_HELP + " VARCHAR, " +
                ContactUsTable.Cols.ONLINE_COMPLAINTS + " VARCHAR, " +
                ContactUsTable.Cols.CONSUMER_PORTAL + " VARCHAR, " +
                ContactUsTable.Cols.IGRC_EMAIL + " VARCHAR, " +
                ContactUsTable.Cols.ELECTRICITY_THEFT_HELP + " VARCHAR, " +
                ContactUsTable.Cols.IGRC_NUMBER + " VARCHAR ";
        createTable(db, ContactUsTable.TABLE_NAME, contactusTableFields);
    }

    private void createComplaintsTable(SQLiteDatabase db) {
        String complaintsTableFields = ComplaintsTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ComplaintsTable.Cols.COMPLAINT_TYPE+ " VARCHAR, " +
                ComplaintsTable.Cols.COMPLAINT_REMARK + " VARCHAR, " +
                ComplaintsTable.Cols.COMPLAINT_IMG + " VARCHAR, " +
                ComplaintsTable.Cols.COMPLAINT_Id + " VARCHAR " ;
        createTable(db,  ComplaintsTable.TABLE_NAME, complaintsTableFields);
    }
    private void createTariffTable(SQLiteDatabase db) {
        String tariffTableFields = TariffCatagoryTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TariffCatagoryTable.Cols.TARIFF_CHARGE+ " VARCHAR, " +
                TariffCatagoryTable.Cols.TARIFF_SLAB + " VARCHAR " ;
        createTable(db,  TariffCatagoryTable.TABLE_NAME, tariffTableFields);
    }
    private void createTariffEnergyChargeTable(SQLiteDatabase db) {
        String tariffenergychargeTableFields = TariffEnergyChargeTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TariffEnergyChargeTable.Cols.TARIFF_CHARGE+ " VARCHAR, " +
                TariffEnergyChargeTable.Cols.TARIFF_SLAB + " VARCHAR " ;
        createTable(db,  TariffEnergyChargeTable.TABLE_NAME,tariffenergychargeTableFields);
    }

    private void createTariffFixedEnergyChargeTable(SQLiteDatabase db) {
        String tarifffixedenergychargeTableFields = TariffFixedEnergyChargeTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TariffFixedEnergyChargeTable.Cols.TARIFF_CHARGE+ " VARCHAR, " +
                TariffFixedEnergyChargeTable.Cols.TARIFF_SLAB + " VARCHAR " ;
        createTable(db,  TariffFixedEnergyChargeTable.TABLE_NAME, tarifffixedenergychargeTableFields);
    }

    /**
     * Drops Table from device database
     *
     * @param db   SqliteDatabase instance
     * @param name TableName
     */
    public void dropTable(SQLiteDatabase db, String name) {
        String query = MessageFormat.format(DatabaseHelper.KEY_DROP_TABLE, name);
        db.execSQL(query);
    }

    public static boolean exists(SQLiteDatabase db, String name) {
        Log.d(TAG, "Checking tables:" + name);
        Cursor cur = db.rawQuery(SQL, new String[]{name});
        cur.moveToFirst();
        int tables = cur.getInt(0);
        if (tables > 0) {
            Log.d(TAG, "table:" + name + " does exsit");
            return true;
        } else {
            Log.d(TAG, "table:" + name + " does not exsit");
            return false;
        }
    }

    /**
     * Creates Table in device database
     *
     * @param db     SqliteDatabase instance
     * @param name   TableName
     * @param fields ColumnFields
     */
    public void createTable(SQLiteDatabase db, String name, String fields) {
        String query = MessageFormat.format(DatabaseHelper.KEY_CREATE_TABLE,
                name, fields);
        db.execSQL(query);
    }


}