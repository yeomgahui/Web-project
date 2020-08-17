package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.OrderNum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderNumRepository extends JpaRepository<OrderNum,Long> {



}
