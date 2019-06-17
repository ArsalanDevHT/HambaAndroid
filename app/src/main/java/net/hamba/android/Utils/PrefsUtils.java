package net.hamba.android.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefsUtils {

    private static Context mContext;
    private final static String FIELD_PHONE = "phone";
    private final static String FIELD_COUNTRY = "country";
    private final static String FIELD_COMPLETED_REGISTRATION = "registration_completed";
    private final static String FIELD_SMS_CODE = "sms_code";
    private final static String FIELD_VERIFICATION_ID = "verification_id";
    private final static String FIELD_FULL_USER = "fullUser";
    private final static String FIELD_USER_TYPE = "user_type";
    private final static String FIELD_PRESHARED_KEY = "shared_key";
    private final static String FIELD_USER_PERCENT = "user_percent";
    private final static String FIELD_FACEBOOK_REGISTERED = "registerFacebook";

    private final static String FIELD_STARTED_USING_APP = "start_using_app";
    private final static String FIELD_FIRST_USING_APP_TIME  = "first_using_app_time";
    private final static String FIELD_STORE      = "store";

    public static void setContext(Context context) {
        mContext = context;
    }

    public static SharedPreferences getPrefs() {
        if (mContext == null) {
            return null;
        }
        return mContext.getSharedPreferences("mashoor", Context.MODE_PRIVATE);
    }

    public static void savePhoneNumber(String number) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(FIELD_PHONE, number);
        editor.apply();
    }

    public static String getPhoneNumber() {
        return getPrefs().getString(FIELD_PHONE, "");
    }

    public static void saveFullUser(Boolean fullUser) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putBoolean(FIELD_FULL_USER, fullUser);
        editor.apply();
    }

    public static boolean isFullUser() {
        return getPrefs().getBoolean(FIELD_FULL_USER, false);
    }

    public static void saveCountry(String number) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(FIELD_COUNTRY, number);
        editor.apply();
    }

    public static String getCountry() {
        return getPrefs().getString(FIELD_COUNTRY, "");
    }

    /**
     * set the user as a registered one?
     * @param completed
     */
    public static void userCompletedRegistered(Boolean completed) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putBoolean(FIELD_COMPLETED_REGISTRATION, completed);
        editor.apply();
    }

    /**
     * Check if the user is registered completely or not.
     * @return
     */
    public static boolean hasUserCompletedRegistration() {
        boolean result = false;
        if(getPrefs()!=null) {
            result =  getPrefs().getBoolean(FIELD_COMPLETED_REGISTRATION, false);
        }
        return result;
    }

    /**
     * This is for achievement.
     * Set if this app is used at first or not
     */
    public static void setUsedAppAtFirst() {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putBoolean(FIELD_STARTED_USING_APP, Boolean.FALSE);
        editor.apply();

        setStartUsingAppTime();
    }

    /**
     * Get if this app is used at first or not
     * @return
     */
    public static boolean isUsedAppAtFirst() {
        return getPrefs().getBoolean(FIELD_STARTED_USING_APP, Boolean.TRUE);
    }

    /**
     * This is for achievement.
     * Set the time that used this app at first.
     */
    public static void setStartUsingAppTime() {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putLong(FIELD_FIRST_USING_APP_TIME, System.currentTimeMillis());
        editor.apply();
    }

    /**
     * Get the first using time
     * @return
     */
    public static long getStartedUsingAppTime() {
        return getPrefs().getLong(FIELD_FIRST_USING_APP_TIME, System.currentTimeMillis());
    }


    public static void setUserNeedsToReauthorized(){
        userCompletedRegistered(false);
        deleteSMSCode();
        deleteVerificationId();
    }

    public static void deleteSMSCode() {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.remove(FIELD_SMS_CODE);
        editor.apply();
    }

    public static void saveSMSCode(String smsCode){
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(FIELD_SMS_CODE, smsCode);
        editor.apply();
    }

    public static String getSMSCode(){
        return getPrefs().getString(FIELD_SMS_CODE, "");
    }

    public static void deleteVerificationId() {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.remove(FIELD_VERIFICATION_ID);
        editor.apply();
    }

    public static void saveVerificationId(String verificationId){
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(FIELD_VERIFICATION_ID, verificationId);
        editor.apply();
    }

    public static String getVerificationId(){
        return getPrefs().getString(FIELD_VERIFICATION_ID, "");
    }

    public static boolean getFacebookRegistered() {
        return getPrefs().getBoolean(FIELD_FACEBOOK_REGISTERED, false);
    }

    public static void saveFacebookRegistered(boolean isRegistered) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putBoolean(FIELD_FACEBOOK_REGISTERED, isRegistered);
        editor.apply();
    }
    public static void setLoggedinUserType(int type) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putInt(FIELD_USER_TYPE, type);
        editor.apply();
    }
    public static int getLoggedinUserType() {
        return getPrefs().getInt(FIELD_USER_TYPE, -1);
    }

    public static void setLoggedinUserPercent(int pecent){
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putInt(FIELD_USER_PERCENT, pecent);
        editor.apply();
    }
    public static int getLoggedinUserPercent() {
        return getPrefs().getInt(FIELD_USER_TYPE, -1);
    }

    public static void setPreSharedKey(String preSharedKey) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(FIELD_PRESHARED_KEY, preSharedKey);
        editor.apply();
    }
    public static String getPreSharedKey() {
        return getPrefs().getString(FIELD_PRESHARED_KEY, "null");
    }

    public static void SaveStore(String store) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(FIELD_STORE, store);
        editor.apply();
    }
    public static String getStore() {
        return getPrefs().getString(FIELD_STORE, null);
    }



}
