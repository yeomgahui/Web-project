package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.OrderSheet;
import com.cartrapido.main.domain.entity.OrderSheetHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderSheetHistoryRepository extends JpaRepository<OrderSheetHistory,Long> {
    List<OrderSheet> findAllByOrderNum(Long orderNum);

}
