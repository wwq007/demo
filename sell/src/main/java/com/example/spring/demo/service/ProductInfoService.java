package com.example.spring.demo.service;

import com.example.spring.demo.dto.CartDTO;
import com.example.spring.demo.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface ProductInfoService {
//    查询所有在架商品列表
    List<ProductInfo>findUpAll();
//    查询所有，分页
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo);
    ProductInfo findByProductId(String productId);

//    减库存

    void decreaseStock(List<CartDTO> cartDTOList);

//    加库存
    void increaseStock(List<CartDTO> cartDTOList);
}
