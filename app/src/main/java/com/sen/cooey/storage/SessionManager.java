package com.sen.cooey.storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    /**
     * Shared Preference Singlton class
     */

    private static SessionManager sessionMgrObj = null;
    private SharedPreferences sharedPreferenceObj;
    private SharedPreferences.Editor spEditorObj;

    //SP
    private final String KEY_SP_MASTER_PREFERENCE = "master_key_sp";


    //KEYS
    private final String NUM_OF_UNDO = "num_of_undo";
    private final String LOGGED_IN = "logged_in";
    private final String LAST_DATA = "last_data";


    private SessionManager(Context context) throws NullPointerException {
        sharedPreferenceObj = context.getSharedPreferences(
                KEY_SP_MASTER_PREFERENCE, Context.MODE_PRIVATE);
//        sharedPreferenceObj = PreferenceManager
//                .getDefaultSharedPreferences(context);
        spEditorObj = sharedPreferenceObj.edit();
    }

    public static SessionManager getInstance(Context context) throws NullPointerException {
        if (sessionMgrObj != null)
            return sessionMgrObj;
        else
            sessionMgrObj = new SessionManager(context);
        return sessionMgrObj;
    }


    /* NUM_OF_UNDO */
    public void putNUM_OF_UNDO(int num) {
        spEditorObj.putInt(NUM_OF_UNDO, num);
        spEditorObj.apply();
        spEditorObj.commit();
    }

    public int getNUM_OF_UNDO() {
        return sharedPreferenceObj.getInt(NUM_OF_UNDO, 0);
    }

    /* LOGGED_IN */
    public void putLOGGED_IN(int logedIn) {
        spEditorObj.putInt(LOGGED_IN, logedIn);
        spEditorObj.apply();
        spEditorObj.commit();
    }

    //zero means MALE, one means FEMALE, minus-one means loggedout
    public int getLOGGED_IN() {
        return sharedPreferenceObj.getInt(LOGGED_IN, -1);
    }


    /* LAST_DATA */
    public void putLAST_DATA(String data) {
        spEditorObj.putString(LAST_DATA, data);
        spEditorObj.apply();
        spEditorObj.commit();
    }

    public String getLAST_DATA() {
        return sharedPreferenceObj.getString(LAST_DATA, "");
    }


    public void clearAll() {
        spEditorObj.clear();
        spEditorObj.apply();
        spEditorObj.commit();
    }

}
