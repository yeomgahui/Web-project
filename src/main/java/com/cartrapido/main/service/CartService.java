package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.Cart;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.repository.CartRepository;
import com.cartrapido.main.web.dto.CartDTO;
import com.cartrapido.main.web.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {
    CartRepository cartRepository;

    //장바구니에 넣기
    @Transactional
    public Cart saveCart(CartDTO cartDTO) {
        Cart cart = cartRepository.save(cartDTO.toEntity());
        return cart;
    }

    //중복상품 확인    
    @Transactional
    public boolean checkCart(Long productId, String userEmail) {
        System.out.print("중복 상품 있는지 확인");
        Cart cart = cartRepository.findAllByProductIdAndUserEmail(productId, userEmail);

        if (cart==null) {
            return true;
        } else return false;
    }

    @Transactional
    public void updateCart(Long productId, String userEmail){
        Cart cart = cartRepository.findAllByProductIdAndUserEmail(productId, userEmail);
    }

    //장바구니 리스트 조회
    @Transactional
    public List<CartDTO> getCartList(String userEmail) {
        List<Cart> cartslist = cartRepository.findAllByUserEmail(userEmail);
        List<CartDTO> cartDTOList = new ArrayList<>();

        for (Cart cart : cartslist) {
            CartDTO cartDTO = CartDTO.builder()
                    .cartId(cart.getCartId())
                    .userEmail(cart.getUserEmail())
                    .productId(cart.getProductId())
                    .amount(cart.getAmount())
                    .build();

            cartDTOList.add(cartDTO);
        }
        return cartDTOList;
    }

    //장바구니 상품 정보
    public CartDTO getCartIdInfo(Long cartId) {
        Cart cart = cartRepository.findAllByCartId(cartId);
        CartDTO cartDTO = CartDTO.builder()
                .cartId(cart.getCartId())
                .userEmail(cart.getUserEmail())
                .productId(cart.getProductId())
                .amount(cart.getAmount())
                .build();
        return cartDTO;

    }

    //장바구니에서 제거
    public void deleteCart(Long cartId) {
        cartRepository.deleteById(cartId);
        System.out.println(cartId+" 카트 튜플을 삭제");
    }

}
