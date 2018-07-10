package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.ProductException;
import com.imooc.product.DecreaseStockInput;
import com.imooc.product.ProductInfoOutput;
import com.imooc.repository.ProductInfoRepository;
import com.imooc.service.ProductService;
import com.imooc.utils.JsonUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by XuQin on 2018/7/3.
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findByProductIdIn(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    public void decreaseStock(List<DecreaseStockInput> cartDTOList) {
        List<ProductInfo> decreaseStockInputList = decreaseStockProcess(cartDTOList);
        List<ProductInfoOutput> collect = decreaseStockInputList.stream().map(e -> {
            ProductInfoOutput productInfoOutput = new ProductInfoOutput();
            BeanUtils.copyProperties(e, productInfoOutput);
            return productInfoOutput;
        }).collect(Collectors.toList());
        //发送mq消息
        amqpTemplate.convertAndSend("product_stock", JsonUtils.toJson(collect));
    }

    @Transactional
    public List<ProductInfo> decreaseStockProcess(List<DecreaseStockInput> cartDTOList) {
        List<ProductInfo> decreaseStockInputList = new ArrayList<>();

        for (DecreaseStockInput cartDTO : cartDTOList){
            //查询商品是否存在或下架
            Optional<ProductInfo> optional = productInfoRepository.findById(cartDTO.getProductId());
            if (!optional.isPresent()){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIT);
            }

            ProductInfo product = optional.get();
            if (product.getProductStatus().equals(ProductStatusEnum.DOWN.getCode())){
                throw new ProductException(ResultEnum.PRODUCT_IS_DOWN);
            }

            int result = product.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0 ){
                throw new ProductException(ResultEnum.STOCK_NOT_ENOUGH);
            }

            product.setProductStock(result);
            productInfoRepository.save(product);
            decreaseStockInputList.add(product);
        }

        return decreaseStockInputList;
    }
}
