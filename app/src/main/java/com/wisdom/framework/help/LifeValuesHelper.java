package com.wisdom.framework.help;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chejiangwei on 2017/11/28.
 * Describe:有生命的变量持有体
 */

public class LifeValuesHelper {
    private static Map<String, LifeValuesHelper> lifeValuesMap;


    private Map<String, Object> valuesMap;

    private LifeValuesHelper() {
        valuesMap = new HashMap<>();
    }


    public void put(String key, Object value) {
        valuesMap.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) valuesMap.get(key);
    }


    private static final String OBJ = "obj";

    /**
     * 大部分实际情况，只需要临时存储一个变量，因此为了方便使用，扩展了一下
     *
     * @param obj
     */
    public void putObj(Object obj) {
        put(OBJ, obj);
    }

    public <T> T getObj() {
        return get(OBJ);
    }

    public synchronized static LifeValuesHelper getIt(String key) {
        if (lifeValuesMap == null) lifeValuesMap = new HashMap<>();

        LifeValuesHelper lifeValuesHelper = lifeValuesMap.get(key);
        if (lifeValuesHelper == null)
            lifeValuesMap.put(key, lifeValuesHelper = new LifeValuesHelper());
        return lifeValuesHelper;
    }

    /**
     * 请在使用结束后释放废弃的静态资源
     *
     * @param key
     */
    public synchronized static void release(String key) {
        if (lifeValuesMap == null) return;
        LifeValuesHelper remove = lifeValuesMap.remove(key);
        if (remove == null) return;
        remove.valuesMap.clear();
        remove.valuesMap = null;
    }

}
