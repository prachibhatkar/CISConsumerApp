package com.essel.smartutilities.models;

import java.util.ArrayList;

/**
 * Created by Admin on 12-09-2016.
 */
public class Response {
  public String page_job_cards_count;
  public String is_next;
  public ArrayList<Consumer> user_data;
  public ArrayList<NotificationCard> notificationcards;
  public ArrayList<String> re_de_assigned_jobcards;
  public String error_code;
}
