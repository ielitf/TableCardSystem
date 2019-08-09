package com.hskj.tablecardsystem.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * 类的作用：共享参数工具类
 */

public class SharedPreferenceTools {
    private static final String FILE_NAME = "tableCardSystem_sp";
    /**
     * 保存数据到共享参数的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    private static SharedPreferences sp;

    public static void init(Context context) {
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
    public static void putValueIntoSP(String key, Object object) {
        if (object instanceof String) {
            sp.edit().putString(key, (String) object).apply();
        } else if (object instanceof Integer) {
            sp.edit().putInt(key, (Integer) object).apply();
        } else if (object instanceof Boolean) {
            sp.edit().putBoolean(key, (Boolean) object).apply();
        } else if (object instanceof Float) {
            sp.edit().putFloat(key, (Float) object).apply();
        } else if (object instanceof Long) {
            sp.edit().putLong(key, (Long) object).apply();
        } else {
            sp.edit().putString(key, object.toString()).apply();
        }
    }

    /**
     * 从共享参数中取值，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object getValueFromSP( String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的共享参数的值
     */
    public static void removeValueforSP(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }
    /**
     * 清除所有共享参数数据
     */
    public static void clearAllSPvalue() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }
    /**
     * 查询某个key是否已经存在
     */
    public static boolean containsofSP(String key) {
        return sp.contains(key);
    }

    /**
     * 返回所有的共享参数键值对
     */
    public static Map<String, ?> getAllofSP() {
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        private static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
