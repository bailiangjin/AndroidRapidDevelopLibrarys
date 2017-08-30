package com.bailiangjin.utilslibrary.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bailiangjin.utilslibrary.api.UtilsLibrary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class MySP {
    /**
     * 保存在手机里面的文件名
     */
    public String mSpFileName = "";

    public MySP(String spFileName) {
        this.mSpFileName = spFileName;
    }

    public void putInt(String key, int value) {
        put(key, value);
    }


    public int getInt(String key) {
        return getInt(key, -1);
    }


    public int getInt(String key, int defaultValue) {
        return (int) get(key, -1);
    }

    /**
     * 保存boolean数据的方法
     *
     * @param key
     */
    public void putBoolean(String key, boolean value) {
        put(key, value);
    }

    /**
     * 获取boolean 方法
     *
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        return getBoolean(key,false);
    }


    public boolean getBoolean(String key,boolean defValue) {
        SharedPreferences sp = getSp();
        return sp.getBoolean(key, defValue);
    }


    /**
     * 保存String的方法
     *
     * @param key
     * @param str
     */
    public void putString(String key, String str) {
        if (null == str) {
            return;
        }
        SharedPreferences sp = getSp();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, str);
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 获取String 方法
     *
     * @param key
     * @param defaultStr
     * @return
     */
    public String getStringDef(String key, String defaultStr) {
        SharedPreferences sp = getSp();

        return sp.getString(key, defaultStr);
    }

    /**
     * 获取String 方法
     *
     * @param key
     * @return
     */
    public String getString(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        SharedPreferences sp = getSp();
        return sp.getString(key, null);
    }

    public long getLong(String key) {

        return getLong(key,-1L);
    }
    public long getLong(String key,long defValue) {
        if (TextUtils.isEmpty(key)) {
            return defValue;
        }
        SharedPreferences sp = getSp();
        return sp.getLong(key, defValue);
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        SharedPreferences sp = getSp();
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        SharedPreferences sp = getSp();
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        SharedPreferences sp = getSp();
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public Map<String, ?> getAll() {
        SharedPreferences sp = getSp();
        return sp.getAll();
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param key
     * @param obj
     */
    public void put(String key, Object obj) {

        SharedPreferences sp = getSp();
        SharedPreferences.Editor editor = sp.edit();

        if (obj instanceof String) {
            editor.putString(key, (String) obj);
        } else if (obj instanceof Integer) {
            editor.putInt(key, (Integer) obj);
        } else if (obj instanceof Boolean) {
            editor.putBoolean(key, (Boolean) obj);
        } else if (obj instanceof Float) {
            editor.putFloat(key, (Float) obj);
        } else if (obj instanceof Long) {
            editor.putLong(key, (Long) obj);
        } else {
            editor.putString(key, obj.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @param defaultObj
     * @return
     */
    public Object get(String key, Object defaultObj) {
        SharedPreferences sp = getSp();

        if (defaultObj instanceof String) {
            return sp.getString(key, (String) defaultObj);
        } else if (defaultObj instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObj);
        } else if (defaultObj instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObj);
        } else if (defaultObj instanceof Float) {
            return sp.getFloat(key, (Float) defaultObj);
        } else if (defaultObj instanceof Long) {
            return sp.getLong(key, (Long) defaultObj);
        }

        return null;
    }


    private SharedPreferences getSp() {
        return getSp(this.mSpFileName);
    }

    private static SharedPreferences getSp(String spFileName) {
        return UtilsLibrary.getAppContext().getSharedPreferences(spFileName, Context.MODE_PRIVATE);
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
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
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
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