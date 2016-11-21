

/*
' History Header:      Version         - Date        - Developer Name   - Work Description
' History       :        1.0           - Aug-2016   - Amol Chavan  - Class contains application database content provider description
 */

/*
 ##############################################################################################
 #####                                                                                    #####                                                                        
 #####     FILE              : ContentDescriptor.Java 	      		 			          #####                 
 #####     CREATED BY        : Amol Chavan                                                #####
 #####     CREATION DATE     : Aug-2016                                                  #####
 #####                                                                                    #####                                                                              
 #####     MODIFIED  BY      : Amol Chavan                                                #####
 #####     MODIFIED ON       :                                                   	      #####                          
 #####                                                                                    #####                                                                              
 #####     CODE BRIEFING     : ContentDescriptor Class.      		       			      #####          
 #####                         Class contains application database  					  #####
 #####						   content provider description								  #####
 #####                                                                                    #####                                                                              
 #####     COMPANY           : Bynari.                                                    #####
 #####                                                                                    #####                                                                              
 ##############################################################################################
 */
package com.essel.smartutilities.db;

import android.content.UriMatcher;
import android.net.Uri;

import com.essel.smartutilities.db.tables.AboutUsTable;
import com.essel.smartutilities.db.tables.LoginTable;
import com.essel.smartutilities.db.tables.ManageAccountsTable;


/**
 * This class contains description about
 * application database content providers
 *

 */
public class ContentDescriptor {

    public static final String AUTHORITY = "com.essel.smartutilities";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();


    /**
     * @return UriMatcher for database table Uris
     */
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHORITY, LoginTable.PATH, LoginTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ManageAccountsTable.PATH, ManageAccountsTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, AboutUsTable.PATH, AboutUsTable.PATH_TOKEN);

        return matcher;
    }
}