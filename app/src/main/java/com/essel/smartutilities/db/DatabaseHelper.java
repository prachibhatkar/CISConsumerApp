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
package com.essel.smartutilities.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.essel.smartutilities.db.tables.AboutUsTable;
import com.essel.smartutilities.db.tables.FAQTable;
import com.essel.smartutilities.db.tables.LoginTable;
import com.essel.smartutilities.db.tables.ManageAccountsTable;
import com.essel.smartutilities.db.tables.TipsTable;

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
                LoginTable.Cols.CONSUMER_CONNECTION_TYPE + " VARCHAR, " +
                LoginTable.Cols.LAST_SYNCED_ON + " LONG, " +
                LoginTable.Cols.LOGIN_ATTEMPTS + " INTEGER";
        createTable(db, LoginTable.TABLE_NAME, loginTableFields);
    }


    private void createManageAccountsTable(SQLiteDatabase db) {
        String ManageAccountsTableFields = ManageAccountsTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ManageAccountsTable.Cols.CONSUMER_ID + " VARCHAR, " +
                ManageAccountsTable.Cols.CONSUMER_NAME + " VARCHAR, " +
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