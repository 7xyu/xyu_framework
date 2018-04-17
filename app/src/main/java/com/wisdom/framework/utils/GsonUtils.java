package com.wisdom.framework.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.SoftReference;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;


/**
 * Created by xyu on 2017/3/15.
 * Description:
 */


public class GsonUtils {
    private GsonUtils() {
    }

    /**
     * 空的 {@code JSON} 数据 - <code>"{}"</code>。
     */
    private static final String EMPTY_JSON = "{}";

    /**
     * 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。
     */
    public static final String EMPTY_JSON_ARRAY = "[]";
    /**
     * 默认的 {@code JSON} 日期/时间字段的格式化模式。
     */
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static SoftReference<Gson> gsonSoftReference = new SoftReference<>(null);

//    private static EnumSerializerProvider enumSerializerProvider = null;//应用所需枚举序列的提供者
//
//    public static void setEnumSerializerProvider(EnumSerializerProvider serializerProvider) {
//        enumSerializerProvider = serializerProvider;
//    }


    /**
     * 将给定的目标对象根据{@code GsonBuilder} 所指定的条件参数转换成 {@code JSON} 格式的字符串。
     * <p/>
     * 该方法转换发生错误时，不会抛出任何异常。若发生错误时，{@code JavaBean} 对象返回 <code>"{}"</code>；
     * 集合或数组对象返回 <code>"[]"</code>。 其本基本类型，返回相应的基本值。
     *
     * @param target     目标对象。
     * @param targetType 目标对象的类型。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     * @since 1.1
     */
    public static String toJson(Object target, Type targetType,
                                Gson gson) {
        if (target == null)
            return EMPTY_JSON;
        if (gson == null) gson = getGson();
        String result = EMPTY_JSON;
        try {
            if (targetType == null) {
                result = gson.toJson(target);
            } else {
                result = gson.toJson(target, targetType);
            }
        } catch (Exception ex) {
            Log.w(GsonUtils.class.getSimpleName(), "目标对象 " + target.getClass().getName()
                    + " 转换 JSON 字符串时，发生异常！", ex);
            if (target instanceof Collection<?>
                    || target instanceof Iterator<?>
                    || target instanceof Enumeration<?>
                    || target.getClass().isArray()) {
                result = EMPTY_JSON_ARRAY;
            }
        }
        return result;
    }

    public static String toJson(Object obj) {
        return toJson(obj, null, null);
    }

    /**
     * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
     * <ul>
     * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
     * <li>该方法不会转换 {@code null} 值字段；</li>
     * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
     * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
     * </ul>
     *
     * @param targetType 目标对象的类型。
     * @return 目标对象的 {@code JSON} 格式的字符串。
     * @since 1.0
     */
    public static String toJson(Object obj, Type targetType) {
        return toJson(obj, targetType, null);
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
     *
     * @param <T>   要转换的目标类型。
     * @param json  给定的 {@code JSON} 字符串。
     * @param token {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     * @since 1.0
     */
    public static <T> T fromJson(String json, TypeToken<T> token) {
        return fromJson(json, token.getType());
    }

    /**
     * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
     * 对象。</strong>
     *
     * @param <T>  要转换的目标类型。
     * @param json 给定的 {@code JSON} 字符串。
     * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
     * @since 1.0
     */
    public static <T> T fromJson(String json, Type type) {
        if (TextUtils.isEmpty(json)) return null;
        Gson gson = getGson();
        try {
            return gson.fromJson(json, type);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e(GsonUtils.class.getSimpleName(), ex.getMessage(), ex);
            return null;
        }
    }


    public static Gson getGson() {
        Gson gson = gsonSoftReference.get();
        if (gson == null) {
            String datePattern = DEFAULT_DATE_PATTERN;
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat(datePattern);
//            gsonBuilder.registerTypeAdapter(Date.class, new DateSerializer());
            /* if (enumSerializerProvider != null) {
               EnumSerializer[] provide = enumSerializerProvider.provide();
                if (provide != null)
                    for (EnumSerializer item : provide) {
                        gsonBuilder.registerTypeAdapter(item.getEnumClass(), item);
                    }
            }*/
            gson = gsonBuilder.create();
            gsonSoftReference = new SoftReference<>(gson);
        }
        return gson;
    }

 /*   public static List parserListTFromJson(final String pJsonData, final Class pClass) {
        List _TList;
//        try {
        if (!TextUtils.isEmpty(pJsonData)) {
            Gson _Gson = getGson();
            _TList = _Gson.fromJson(pJsonData, new ListOfJson(pClass));
        } else {
            _TList = null;
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//            _TList = null;
//            throw new RuntimeException("json转化bean失败");
//        }
        return _TList;
    }*/

    public static String parserListTToJson(List pListT) {
        String _JosnStr;
//        try {
        if (pListT != null) {
            Gson _Gson = getGson();
            _JosnStr = _Gson.toJson(pListT);
        } else {
            _JosnStr = "";
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//            _JosnStr = "";
//            throw new RuntimeException("bean转化json失败");
//        }
        return _JosnStr;
    }

    /*public interface EnumSerializerProvider {
        EnumSerializer[] provide();
    }*/

}
