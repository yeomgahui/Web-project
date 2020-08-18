package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.OrderNum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderNumRepository extends JpaRepository<OrderNum,Long> {
    List<OrderNum> findAllByUserEmail(String userEmail);
    List<OrderNum> findAllByShopper(String shopper);

    OrderNum findByOrderNum(Long orderNum);

}
