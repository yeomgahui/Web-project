package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.OrderNumHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderNumHistoryRepository extends JpaRepository<OrderNumHistory,Long> {
    List<OrderNumHistory> findAllByUserEmail(String userEmail);
    List<OrderNumHistory> findAllByShopper(String shopper);
    OrderNumHistory findByOrderNum(Long orderNum);
    OrderNumHistory findByOriOrderNum(Long orderNum);

    //중계 수수료 계산
    @Query(value="select sum(m.delivery_cost) FROM order_num_history m",nativeQuery = true)
    Long selectTotals();

}
