package com.example.spring.demo.dao;

import com.example.spring.demo.entity.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@Slf4j
class ProductCategoryDaoTest {
    @Autowired
    ProductCategoryDao productCategoryDao;

    @Test
    public void findById(){
        System.out.println(productCategoryDao.findById(1));
    }
    @Test
    public void update(){

        ProductCategory productCategory=productCategoryDao.findById(2).get();
        productCategory.setCategoryType(4);
        productCategoryDao.save(productCategory);
    }

    @Test
    public void save(){

        ProductCategory productCategory=new ProductCategory();
        productCategory.setCategoryId(3);
        productCategory.setCategoryName("女生最爱");
        productCategory.setCategoryType(3);
        productCategoryDao.save(productCategory);
    }
    @Test
    public void findByCategoryType(){
        List<Integer> list= Arrays.asList(1,2,3,4);
        System.out.println(productCategoryDao.findByCategoryTypeIn(list).toString());
    }



}