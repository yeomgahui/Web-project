package com.cartrapido.main.domain.repository;

import com.cartrapido.main.domain.entity.OrderNum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderNumRepository extends JpaRepository<OrderNum,Long> {
    List<OrderNum> findAllByUserEmail(String userEmail);
    List<OrderNum> findAllByUserEmailAndPay(String userEmail, int pay);
    List<OrderNum> findAllByPay(int pay);


    List<OrderNum> findAllByShopper(String shopper);


    OrderNum findByOrderNum(Long orderNum);

/*    @Query("update OrderNum set pay=:pay  where orderNum =:orderNum")
    void updatePay(Long orderNum, int pay);*/
}
