package com.imooc.enums;

import lombok.Getter;

/**
 * Created by XuQin on 2018/7/3.
 */
@Getter
public enum ProductStatusEnum {
    UP(0, "在架"),
    DOWN(1, "下架"),
    ;

    private Integer code;

    private String desc;

    ProductStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
