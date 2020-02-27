package com.example.spring.demo.dao;

import com.example.spring.demo.entity.ProductInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;


@SpringBootTest
class ProductInfoDaoTest {
    @Autowired
    ProductInfoDao productInfoDao;
    @Test
    public void findByProductStatus(){
        System.out.println(productInfoDao.findByProductStatus(0));
    }
    @Test
    public void save(){
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋粥");
        productInfo.setProductPrice(new BigDecimal(12));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("好吃的粥");
        productInfo.setProductIcon("http.xxxx.jsp");
        productInfo.setProductStatus(0);
        productInfo.setProductType(1);
        productInfoDao.save(productInfo);
    }

}