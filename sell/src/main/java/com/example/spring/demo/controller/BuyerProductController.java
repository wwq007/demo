package com.example.spring.demo.controller;

import com.example.spring.demo.VO.ProductInfoVO;
import com.example.spring.demo.VO.ProductVO;
import com.example.spring.demo.VO.ResultVO;
import com.example.spring.demo.entity.ProductCategory;
import com.example.spring.demo.entity.ProductInfo;
import com.example.spring.demo.service.ProductInfoService;
import com.example.spring.demo.service.impl.CategoryServiceImpl;
import com.example.spring.demo.service.impl.ProductInfoServiceImpl;
import com.example.spring.demo.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/buyer/product")
public class BuyerProductController {
    @Autowired
    ProductInfoServiceImpl productInfoService;
    @Autowired
    CategoryServiceImpl categoryService;
    @GetMapping(value = "/list")
    public ResultVO list(){
        //查询所有上架商品
        List<ProductInfo> productInfoList=productInfoService.findUpAll();
        //查询类目（一次性查）
        /**传统做法*/
//        List<Integer> categoryTypeList = new ArrayList<>();
//        for(ProductInfo productInfo:productInfoList){
//            categoryTypeList.add(productInfo.getProductType());
//        }
        /**lambda*/
        List<Integer> categoryTypeList=productInfoList.stream().map(e->e.getProductType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(categoryTypeList);

        //数据拼接
        List<ProductVO> productVOList=new ArrayList<>();
        for(ProductCategory productCategory:productCategoryList){
            ProductVO productVO=new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for(ProductInfo productInfo:productInfoList){
                if(productInfo.getProductType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }
}
