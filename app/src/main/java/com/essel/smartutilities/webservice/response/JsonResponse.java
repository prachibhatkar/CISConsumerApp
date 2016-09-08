package com.essel.smartutilities.webservice.response;


import com.essel.smartutilities.webservice.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SANDEEP on 11/28/2015.
 */
public class JsonResponse extends BaseResponse {

    String mPhone;
    String mFirst_name;
    String mState;
    String mUser_id;
    String mSms_alert;
    String mEmail_alert;
    String mUser_profile_image;
    String mEmail_id;
    String mAddress;
    String mCity;

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhon(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmFirst_name() {
        return mFirst_name;
    }

    public void setmFirst_name(String mFirst_name) {
        this.mFirst_name = mFirst_name;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmUser_id() {
        return mUser_id;
    }

    public void setmUser_id(String mUser_id) {
        this.mUser_id = mUser_id;
    }

    public String getmSms_alert() {
        return mSms_alert;
    }

    public void setmSms_alert(String mSms_alert) {
        this.mSms_alert = mSms_alert;
    }

    public String getmEmail_alert() {
        return mEmail_alert;
    }

    public void setmEmail_alert(String mEmail_alert) {
        this.mEmail_alert = mEmail_alert;
    }

    public String getmUser_profile_image() {
        return mUser_profile_image;
    }

    public void setmUser_profile_image(String mUser_profile_image) {
        this.mUser_profile_image = mUser_profile_image;
    }

    public String getmEmail_id() {
        return mEmail_id;
    }

    public void setmEmail_id(String mEmail_id) {
        this.mEmail_id = mEmail_id;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public static JsonResponse fromJson(String pResult)  {
    try {
        JSONObject objJson= new JSONObject(pResult);

        JsonResponse obj=new JsonResponse();

        JSONObject user_info=objJson.getJSONObject("user_info");
        JSONObject basic_info=user_info.getJSONObject("basic");
        obj.setmUser_id(basic_info.getString("user_id"));

        return obj;
    } catch (JSONException e) {
        e.printStackTrace();
    }
        return null;
    }
}
