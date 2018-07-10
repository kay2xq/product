package com.imooc.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created by XuQin on 2018/7/6.
 */
@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/msg")
    String getProductMsg();

    @PostMapping("/service/product/findByProductIdIn")
    List<ProductInfoOutput> findByProductIdIn(@RequestBody List<String> productIds);

    @PostMapping("/service/product/decreaseStock")
    void decreaseStock(@RequestBody List<DecreaseStockInput> cartDTOs);
}
