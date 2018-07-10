package com.imooc.service;

import com.imooc.dataobject.ProductInfo;
import com.imooc.product.DecreaseStockInput;

import java.util.List;

/**
 * Created by XuQin on 2018/7/3.
 */
public interface ProductService {

    List<ProductInfo> findUpAll();

    List<ProductInfo> findByProductIdIn(List<String> productIdList);

    void decreaseStock(List<DecreaseStockInput> cartDTOList);
}
