package com.guochuang.mimedia.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 本地Preference的存取工具类
 *
 * @author huang jiaqiang
 */
public class PrefUtil {
    public static final String USER_ROLE = "user_role";
    public static final String MOBILE = "mobile";

    private SharedPreferences sharedPreference;
    private static PrefUtil preference = null;
    public static final String LATITUDE="latitude";
    public static final String LONGITUDE="longitude";
    public static final String USER_TOKEN = "userToken";
    public static final String COIN = "coin";
    public static final String MONEY = "money";
    // 未认证：0 已认证：1 审核中：2
    public static final String IDENTITY = "identify";
    // 未设置：0 已设置：1
    public static final String SAFECODE = "safeCode";
    public static final String FIRSTOPEN = "firstOpen";
    public static final String MSGISNEW = "msgIsNew";
    public static final String KILOMETRE = "kilometre";
    public static final String DEBUGHOST = "debugHost";
    public static final String SOUNDSWITCH = "sound_switch";
    // 广告相关信息存储
    public static final String ADVER_MESSAGE = "adverMessage";
    public static final String UPGRADE_NOTICE = "upgrade_notice";
    public static final String LAST_REMIND_TIME = "last_remind_time";
    public static final String MARKET_SWITCH = "market_switch";

    public static final String ISDRAG = "isdrag";
    public static final String EDIT_REDBAG_TYPE = "edit_redbag_type";
    public static final String VIDEO_FOLDER= "video_folder";
    public static final String IMAGE_FOLDER= "image_folder";

    public static void init(Context context) {
        preference = new PrefUtil(context);
    }

    public static synchronized PrefUtil getInstance() {
        return preference;
    }

    private PrefUtil(Context context) {
        String packageName = context.getPackageName() + "_preferences";
        sharedPreference = context.getSharedPreferences(packageName, Context.MODE_PRIVATE);
    }
    public String getLatitude() {
        return sharedPreference.getString(LATITUDE, "");
    }
    public String getLongitude() {
        return sharedPreference.getString(LONGITUDE, "");
    }

    public void clean() {
        Editor editor = sharedPreference.edit();
        editor.clear();
        editor.commit();
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        boolean tid = sharedPreference.getBoolean(name, defaultValue);
        return tid;
    }

    public int getInt(String name, int defaultValue) {
        int tid = sharedPreference.getInt(name, defaultValue);
        return tid;
    }

    public long getLong(String name, long defaultValue) {
        long tid = sharedPreference.getLong(name, defaultValue);
        return tid;
    }

    public String getString(String name, String defaultValue) {
        String tid = sharedPreference.getString(name, defaultValue);
        return tid;
    }

    public float getFloat(String name, float defaultValue) {
        float tid = sharedPreference.getFloat(name, defaultValue);
        return tid;
    }

    public void setInt(String name, int value) {
        Editor edit = sharedPreference.edit();
        edit.putInt(name, value);
        edit.commit();
    }

    public void setLong(String name, long value) {
        Editor edit = sharedPreference.edit();
        edit.putLong(name, value);
        edit.commit();
    }

    public void setBoolean(String name, boolean value) {
        Editor edit = sharedPreference.edit();
        edit.putBoolean(name, value);
        edit.commit();
    }

    public void setString(String name, String value) {
        Editor edit = sharedPreference.edit();
        edit.putString(name, value);
        edit.commit();
    }

    public void setFloat(String name, float value) {
        Editor edit = sharedPreference.edit();
        edit.putFloat(name, value);
        edit.commit();
    }

    public String getUserToken(){
        return getString(USER_TOKEN, "");
    }


}
