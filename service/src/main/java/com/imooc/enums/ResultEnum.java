package com.imooc.enums;

import lombok.Getter;

/**
 * Created by XuQin on 2018/7/6.
 */
@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIT(1, "商品不存在"),
    STOCK_NOT_ENOUGH(2, "商品库存不足"),
    PRODUCT_IS_DOWN(3, "商品已下架");

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
