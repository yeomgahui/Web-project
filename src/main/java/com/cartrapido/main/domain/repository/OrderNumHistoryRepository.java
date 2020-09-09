package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.OrderNumHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderNumHistoryRepository extends JpaRepository<OrderNumHistory,Long> {
//    List<OrderNumHistory> findAllByUserEmail(String userEmail, Pageable pageable);
    Page<OrderNumHistory> findAllByUserEmail(String userEmail, Pageable pageable);
    List<OrderNumHistory> findAllByShopper(String shopper);
    OrderNumHistory findByOrderNum(Long orderNum);
    OrderNumHistory findByOriOrderNum(Long orderNum);

}
