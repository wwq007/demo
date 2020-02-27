package com.example.spring.demo.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Author: wwq
 * @Date: 2020/2/26 14:19
 * @Description: xxxxx
 */
public class JsonUtil {

    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();

        return gson.toJson(object);
    }
}
