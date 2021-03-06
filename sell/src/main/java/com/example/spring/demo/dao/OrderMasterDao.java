package com.example.spring.demo.dao;

import com.example.spring.demo.dto.OrderDTO;
import com.example.spring.demo.entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterDao extends JpaRepository<OrderMaster,String> {

    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid,Pageable pageable);
    OrderMaster findByOrderId(String orderId);
    Page<OrderMaster> findAll(Pageable pageable);

}
