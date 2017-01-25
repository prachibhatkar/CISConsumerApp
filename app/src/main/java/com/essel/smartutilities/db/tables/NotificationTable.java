package com.essel.smartutilities.db.tables;

import android.net.Uri;

import com.essel.smartutilities.db.ContentDescriptor;

/**
 * Created by Admin on 18-01-2017.
 */

public class NotificationTable
{
    public static final String TABLE_NAME = "Notification";
    public static final String PATH = "Notification_TABLE";
    public static final int PATH_TOKEN = 1;
    public static final Uri CONTENT_URI = ContentDescriptor.BASE_URI.buildUpon().appendPath(PATH).build();

    /**
     * This class contains Constants to describe name of Columns of UserLoginTable
     *

     */
    public static class Cols {
        public static final String ID = "_id";
        public static final String MSG = "msg";
        public static final String TITLE = "title";
        public static final String DATE = "date";
        public static final String IS_READED="is_readed";

    }
}

