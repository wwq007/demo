package com.example.spring.demo.dao;

import com.example.spring.demo.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoDao extends JpaRepository<ProductInfo,String>{
    List<ProductInfo> findByProductStatus(Integer productStatus);
    ProductInfo findByProductId(String productId);
}
