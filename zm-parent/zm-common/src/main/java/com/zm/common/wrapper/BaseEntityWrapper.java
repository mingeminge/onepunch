package com.zm.common.wrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ==========================
 *
 * @author yizuomin
 * @date 2020/5/22 14:10
 * ==========================
 **/
public abstract class BaseEntityWrapper<E, V> {

    /**
     * 转化vo对象
     *
     * @param e
     * @return
     */
    public abstract V entityVO(E e);

    /**
     * 转化集合
     *
     * @param list 集合
     * @return
     */
    public List<V> listVO(List<E> list) {
        return list.stream().map(this::entityVO).collect(Collectors.toList());
    }

    /**
     * 转换分页
     *
     * @param pages 分页
     * @return
     */
    public IPage<V> pageVO(IPage<E> pages) {
        List<V> records = listVO(pages.getRecords());
        IPage<V> pageVo = new Page<>(pages.getCurrent(), pages.getSize(), pages.getTotal());
        pageVo.setRecords(records);
        return pageVo;
    }
}
