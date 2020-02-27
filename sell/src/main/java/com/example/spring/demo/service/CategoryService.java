package com.example.spring.demo.service;

import com.example.spring.demo.entity.ProductCategory;


import java.util.List;
public interface CategoryService {
    ProductCategory findById(Integer id);
    List<ProductCategory> findAll();
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    ProductCategory save(ProductCategory productCategory);
}
