package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.Member;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.entity.QnA;
import com.cartrapido.main.service.CategoryMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
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

    @Query("SELECT p.modifiedDate FROM Product p where p.store=:store ORDER BY p.modifiedDate DESC")
    List<LocalDateTime> martDate(String store);

    void deleteByStore(String store);

    @Query("select p from Product p where p.productName like %:searchValue% and p.store =:store and p.category=:category")
    Page<Product> productSearchCategory(String store, String category, String searchValue, Pageable pageable);

    @Query("select p from Product p where p.productName like %:searchValue% and p.store =:store")
    Page<Product> productSearchCategory(String store, String searchValue, Pageable pageable);

    @Query("select p from Product p where p.productName like %:searchValue%")
    Page<Product> productSearchCategory(String searchValue, Pageable pageable);


    Page<Product> findByProductNameContaining(String searchValue, Pageable pageable);


    //Page<Member> findByEmailContaining(String user, Pageable pageable);

    /*Optional<Member> findByEmail(String email);*/
    /*@Query("SELECT p FROM member p ORDER BY p.id DESC")
    List<Member> findAllMember();*/

}
