package com.bynry.cisconsumerapp.db.tables;

import android.net.Uri;

import com.bynry.cisconsumerapp.db.ContentDescriptor;

/**
 * Created by hp on 11/17/2016.
 */

public class ManageAccountsTable {

    public static final String TABLE_NAME = "ManageAccountsTable";
    public static final String PATH = "MANAGEACCOUNTS_TABLE";
    public static final int PATH_TOKEN = 20;
    public static final Uri CONTENT_URI = ContentDescriptor.BASE_URI.buildUpon().appendPath(PATH).build();

    public static class Cols {
        public static final String ID = "_id";
        public static final String CONSUMER_ID = "consumer_id";
        public static final String CONSUMER_NAME = "consumer_name";
        public static final String CONTACT_NO = "contact_no";
        public static final String ALTERNATE_CONTACT_NO = "alternate_contact_no";
        public static final String ALTERNATE_EMAIL_ID = "alternate_email_id";
        public static final String CITY="city";
        public static final String ADDRESS = "address";
        public static final String IS_PRIMARY = "is_primary";
    }

}
 