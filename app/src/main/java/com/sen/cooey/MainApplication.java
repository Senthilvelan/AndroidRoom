package com.sen.cooey;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.sen.cooey.network.LruBitmapCache;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;


@ReportsCrashes(formKey = "Hi",
        mailTo = "velansms@gmail.com", mode = ReportingInteractionMode.SILENT, resToastText = R.string.app_name)

public class MainApplication extends Application {

    public static final String TAG = MainApplication.class.getSimpleName();

    private static MainApplication mInstance;

    //For Volley
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ACRA.init(this);
        registerActivityLifecycleCallbacks(new MyLifeCycleHandler());
    }

    public static synchronized MainApplication getInstance() {
        return mInstance;
    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }


}
