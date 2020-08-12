package com.cartrapido.main.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
<<<<<<< HEAD

=======
>>>>>>> 5963872dc99b808d636acd298534b25b18e6f280
    Optional<Member> findByEmail(String email);

    /*@Query("SELECT p FROM member p ORDER BY p.id DESC")
    List<Member> findAllMember();*/
}
