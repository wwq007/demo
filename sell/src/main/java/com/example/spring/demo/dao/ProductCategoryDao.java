package com.example.spring.demo.dao;

import com.example.spring.demo.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryDao extends JpaRepository<ProductCategory,Integer> {

 List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
