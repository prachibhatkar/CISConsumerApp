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
package com.bynry.cisconsumerapp.db.tables;

import android.net.Uri;

import com.bynry.cisconsumerapp.db.ContentDescriptor;


/**
 * This class describes all necessary info
 * about the UserLogin Table of device database
 *

 */
public class TipsTable {

    public static final String TABLE_NAME = "TipsTable";
    public static final String PATH = "TIPS_TABLE";
    public static final int PATH_TOKEN = 13;
    public static final Uri CONTENT_URI = ContentDescriptor.BASE_URI.buildUpon().appendPath(PATH).build();

    /**
     * This class contains Constants to describe name of Columns of UserLoginTable
     *

     */
    public static class Cols {
        public static final String ID = "_id";
        public static final String TIPS_IMAGE = "tips_image";
        public static final String TIPS_MESSAGE = "tips_message";

    }
}