package com.zhou.demo.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ZhouMeng
 * @version 1.0.0
 * @date 2023/8/4 下午6:41
 */
public class ObjectUtils {
    public static <T> String sortByDictOrderAndConcat(T obj, String delimiter, String... excludes) {
        Class<?> aClass = obj.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();

        List<String> resultList = new ArrayList<>(declaredFields.length);
        List<String> excludeList = Arrays.asList(excludes);

        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                if (!excludeList.contains(field.getName())) {
                    resultList.add(field.getName().concat("=").concat(field.get(obj) == null ? "" : (String) field.get(obj)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(resultList);
        return String.join(delimiter, resultList);
    }
}
