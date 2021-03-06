/*
' History Header:      Version         - Date        - Developer Name   - Work Description
' History       :        1.0           - Aug-2016    - Amol Chavan  - ContentProvider for Application database
 */

/*
 ##############################################################################################
 #####                                                                                    #####                                                                        
 #####     FILE              : DatabaseProvider.Java 	       						      #####                 
 #####     CREATED BY        : Amol Chavan                                                #####
 #####     CREATION DATE     : Aug-2015                                                   #####
 #####                                                                                    #####                                                                              
 #####     MODIFIED  BY      : Amol Chavan                                                #####
 #####     MODIFIED ON       :                                                   	      #####                          
 #####                                                                                    #####                                                                              
 #####     CODE BRIEFING     : DatabaseProvider Class.          			   			  #####          
 #####                         ContentProvider for Application database					  #####
 #####                                                                                    #####                                                                              
 #####     COMPANY           : Bynry                                                     #####
 #####                                                                                    #####                                                                              
 ##############################################################################################
 */
package com.bynry.cisconsumerapp.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.bynry.cisconsumerapp.db.tables.ContactUsTable;
import com.bynry.cisconsumerapp.db.tables.AboutUsTable;
import com.bynry.cisconsumerapp.db.tables.ComplaintsTable;
import com.bynry.cisconsumerapp.db.tables.FAQTable;
import com.bynry.cisconsumerapp.db.tables.LoginTable;
import com.bynry.cisconsumerapp.db.tables.ManageAccountsTable;
import com.bynry.cisconsumerapp.db.tables.TariffCatagoryTable;
import com.bynry.cisconsumerapp.db.tables.TariffEnergyChargeTable;
import com.bynry.cisconsumerapp.db.tables.TariffFixedEnergyChargeTable;


/**
 * This class provides Content Provider for application database
 */
public class DatabaseProvider extends ContentProvider {

    private static final String UNKNOWN_URI = "Unknown URI";

    private DatabaseHelper dbHelper;


    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        dbHelper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int token = ContentDescriptor.URI_MATCHER.match(uri);

        Cursor result = null;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doQuery(db, uri, LoginTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }
            case AboutUsTable.PATH_TOKEN: {
                result = doQuery(db, uri, AboutUsTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }

            case ManageAccountsTable.PATH_TOKEN: {
                result = doQuery(db, uri, ManageAccountsTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }

            case FAQTable.PATH_TOKEN: {
                result = doQuery(db, uri, FAQTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }
            case ContactUsTable.PATH_TOKEN: {
                result = doQuery(db, uri, ContactUsTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }

            case ComplaintsTable.PATH_TOKEN: {
                result = doQuery(db, uri, ComplaintsTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }
            case TariffCatagoryTable.PATH_TOKEN: {
                result = doQuery(db, uri, TariffCatagoryTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }
            case TariffEnergyChargeTable.PATH_TOKEN: {
                result = doQuery(db, uri, TariffEnergyChargeTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }

        }
        return result;
    }


    public String getType(Uri uri) {
        return null;
    }


    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = ContentDescriptor.URI_MATCHER.match(uri);

        Uri result = null;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doInsert(db, LoginTable.TABLE_NAME,
                        LoginTable.CONTENT_URI, uri, values);
                break;
            }
            case AboutUsTable.PATH_TOKEN: {
                result = doInsert(db, AboutUsTable.TABLE_NAME,
                        AboutUsTable.CONTENT_URI, uri, values);
                break;
            }
            case ManageAccountsTable.PATH_TOKEN: {
                result = doInsert(db, ManageAccountsTable.TABLE_NAME,
                        ManageAccountsTable.CONTENT_URI, uri, values);
                break;
            }
            case FAQTable.PATH_TOKEN: {
                result = doInsert(db, FAQTable.TABLE_NAME,
                        FAQTable.CONTENT_URI, uri, values);
                break;
            }
            case ContactUsTable.PATH_TOKEN: {
                result = doInsert(db, ContactUsTable.TABLE_NAME,
                        ContactUsTable.CONTENT_URI, uri, values);
                break;
            }
            case ComplaintsTable.PATH_TOKEN: {
                result = doInsert(db, ComplaintsTable.TABLE_NAME,
                       ComplaintsTable.CONTENT_URI, uri, values);
                break;
            }

            case TariffCatagoryTable.PATH_TOKEN: {
                result = doInsert(db, TariffCatagoryTable.TABLE_NAME,
                        TariffCatagoryTable.CONTENT_URI, uri, values);
                break;
            }

            case TariffEnergyChargeTable.PATH_TOKEN: {
                result = doInsert(db, TariffEnergyChargeTable.TABLE_NAME,
                        TariffEnergyChargeTable.CONTENT_URI, uri, values);
                break;
            }
            case TariffFixedEnergyChargeTable.PATH_TOKEN: {
                result = doInsert(db, TariffFixedEnergyChargeTable.TABLE_NAME,
                        TariffFixedEnergyChargeTable.CONTENT_URI, uri, values);
                break;
            }

        }

        if (result == null) {
            throw new IllegalArgumentException(UNKNOWN_URI + uri);
        }

        return result;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        String table = null;
        int token = ContentDescriptor.URI_MATCHER.match(uri);

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                table = LoginTable.TABLE_NAME;
                break;
            }
            case AboutUsTable.PATH_TOKEN: {
                table = AboutUsTable.TABLE_NAME;
                break;
            }
            case ManageAccountsTable.PATH_TOKEN: {
                table = ManageAccountsTable.TABLE_NAME;
                break;
            }
            case FAQTable.PATH_TOKEN: {
                table = FAQTable.TABLE_NAME;
                break;
            }
            case ContactUsTable.PATH_TOKEN: {
                table = ContactUsTable.TABLE_NAME;
                break;
            }
            case ComplaintsTable.PATH_TOKEN: {
                table = ComplaintsTable.TABLE_NAME;
                break;
            }
            case TariffCatagoryTable.PATH_TOKEN: {
                table = TariffCatagoryTable.TABLE_NAME;
                break;
            }
            case TariffEnergyChargeTable.PATH_TOKEN: {
                table = TariffEnergyChargeTable.TABLE_NAME;
                break;
            }
            case TariffFixedEnergyChargeTable.PATH_TOKEN: {
                table = TariffFixedEnergyChargeTable.TABLE_NAME;
                break;
            }
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.beginTransaction();

        for (ContentValues cv : values) {
            db.insert(table, null, cv);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return values.length;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = ContentDescriptor.URI_MATCHER.match(uri);

        int result = 0;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doDelete(db, uri, LoginTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
            case AboutUsTable.PATH_TOKEN: {
                result = doDelete(db, uri, AboutUsTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
            case ManageAccountsTable.PATH_TOKEN: {
                result = doDelete(db, uri, ManageAccountsTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
            case FAQTable.PATH_TOKEN: {
                result = doDelete(db, uri, FAQTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
            case ContactUsTable.PATH_TOKEN: {
                result = doDelete(db, uri, ContactUsTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
            case ComplaintsTable.PATH_TOKEN: {
                result = doDelete(db, uri, ComplaintsTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }

            case TariffCatagoryTable.PATH_TOKEN: {
                result = doDelete(db, uri, TariffCatagoryTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
            case TariffEnergyChargeTable.PATH_TOKEN: {
                result = doDelete(db, uri, TariffCatagoryTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
            case TariffFixedEnergyChargeTable.PATH_TOKEN: {
                result = doDelete(db, uri, TariffFixedEnergyChargeTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
        }

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = ContentDescriptor.URI_MATCHER.match(uri);

        int result = 0;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doUpdate(db, uri, LoginTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
            case AboutUsTable.PATH_TOKEN: {
                result = doUpdate(db, uri, AboutUsTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
            case ManageAccountsTable.PATH_TOKEN: {
                result = doUpdate(db, uri, ManageAccountsTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
            case FAQTable.PATH_TOKEN: {
                result = doUpdate(db, uri, FAQTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
            case ContactUsTable.PATH_TOKEN: {
                result = doUpdate(db, uri, ContactUsTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
            case ComplaintsTable.PATH_TOKEN: {
                result = doUpdate(db, uri, ComplaintsTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
            case TariffCatagoryTable.PATH_TOKEN: {
                result = doUpdate(db, uri, TariffCatagoryTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
            case TariffEnergyChargeTable.PATH_TOKEN: {
                result = doUpdate(db, uri, TariffEnergyChargeTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
            case TariffFixedEnergyChargeTable.PATH_TOKEN: {
                result = doUpdate(db, uri, TariffFixedEnergyChargeTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
        }

        return result;
    }

    /**
     * Performs query to specified table using the projection, selection and
     * sortOrder
     *
     * @param db            SQLiteDatabase instance
     * @param uri           ContentUri for watch
     * @param tableName     Name of table on which query has to perform
     * @param projection    needed projection
     * @param selection     needed selection cases
     * @param selectionArgs needed selection arguments
     * @param sortOrder     sort order if necessary
     * @return Cursor cursor from the table tableName matching all the criterion
     */
    private Cursor doQuery(SQLiteDatabase db, Uri uri, String tableName,
                           String[] projection, String selection, String[] selectionArgs,
                           String sortOrder) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(tableName);
        Cursor result = builder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);

        result.setNotificationUri(getContext().getContentResolver(), uri);

        return result;
    }

    /**
     * performs update to the specified table row or rows
     *
     * @param db            SQLiteDatabase instance
     * @param uri           uri of the content that was changed
     * @param tableName     Name of table on which query has to perform
     * @param selection     needed selection cases
     * @param selectionArgs needed selection arguments
     * @param values        content values to update
     * @return success or failure
     */
    private int doUpdate(SQLiteDatabase db, Uri uri, String tableName,
                         String selection, String[] selectionArgs, ContentValues values) {
        int result = db.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    /**
     * deletes the row/rows from the table
     *
     * @param db            SQLiteDatabase instance
     * @param uri           uri of the content that was changed
     * @param tableName     Name of table on which query has to perform
     * @param selection     needed selection cases
     * @param selectionArgs needed selection arguments
     * @return success or failure
     */
    private int doDelete(SQLiteDatabase db, Uri uri, String tableName,
                         String selection, String[] selectionArgs) {
        int result = db.delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    /**
     * insert rows to the specified table
     *
     * @param db         SQLiteDatabase instance
     * @param tableName  Name of table on which query has to perform
     * @param contentUri ContentUri to build the path
     * @param uri        uri of the content that was changed
     * @param values     content values to update
     * @return success or failure
     */
    private Uri doInsert(SQLiteDatabase db, String tableName, Uri contentUri,
                         Uri uri, ContentValues values) {
        long id = db.insert(tableName, null, values);
        Uri result = contentUri.buildUpon().appendPath(String.valueOf(id))
                .build();
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }
}