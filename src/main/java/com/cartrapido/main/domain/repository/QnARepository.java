package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.QnA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnARepository extends JpaRepository<QnA, Integer> {
    Page<QnA> findAllByEmail(String email, Pageable pageable);
}
