package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.OrderSheet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderSheetRepository extends JpaRepository<OrderSheet,Long> {
    List<OrderSheet> findAllByOrderNum(Long orderNum);
    int countAllByStore(String store);
}
