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

 */
public class ContactUsTable {

    public static final String TABLE_NAME = "ContactUsTable";
    public static final String PATH = "ContactUs_TABLE";
    public static final int PATH_TOKEN = 15;
    public static final Uri CONTENT_URI = ContentDescriptor.BASE_URI.buildUpon().appendPath(PATH).build();

    /**
     * This class contains Constants to describe name of Columns
     *

     */
    public static class Cols {
        public static final String ID = "_id";
        public static final String HELPLINE_NUMBER = "helpline_number";
        public static final String ANTI_BERIBERY_HELP = "anti_beribery_help";
        public static final String ONLINE_COMPLAINTS = "online_complaint";
        public static final String IGRC_EMAIL = "igrc_email";
        public static final String CONSUMER_PORTAL = "consumer_portal";
        public static final String ELECTRICITY_THEFT_HELP = "electricity_theft_help";
        public static final String IGRC_NUMBER = "igrc_number";

    }
}