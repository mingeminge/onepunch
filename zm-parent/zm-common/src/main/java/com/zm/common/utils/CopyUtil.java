package com.zm.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 10:53 2019/12/14
 * ===========================
 */
public class CopyUtil<T, E> {

    public static <E> E copyProperties(Object o, Class<E> clazz) {
        //o->clazz
        if (o == null) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        try {
            E newInstance = clazz.newInstance();
            BeanUtils.copyProperties(o, newInstance);
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }


    public static <T,E> List<E> copyList(List<T> objectList, Class<E> clazz) {
        //list->list<clazz>
        if (CollectionUtils.isEmpty(objectList)) {
            return null;
        }
        if (clazz == null) {
            return null;
        }
        try {
            List<E> dtoList = new ArrayList<>();
            for (Object o : objectList) {
                E e = clazz.newInstance();
                BeanUtils.copyProperties(o, e);
                dtoList.add(e);
            }
            return dtoList;
        } catch (Exception e) {
            return null;
        }
    }
}
