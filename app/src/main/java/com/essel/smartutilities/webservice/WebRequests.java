package com.essel.smartutilities.webservice;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import com.essel.smartutilities.activity.FAQActivity;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.fragments.Contact_Details_Fragment;
import com.essel.smartutilities.models.JsonResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 22-08-2016.
 */
public class WebRequests {

    private static final String TAG = "WebRequests";

    public static JsonObjectRequest loginRequest(Context context, int request_type, String url, final String label, final ServiceCaller caller, final String consumerno, final String password) {
        final Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("username", consumerno);
        postParam.put("password", password);
        final JSONObject jsonObject = new JSONObject(postParam);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException | JsonSyntaxException e1) {
                        // e1.printStackTrace();
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;
    }


    public static JsonObjectRequest addNewConnectionRequest(Context context, int request_type, String url, final String label, final ServiceCaller caller, final JSONObject obj) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException | JsonSyntaxException e1) {
                        // e1.printStackTrace();
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;
    }

    public static JsonObjectRequest getRequestRegister(Context context, int request_type, String url, final String label, final ServiceCaller caller, final JSONObject obj) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException | JsonSyntaxException e1) {
                        // e1.printStackTrace();
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;
    }

    public static JsonObjectRequest getRequestOtp(Context context, int request_type, String url, final String label, final ServiceCaller caller, final JSONObject obj) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException | JsonSyntaxException e1) {
                        // e1.printStackTrace();
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;
    }

    public static JsonObjectRequest getRequestUser(Context context, int request_type, String url, final String label, final ServiceCaller caller, final JSONObject obj) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException | JsonSyntaxException e1) {
                        // e1.printStackTrace();
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;
    }

    public static JsonObjectRequest getConnectionType(Context context, int request_type, String url, final String label, final ServiceCaller caller) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JsonSyntaxException je) {
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json; charset=utf-8");
//                params.put("Accept", "application/json");
//                params.put("Authorization", token);
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;

    }

    public static JsonObjectRequest getFaq(Context context, int request_type, String url, final String label, final ServiceCaller caller, final String token) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JsonSyntaxException je) {
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json; charset=utf-8");
//                params.put("Accept", "application/json");
                params.put("Authorization", token);
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;

    }

    public static JsonObjectRequest getServiceType(Context context, int request_type, String url, final String label, final ServiceCaller caller, final String token) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JsonSyntaxException je) {
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json; charset=utf-8");
//                params.put("Accept", "application/json");
                params.put("Authorization", token);
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;

    }


    public static JsonObjectRequest getComplaintType(Context context, int request_type, String url, final String label, final ServiceCaller caller, final String token) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JsonSyntaxException je) {
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                // params.put("Content-Type", "application/json; charset=utf-8");
                // params.put("Accept", "application/json");
                params.put("Authorization", token);
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;

    }


    public static JsonObjectRequest getContactDetails(Context context, int request_type, String url, final String label, final ServiceCaller caller, final String token) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JsonSyntaxException je) {
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json; charset=utf-8");
//                params.put("Accept", "application/json");
                params.put("Authorization", token);
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;
    }


    public static JsonObjectRequest getAboutUs(Context context, int request_type, String url, final String label, final ServiceCaller caller, final String token) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JsonSyntaxException je) {
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json; charset=utf-8");
//                params.put("Accept", "application/json");
                params.put("Authorization", token);
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;

    }

    public static JsonObjectRequest getTips(Context context, int request_type, String url, final String label, final ServiceCaller caller, final String token) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JsonSyntaxException je) {
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json; charset=utf-8");
//                params.put("Accept", "application/json");
                params.put("Authorization", token);
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;

    }


    public static JsonObjectRequest getTariff(Context context, int request_type, String url, final String label, final ServiceCaller caller, final String token) {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                caller.onAsyncSuccess(jsonResponse, label);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        VolleyLog.d(TAG, "Error: " + res);
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    } catch (JsonSyntaxException je) {
                        caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                } else
                    caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json; charset=utf-8");
//                params.put("Accept", "application/json");
                params.put("Authorization", token);
                return params;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        return jsonObjReq;

    }



            public static JsonObjectRequest feedbackrequest(Context context, int request_type, String url, final String label, final ServiceCaller caller, final String starcount, final String remark, final String token) {
                final Map<String, String> postParam = new HashMap<String, String>();
                postParam.put("stars", starcount);
                postParam.put("feedback", remark);
                final JSONObject jsonObject = new JSONObject(postParam);
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                VolleyLog.d(TAG, "Error: " + res);
                                Gson gson = new Gson();
                                JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                                caller.onAsyncSuccess(jsonResponse, label);
                            } catch (UnsupportedEncodingException e1) {
                                e1.printStackTrace();
                            } catch (JsonSyntaxException je) {
                                caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : " ", label, response);
                            }
                        } else
                            caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : " ", label, response);
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        // params.put("Content-Type", "application/json; charset=utf-8");
                        // params.put("Accept", "application/json");
                        params.put("Authorization", token);
                        return params;
                    }
                };

                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                return jsonObjReq;
            }


            public static JsonObjectRequest serviceRequest(Context context, int request_type, String url, final String label, final ServiceCaller caller, final JSONObject obj, final String consumer_remark, final String token) {
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(request_type, url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                        caller.onAsyncSuccess(jsonResponse, label);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                VolleyLog.d(TAG, "Error: " + res);
                                Gson gson = new Gson();
                                JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                                caller.onAsyncSuccess(jsonResponse, label);
                            } catch (UnsupportedEncodingException | JsonSyntaxException e1) {
                                // e1.printStackTrace();
                                caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                            }
                        } else
                            caller.onAsyncFail(error.getMessage() != null && !error.getMessage().equals("") ? error.getMessage() : "Please Contact Server Admin", label, response);
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("Content-Type", "application/json");
                        params.put("Accept", "application/json");
                        params.put("Authorization", token);
                        return params;
                    }
                };

                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                return jsonObjReq;
            }


        }
