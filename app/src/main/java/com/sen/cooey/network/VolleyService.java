package com.sen.cooey.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.sen.cooey.MainApplication;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class VolleyService {
    private static final String TAG = "VolleyService";
    private static final int TIME_OUT = 10000;

    IResult mResultCallback = null;
    Context mContext;

    public VolleyService(IResult resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
    }

    public synchronized void postReqStringRes(final String reqType, int method, String url,
                                              final Map<String, String> paramsData, boolean isShowProgress) {


        try {
            StringRequest sr = new StringRequest(method, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i(TAG, "responseString " + response);

                            if (mResultCallback != null)
                                mResultCallback
                                        .notifySuccess(reqType, response);
                        }

                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());

                    if (mResultCallback != null)
                        mResultCallback.notifyError(reqType, error);
                }
            }) {

                @Override
                protected Map<String, String> getParams()
                        throws AuthFailureError {
                    return paramsData;
                }//eof getParams()


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    return super.parseNetworkResponse(response);
                }//eof parseNetworkResponse()

                /* (non-Javadoc)
                 * @see com.android.volley.Request#getHeaders()
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> mapHeadersParams = super.getHeaders();

                    if (mapHeadersParams == null
                            || mapHeadersParams.equals(Collections.emptyMap())) {
                        mapHeadersParams = new HashMap<String, String>();
                        //unComment if required
                        //mapHeadersParams.put("Content-Type", "application/json");
                    }

                    return mapHeadersParams;
                }//eof getHeaders()

            };

            //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions.
            //Volley does retry for a specified the policy.
            sr.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            // Adding request to request queue
            MainApplication.getInstance().addToRequestQueue(sr, reqType);

        } catch (Exception e) {
            Log.e(TAG, "Exception " + e.getMessage());
        }//eof try...catch
    }

}// eof class VolleyService