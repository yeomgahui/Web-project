package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.OrderNumHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderNumHistoryRepository extends JpaRepository<OrderNumHistory,Long> {
//    List<OrderNumHistory> findAllByUserEmail(String userEmail, Pageable pageable);
    Page<OrderNumHistory> findAllByUserEmail(String userEmail, Pageable pageable);
    Page<OrderNumHistory> findAllByUserEmailOrderByCreatedDateDesc(String userEmail, Pageable pageable);
    List<OrderNumHistory> findAllByShopper(String shopper);
    OrderNumHistory findByOrderNum(Long orderNum);
    OrderNumHistory findByOriOrderNum(Long orderNum);
    //중계 수수료 계산
    @Query(value="select sum(m.delivery_cost) FROM order_num_history m",nativeQuery = true)
    Long selectTotals();

    @Query(value = "SELECT DATE_FORMAT(created_date, '%m') as month, COUNT(created_date) as salesMonth FROM order_num_history GROUP BY DATE_FORMAT(created_date, '%m') Order by month asc",nativeQuery = true)
    List<Object[]> salesOfMonth();

    @Query(value = "SELECT COUNT(created_date) as salesMonth FROM order_num_history WHERE DATE_FORMAT(created_date, '%y-%m-%d') = curdate();",nativeQuery = true)
    Long getTodaySales();

}
