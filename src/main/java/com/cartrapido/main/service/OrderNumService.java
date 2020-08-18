package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.repository.OrderNumRepository;
import com.cartrapido.main.web.dto.OrderNumDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderNumService {
    OrderNumRepository orderNumRepository;

    @Transactional
    public OrderNum saveOrderNum(OrderNumDTO orderNumDTO){
        OrderNum orderNum = orderNumRepository.save(orderNumDTO.toEntitiy());
        return orderNum;
    }

    public List<OrderNumDTO> getOrderNumList() {
        List<OrderNum> orderNumList =orderNumRepository.findAll();

        List<OrderNumDTO> orderNumDTOList =new ArrayList<>();
        for(OrderNum orderNum : orderNumList){
            OrderNumDTO orderNumDTO = new OrderNumDTO(
                    orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getLongitude(), orderNum.getLatitud()
            );

            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }

    public List<OrderNumDTO> getMyOrderNumList(String userEmail) {
        List<OrderNum> orderNumList = orderNumRepository.findAllByUserEmail(userEmail);
        List<OrderNumDTO> orderNumDTOList = new ArrayList<>();
        for (OrderNum orderNum : orderNumList) {
            OrderNumDTO orderNumDTO = new OrderNumDTO(
                    orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getLongitude(), orderNum.getLatitud()
            );
            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }

    public void acceptOrder(Long orderNum, String shopperEmail) {
        OrderNum orderNum1 = orderNumRepository.findByOrderNum(orderNum);
        orderNum1.setShopper(shopperEmail);
        orderNumRepository.save(orderNum1);

    }

    public List<OrderNumDTO> getMyOrderNumListShopper(String shopperEmail) {
        List<OrderNum> orderNumList = orderNumRepository.findAllByShopper(shopperEmail);
        List<OrderNumDTO> orderNumDTOList = new ArrayList<>();
        for (OrderNum orderNum : orderNumList) {
            OrderNumDTO orderNumDTO = new OrderNumDTO(
                    orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getLongitude(), orderNum.getLatitud()
            );
            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }

/*    public OrderNumDTO getOrderNum(OrderNum orderNum){
        OrderNumDTO orderNumDTO = orderNum.builder()
                .orderNum(orderNum.getOrderNum())
                .latitud(orderNum.getLatitud())
                .longitude(orderNum.getLongitude())
                .shopper(orderNum.getShopper())
                .userEmail(orderNum.getUserEmail())
                .build();

    }*/

}
