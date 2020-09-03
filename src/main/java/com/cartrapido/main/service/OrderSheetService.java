package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.OrderSheet;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.repository.OrderSheetRepository;
import com.cartrapido.main.domain.repository.ProductRepository;
import com.cartrapido.main.web.dto.OrderSheetDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderSheetService {
    OrderSheetRepository orderSheetRepository;
    ProductRepository productRepository;
    @Transactional
    public void saveOrderSheet(OrderSheetDTO orderSheetDTO) {
        System.out.println("=============saveOrderSheet================"+orderSheetDTO.getProductName());
        OrderSheet orderSheet = orderSheetRepository.save(orderSheetDTO.toEntitiy());

    }

    public List<OrderSheetDTO> getOrderSheetList(Long orderNum) {
        List<OrderSheet> orderSheetList = orderSheetRepository.findAllByOrderNum(orderNum);
        List<OrderSheetDTO> orderSheetDTOList = new ArrayList<>();

            for(OrderSheet orderSheet : orderSheetList){
                OrderSheetDTO orderSheetDTO = new OrderSheetDTO(
                        orderNum, orderSheet.getUserEmail(),
                        orderSheet.getProductId(), orderSheet.getAmount()
                );
                orderSheetDTO.setOtherInfo(
                        orderSheet.getProductName(), orderSheet.getProductPrice(),
                        orderSheet.getStore(), orderSheet.getImage()
                );
                orderSheetDTOList.add(orderSheetDTO);
            }
        return orderSheetDTOList;
    }
}
