package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);


    Page<Member> findByNameContaining(String user,Pageable pageable);
    Page<Member> findByEmailContaining(String user, Pageable pageable);

}
