package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.entity.QnA;
import com.cartrapido.main.web.dto.QnADTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface QnARepository extends JpaRepository<QnA, Integer> {


}
