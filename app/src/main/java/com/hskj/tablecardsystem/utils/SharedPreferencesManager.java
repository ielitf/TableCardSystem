package com.hskj.tablecardsystem.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    static SharedPreferences sp;

    public static void init(Context context, String name) {
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
    /**
     * 登录账号
     */
    private static final String ACCOUNT = "account";
    public static void setAccount(String num) {
        if (null != sp) {
            sp.edit().putString(ACCOUNT, num).commit();
        }
    }

    public static String getAccount() {
        if (null != sp) {
            return sp.getString(ACCOUNT, "");
        }
        return "";
    }
    /**
     * 登录密码
     */
    private static final String PASSWORD = "password";
    public static void setPassword(String num) {
        if (null != sp) {
            sp.edit().putString(PASSWORD, num).commit();
        }
    }

    public static String getPassword() {
        if (null != sp) {
            return sp.getString(PASSWORD, "");
        }
        return "";
    }
    /**
     * 服务器IP
     */
    private static final String SERVICE_IP = "service_ip";
    public static void setServiceIp(String num) {
        if (null != sp) {
            sp.edit().putString(SERVICE_IP, num).commit();
        }
    }

    public static String getServiceIp() {
        if (null != sp) {
            return sp.getString(SERVICE_IP, "");
        }
        return "";
    }
    /**
     * 会议室编号
     */
    private static final String ROOM_NUM = "room_num";
    public static void setRoomNum(String num) {
        if (null != sp) {
            sp.edit().putString(ROOM_NUM, num).commit();
        }
    }

    public static String getRoomNum() {
        if (null != sp) {
            return sp.getString(ROOM_NUM, "");
        }
        return "";
    }
    /**
     * 桌牌编号
     */
    private static final String TABLE_NUM = "table_num";
    public static void setTableNum(String num) {
        if (null != sp) {
            sp.edit().putString(TABLE_NUM, num).commit();
        }
    }

    public static String getTableNum() {
        if (null != sp) {
            return sp.getString(TABLE_NUM, "");
        }
        return "";
    }
    /**
     * 姓名
     */
    private static final String PERSON_NAME = "person_name";
    public static void setPersonName(String jsonStr) {
        if (null != sp) {
            sp.edit().putString(PERSON_NAME, jsonStr).commit();
        }
    }

    public static String getPersonName() {
        if (null != sp) {
            return sp.getString(PERSON_NAME, "待定");
        }
        return "";
    }
    /**
     * 字体大小
     */
    private static final String TEXT_SIZE = "text_size";
    public static void setTextSize(String jsonStr) {
        if (null != sp) {
            sp.edit().putString(TEXT_SIZE, jsonStr).commit();
        }
    }

    public static String getTextSize() {
        if (null != sp) {
            return sp.getString(TEXT_SIZE, "200");
        }
        return "";
    }
    /**
     * 缓存主页数据
     */
    private static final String CACHED_HOMEPAGE_DATA = "cached_homepage_data";
    public static void setCachedHomepageData(String jsonStr) {
        if (null != sp) {
            sp.edit().putString(CACHED_HOMEPAGE_DATA, jsonStr).commit();
        }
    }

    public static String getCachedHomepageData() {
        if (null != sp) {
            return sp.getString(CACHED_HOMEPAGE_DATA, null);
        }
        return null;
    }

    /**
     * 是否是第一次使用
     */
    private static final String IS_FIRST_USE = "Is_app_first_use";
    public static void setIsFirstUse(boolean value) {
        if (null != sp) {
            sp.edit().putBoolean(IS_FIRST_USE, value).commit();
        }
    }

    public static boolean getIsFirstUse() {
        if (null != sp) {
            return sp.getBoolean(IS_FIRST_USE, true);
        }
        return true;
    }
}
