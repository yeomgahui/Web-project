package com.cartrapido.main.service;

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
                    .itemId(wishItem.getItemId())
                    .category(wishItem.getCategory())
                    .email(wishItem.getEmail())
                    .image(wishItem.getImage())
                    .productContent(wishItem.getProductContent())
                    .productId(wishItem.getProductId())
                    .productName(wishItem.getProductName())
                    .store(wishItem.getStore())
                    .productPrice(wishItem.getProductPrice())
                    .wiSequence(wishItem.getWiSequence())
                    .build();
            wishItemDTOList.add(wishItemDTO);
        }
        return wishItemDTOList;
    }
}
