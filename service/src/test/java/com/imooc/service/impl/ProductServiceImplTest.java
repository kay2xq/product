package com.imooc.service.impl;

import com.imooc.ProductApplicationTests;
import com.imooc.product.DecreaseStockInput;
import com.imooc.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by XuQin on 2018/7/6.
 */
@Component
public class ProductServiceImplTest extends ProductApplicationTests {

    @Autowired
    private ProductService productService;

    @Test
    public void decreaseStock(){
        productService.decreaseStock(Arrays.asList(new DecreaseStockInput("1", 2)));
    }

}