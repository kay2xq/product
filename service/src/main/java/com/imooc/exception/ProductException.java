package com.imooc.exception;

import com.imooc.enums.ResultEnum;

/**
 * Created by XuQin on 2018/7/6.
 */
public class ProductException extends RuntimeException {
    private Integer code;

    public ProductException(String message, Integer code){
        super(message);
        this.code = code;
    }

    public ProductException(ResultEnum resultEnum){
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
