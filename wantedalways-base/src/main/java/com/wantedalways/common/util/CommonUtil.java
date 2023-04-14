package com.wantedalways.common.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Wantedalways
 */
@Component
public class CommonUtil {

    /**
     * 获取类的所有属性，包括父类
     */
    public static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 获取两集合的差异，获取主要集合中存在，次要集合中不存在的元素
     */
    public static List<String> getDifference(List<String> mainList, List<String> minorList) {
        Map<String, String> tempMap = minorList.parallelStream().collect(Collectors.toMap(Function.identity(), Function.identity(), (oldData, newData) -> newData));
        return mainList.parallelStream().filter(str -> !tempMap.containsKey(str)).collect(Collectors.toList());
    }

}
