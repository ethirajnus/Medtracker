package com.ethigeek.medtracker.managers;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Moushumi Seal
 */
public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "activity_executed";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_SHOW_HELP_SCREENS = "IsShowHelpScreens";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setShowHelpScreens(boolean isFirstTime) {
        editor.putBoolean(IS_SHOW_HELP_SCREENS, isFirstTime);
        editor.commit();
    }

    public boolean isShowHelpScreens() {
        return pref.getBoolean(IS_SHOW_HELP_SCREENS,true);
    }

    public void setUserName(String userName){
        editor.putString("userName",userName);
        editor.commit();
    }

    public String getUsername(){
        return pref.getString("userName","");
    }

    public void setUserId(int userId){
        editor.putInt("userId",userId);
        editor.commit();
    }

    public int getUserId(){
        return pref.getInt("userId",0);
    }
}
