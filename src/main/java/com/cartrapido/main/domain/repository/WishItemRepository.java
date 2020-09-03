package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishItemRepository extends JpaRepository<WishItem, Long> {
    List<WishItem> findAllByEmail(String email);

}
