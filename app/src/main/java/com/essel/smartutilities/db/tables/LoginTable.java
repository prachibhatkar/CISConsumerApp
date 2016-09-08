/*
' History Header:      Version         - Date        - Developer Name   - Work Description
' History       :        1.0           - Aug-2016    - Amol Chavan  - class describes all necessary info about the UserLogin Table Table
 */

/*
 ##############################################################################################
 #####                                                                                    #####
 #####     FILE              : LoginTable.Java 	       								      #####
 #####     CREATED BY        : Amol Chavan                                                #####
 #####     CREATION DATE     : Aug-2016                                                   #####
 #####                                                                                    #####
 #####     MODIFIED  BY      : Amol Chavan                                                #####
 #####     MODIFIED ON       :                                                   	      #####
 #####                                                                                    #####
 #####     CODE BRIEFING     : LoginTable Class.         		 			   	          #####
 #####                         class describes all necessary info about LoginTable        #####
 #####                                                                                    #####
 #####     COMPANY           : Bynry.                                                     #####
 #####                                                                                    #####
 ##############################################################################################
 */
package com.essel.smartutilities.db.tables;

import android.net.Uri;

import com.essel.smartutilities.db.ContentDescriptor;


/**
 * This class describes all necessary info
 * about the UserLogin Table of device database
 *
 * @author Amol Chavan
 */
public class LoginTable {

    public static final String TABLE_NAME = "LoginTable";
    public static final String PATH = "LOGIN_TABLE";
    public static final int PATH_TOKEN = 10;
    public static final Uri CONTENT_URI = ContentDescriptor.BASE_URI.buildUpon().appendPath(PATH).build();

    /**
     * This class contains Constants to describe name of Columns of UserLoginTable
     *
     * @author Amol Chavan
     */
    public static class Cols {
        public static final String ID = "_id";
        public static final String USER_ID = "userid";
        public static final String USER_NAME = "username";
        public static final String USER_EMAIL_ID = "emailId";
        public static final String ACTIVE_FLAG = "activeFlag";
        public static final String LAST_SYNCED_ON = "last_synced_on";
        public static final String LOGIN_ATTEMPTS = "login_attempts";
    }
}