package com.qihang.erp.api.controller.dou;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

/**
 * author pbd
 *
 * @desc 通用map数据类
 * @date 2018/6/5
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class DataRow extends HashMap implements Serializable {
    private static final long serialVersionUID = 1L;

    public void set(String name, String value) {
        if (name == null || name.equals(""))
            return;

        if (value == null)
            put(name, "");
        else
            put(name, value);
    }

    public void set(String name, int value) {
        put(name, value);
    }

    public void set(String name, boolean value) {
        put(name, value);
    }

    public void set(String name, long value) {
        put(name, value);
    }

    public void set(String name, float value) {
        put(name, value);
    }

    public void set(String name, double value) {
        put(name, value);
    }

    public void set(String name, BigDecimal value) {
        put(name, value);
    }

    public void set(String name, Object value) {
        put(name, value);

    }

    public String getString(String name) {
        if (name == null || name.equals(""))
            return "";

        Object obj = this.get(name);
        return (obj == null) ? "" : obj.toString();
    }

    public int getInt(String name) {
        if (name == null || name.equals(""))
            return 0;

        int value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Integer)) {
            try {
                value = Integer.parseInt(obj.toString());
            } catch (Exception ex) {
                value = 0;
            }
        } else {
            value = ((Integer) obj).intValue();
            obj = null;
        }

        return value;
    }

    public long getLong(String name) {
        if (name == null || name.equals(""))
            return 0;

        long value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Long)) {
            try {
                value = Long.parseLong(obj.toString());
            } catch (Exception ex) {
                value = 0;
            }
        } else {
            value = ((Long) obj).longValue();
            obj = null;
        }

        return value;
    }

    public float getFloat(String name) {

        if (name == null || name.equals(""))
            return 0;

        float value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Float)) {
            try {
                value = Float.parseFloat(obj.toString());
            } catch (Exception ex) {
                value = 0;
            }
        } else {
            value = ((Float) obj).floatValue();
            obj = null;
        }

        return value;
    }

    public double getDouble(String name) {
        if (name == null || name.equals(""))
            return 0;

        double value = 0;
        if (containsKey(name) == false)
            return 0;

        Object obj = this.get(name);
        if (obj == null)
            return 0;

        if (!(obj instanceof Double)) {
            try {
                value = Double.parseDouble(obj.toString());
            } catch (Exception ex) {
                value = 0;
            }
        } else {
            value = ((Double) obj).doubleValue();
            obj = null;
        }

        return value;
    }

    public boolean getBoolean(String name) {
        if (name == null || name.equals(""))
            return false;

        boolean value = false;
        if (containsKey(name) == false)
            return false;
        Object obj = this.get(name);
        if (obj == null)
            return false;

        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        }

        value = Boolean.valueOf(obj.toString()).booleanValue();
        obj = null;
        return value;
    }

    public BigDecimal getBigDecimal(String name) {
        if (name == null || name.equals(""))
            return new BigDecimal(0);
        BigDecimal value = null;
        if (containsKey(name) == false)
            return new BigDecimal(0);
        Object obj = this.get(name);
        if (obj == null)
            return new BigDecimal(0);
        if (obj instanceof BigDecimal) {
            value = (BigDecimal) obj;
        } else if (obj instanceof String) {
            value = new BigDecimal(obj.toString());
        } else if (obj instanceof BigInteger) {
            value = new BigDecimal((BigInteger) obj);
        } else if (obj instanceof Number) {
            value = new BigDecimal(((Number) obj).doubleValue());
        }
        return value;
    }

    public Object getObject(String name) {
        if (name == null || name.equals(""))
            return null;
        if (containsKey(name) == false)
            return null;
        return this.get(name);
    }
}
