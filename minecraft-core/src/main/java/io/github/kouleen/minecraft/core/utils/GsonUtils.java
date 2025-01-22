package io.github.kouleen.minecraft.core.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * @author zhangqing
 * @since 2025/1/22 16:03
 */
public final class GsonUtils {

    /**
     * 全局单例 google json 对象
     */
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    public static String toJson(Object src) {
        if (null == src) {
            return null;
        }
        return gson.toJson(src);
    }

    public static String toJson(Object src, Type typeOfSrc) {
        return gson.toJson(src, typeOfSrc);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        if (null == json) {
            return null;
        }
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public static <T> T fromJson(Reader json, Class<T> classOfT) throws JsonSyntaxException, JsonIOException {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(Reader json, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    public static <T> T fromJson(JsonReader reader, Type typeOfT) throws JsonIOException, JsonSyntaxException {
        return gson.fromJson(reader, typeOfT);
    }

    public static <T> T fromJson(JsonElement json, Class<T> classOfT) throws JsonSyntaxException {
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(JsonElement json, Type typeOfT) throws JsonSyntaxException {
        return gson.fromJson(json, typeOfT);
    }

    /**
     * 全局 google json format 对象
     */
    private static Gson format = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Object convert Format String
     *
     * @param src Object
     * @return json String
     */
    public static String toFormat(Object src) {
        return format.toJson(src);
    }
}
