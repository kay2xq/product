package com.imooc.controller;

import com.imooc.dataobject.ProductInfo;
import com.imooc.product.DecreaseStockInput;
import com.imooc.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by XuQin on 2018/7/6.
 */
@RestController
@Slf4j
@RequestMapping("/service/product")
public class ServiceController {
    @Autowired
    private ProductService productService;

    @GetMapping("/msg")
    public String msg(){
        return "this is product msg 9080 ";
    }


    @PostMapping("/findByProductIdIn")
    public List<ProductInfo> findByProductIdIn(@RequestBody List<String> productIds){
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        List<ProductInfo> products = productService.findByProductIdIn(productIds);
        return products;
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> cartDTOs){
        productService.decreaseStock(cartDTOs);
        log.info("~~~~~~~~~~~~");
        return;
    }
}
