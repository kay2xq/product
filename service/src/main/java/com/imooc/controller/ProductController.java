package com.imooc.controller;

import com.imooc.vo.ProductInfoVO;
import com.imooc.vo.ProductVO;
import com.imooc.vo.ResultVO;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.service.CategoryService;
import com.imooc.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by XuQin on 2018/7/3.
 */
@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO<ProductVO> list() {
        List<ProductInfo> upAll = productService.findUpAll();
        if (upAll == null){
            return new ResultVO<>();
        }

        List<Integer> categoryTypes = upAll.stream().map(ProductInfo::getCategoryType).collect(Collectors.toList());

        List<ProductCategory> categories = categoryService.findByCategoryTypeIn(categoryTypes);

        //构造数据
        List<ProductVO> productList = new ArrayList<>();
        for (ProductCategory category : categories){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());
            List<ProductInfoVO> productInfoVOS = new ArrayList<>();
            for (ProductInfo product : upAll){
                if (product.getCategoryType().equals(category.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(product, productInfoVO);
                    productInfoVOS.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOS);
            productList.add(productVO);
        }

        return ResultVO.success(productList);
    }
}
