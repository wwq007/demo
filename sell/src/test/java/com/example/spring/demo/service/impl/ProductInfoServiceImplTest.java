package com.example.spring.demo.service.impl;

import com.example.spring.demo.entity.ProductInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductInfoServiceImplTest {
@Autowired
ProductInfoServiceImpl productInfoService;
    @Test
    void findUpAll() {
        System.out.println(productInfoService.findUpAll());

    }

    @Test
    void findAll() throws Exception{
        PageRequest pageRequest=PageRequest.of(1,2);
        Page<ProductInfo> productInfoPage=productInfoService.findAll(pageRequest);
        System.out.println(productInfoPage);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    void save() {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("皮皮虾");
        productInfo.setProductPrice(new BigDecimal(12));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("好吃的虾");
        productInfo.setProductIcon("http.xxxx.jsp");
        productInfo.setProductStatus(0);
        productInfo.setProductType(1);
        productInfoService.save(productInfo);
    }

    @Test
    void findById() {
        ProductInfo productInfo=productInfoService.findByProductId("123456");
        System.out.println(productInfo);
    }
}