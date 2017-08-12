

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
package com.bynry.cisconsumerapp.db;

import android.content.UriMatcher;
import android.net.Uri;

import com.bynry.cisconsumerapp.db.tables.ContactUsTable;
import com.bynry.cisconsumerapp.db.tables.ManageAccountsTable;
import com.bynry.cisconsumerapp.db.tables.TariffEnergyChargeTable;
import com.bynry.cisconsumerapp.db.tables.AboutUsTable;
import com.bynry.cisconsumerapp.db.tables.ComplaintsTable;
import com.bynry.cisconsumerapp.db.tables.FAQTable;
import com.bynry.cisconsumerapp.db.tables.LoginTable;
import com.bynry.cisconsumerapp.db.tables.NotificationTable;
import com.bynry.cisconsumerapp.db.tables.TariffCatagoryTable;
import com.bynry.cisconsumerapp.db.tables.TariffFixedEnergyChargeTable;
import com.bynry.cisconsumerapp.db.tables.TipsTable;


/**
 * This class contains description about
 * application database content providers
 */
public class ContentDescriptor {

    public static final String AUTHORITY = "com.bynry.cisconsumerapp.smartutilities";
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
        matcher.addURI(AUTHORITY, FAQTable.PATH, FAQTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, TipsTable.PATH, TipsTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ContactUsTable.PATH, ContactUsTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ComplaintsTable.PATH, ComplaintsTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, TariffCatagoryTable.PATH, TariffCatagoryTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, TariffEnergyChargeTable.PATH, TariffEnergyChargeTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, TariffFixedEnergyChargeTable.PATH, TariffFixedEnergyChargeTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, NotificationTable.PATH, NotificationTable.PATH_TOKEN);

        return matcher;
    }
}