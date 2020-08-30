package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.OrderNumHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderNumHistoryRepository extends JpaRepository<OrderNumHistory,Long> {
    List<OrderNumHistory> findAllByUserEmail(String userEmail);
    List<OrderNumHistory> findAllByShopper(String shopper);
    OrderNumHistory findByOrderNum(Long orderNum);

}
