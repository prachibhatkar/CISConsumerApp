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
public class ComplaintsTable {

    public static final String TABLE_NAME = "ComplaintsTable";
    public static final String PATH = "COMPLAINTS_TABLE";
    public static final int PATH_TOKEN = 19;
    public static final Uri CONTENT_URI = ContentDescriptor.BASE_URI.buildUpon().appendPath(PATH).build();

    /**
     * This class contains Constants to describe name of Columns
     *

     */
    public static class Cols {
        public static final String ID = "_id";
        public static final String COMPLAINT_TYPE = "complaint_type";
        public static final String COMPLAINT_REMARK = "complaint_remark";
        public static final String COMPLAINT_IMG = "complaint_img";
        public static final String COMPLAINT_Id = "complaint_id";


    }
}