package com.cartrapido.main.domain.repository;


import com.cartrapido.main.domain.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query(value="Select * from visit where date =DATE_FORMAT(date, '%y-%m-%d') = curdate()",nativeQuery=true)
    Optional<Visit> findByDate();
}
