package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.service.CategoryMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findAllByStore(String store);
    List<Product> findAllByCategory(String catrgory);
    Product findAllByProductId(Long productId);
    List<Product> findAllByStoreAndCategory(String store, String category);

    Page<Product> findAllByStore(String store, Pageable pageable);
    Page<Product> findAllByStoreAndCategory(String store, String category, Pageable pageable);

    @Query("SELECT distinct p.category FROM Product p where p.store =:store")
    List<String> findDistinctByStore(String store);

    /*Optional<Member> findByEmail(String email);*/
    /*@Query("SELECT p FROM member p ORDER BY p.id DESC")
    List<Member> findAllMember();*/

}
