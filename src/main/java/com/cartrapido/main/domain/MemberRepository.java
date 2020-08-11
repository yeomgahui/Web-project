package com.cartrapido.main.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);

    /*@Query("SELECT p FROM member p ORDER BY p.id DESC")
    List<Member> findAllMember();*/
}
