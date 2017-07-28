package com.jean.mybatis.generator.utils;

public class StringUtil {

    public static String toCamelCase(String str, boolean toCamelCase) {
        if (str) {
            if (toCamelCase) {
               return str.split("_").collect({
                    uppercaseFirst(it, true)
                }).join("")
            } else {
                return str.collect {
                    it == it.toUpperCase() ? "_" + it.toLowerCase() : it
                }.join("").substring(1)
            }
        }
        return str
    }

    public static String uppercaseFirst(String str, boolean uppercase) {
        if (str) {
            def s = str.substring(1)
            if (uppercase) {
                return str.charAt(0).toUpperCase().toString() + s
            } else {
                str.charAt(0).toLowerCase().toString() + s
            }
        }
        return str
    }
}
