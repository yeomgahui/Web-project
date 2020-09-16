package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.Cart;
import com.cartrapido.main.domain.entity.WishItem;
import com.cartrapido.main.domain.repository.WishItemRepository;
import com.cartrapido.main.web.dto.WishItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class WishItemService {
    @Autowired
    private WishItemRepository wishItemRepository;

    public void saveWishItem(WishItemDTO wishItemDTO) {
        wishItemRepository.save(wishItemDTO.toEntity());
    }

    public List<WishItemDTO> findByEmail(String userEmail) {
        List<WishItem> list = wishItemRepository.findAllByEmail(userEmail);
        List<WishItemDTO> wishItemDTOList = new ArrayList<>();
        for(WishItem wishItem:list){
            WishItemDTO wishItemDTO = WishItemDTO.builder()
                    .productId(wishItem.getProductId())
                    .wiSequence(wishItem.getWiSequence())
                    .email(wishItem.getEmail())
                    .build();
            wishItemDTOList.add(wishItemDTO);
        }
        return wishItemDTOList;
    }

    public boolean checkWishList(Long productId, String email) {
        WishItem wishItem = wishItemRepository.findAllByProductIdAndEmail(productId, email);

        if (wishItem==null) {
            return true;
        } else return false;
    }

    public void deleteWishItem(Long wiSequence) {
        wishItemRepository.deleteById(wiSequence);
    }

}
