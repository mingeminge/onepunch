package com.yzm.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ==========================
 *
 * @author : yizuomin
 * @date : Created in 11:32 2019/12/14
 * ===========================
 */
@Data
@SuppressWarnings("unchecked")
public class PageVO<T> implements Serializable {

    private static final long serialVersionUID = -6688179934539656285L;

    private Integer code;

    private Long count;

    private T data;

    public static <T>PageVO build(Long count, T data) {
        PageVO pageVO = new PageVO();
        pageVO.setData(data);
        pageVO.setCount(count);
        pageVO.setCode(200);
        return pageVO;
    }

}
