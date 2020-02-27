package com.example.spring.demo.service.impl;

import com.example.spring.demo.dao.ProductInfoDao;
import com.example.spring.demo.dto.CartDTO;
import com.example.spring.demo.entity.OrderDetail;
import com.example.spring.demo.entity.ProductInfo;
import com.example.spring.demo.enums.ProductStatusEnum;
import com.example.spring.demo.enums.ResultEnum;
import com.example.spring.demo.exception.SellException;
import com.example.spring.demo.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    ProductInfoDao productInfoDao;
    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDao.save(productInfo);
    }

    @Override
    public ProductInfo findByProductId(String productId) {
        return productInfoDao.findByProductId(productId);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = productInfoDao.findById(cartDTO.getProductId()).get();
            if(productInfo == null){
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
           Integer result = productInfo.getProductStock()-cartDTO.getProductQuantity();
           if(result<0){
               throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
           }
           if(result>=0){
               productInfo.setProductStock(result);
               productInfoDao.save(productInfo);
           }
        }
    }

    @Transactional
    @Override
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = productInfoDao.findById(cartDTO.getProductId()).get();
            if(productInfo == null){
                throw new SellException(ResultEnum.ORDER_NOT_EXIST);
            }
            Integer productStock = productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(productStock);
            productInfoDao.save(productInfo);
        }
    }
}
