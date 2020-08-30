package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.OrderNumHistory;
import com.cartrapido.main.domain.repository.OrderNumHistoryRepository;
import com.cartrapido.main.domain.repository.OrderNumRepository;
import com.cartrapido.main.web.dto.OrderExtraInfoDTO;
import com.cartrapido.main.web.dto.OrderNumDTO;
import com.cartrapido.main.web.dto.OrderNumHistoryDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderNumHistoryService {
    OrderNumHistoryRepository orderNumHistoryRepository;

    @Transactional
    public OrderNumHistory saveOrderNum(OrderNumHistoryDTO orderNumHistoryDTO){
        OrderNumHistory orderNumHistory = orderNumHistoryRepository.save(orderNumHistoryDTO.toEntitiy());
        return orderNumHistory;
    }

    public List<OrderNumHistoryDTO> getOrderNumHistoryList() {
        List<OrderNumHistory> orderNumList =orderNumHistoryRepository.findAll();

        List<OrderNumHistoryDTO> orderNumDTOList =new ArrayList<>();
        for(OrderNumHistory orderNum : orderNumList){
            OrderNumHistoryDTO orderNumDTO = new OrderNumHistoryDTO(
                    orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay()
            );

            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }

    public List<OrderNumHistoryDTO> getMyOrderNumList(String userEmail) {
        List<OrderNumHistory> orderNumList = orderNumHistoryRepository.findAllByUserEmail(userEmail);
        List<OrderNumHistoryDTO> orderNumDTOList = new ArrayList<>();
        for (OrderNumHistory orderNum : orderNumList) {
            OrderNumHistoryDTO orderNumDTO = new OrderNumHistoryDTO(
                    orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay()
            );
            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }


    public List<OrderNumHistoryDTO> getMyOrderNumListShopper(String shopperEmail) {
        List<OrderNumHistory> orderNumList = orderNumHistoryRepository.findAllByShopper(shopperEmail);
        List<OrderNumHistoryDTO> orderNumDTOList = new ArrayList<>();
        for (OrderNumHistory orderNum : orderNumList) {
            OrderNumHistoryDTO orderNumDTO = new OrderNumHistoryDTO(
                    orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay()
            );
            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }


    public OrderNumHistoryDTO getOrderNum(Long orderNum1){
        OrderNumHistory orderNum = orderNumHistoryRepository.findByOrderNum(orderNum1);
        OrderNumHistoryDTO orderNumDTO = new OrderNumHistoryDTO(
                orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                orderNum.getDeliveryCost(), orderNum.getProductTot(),
                orderNum.getPay(), orderNum.getAddress(), orderNum.getDetailAddress(),
                orderNum.getAgree(), orderNum.getRequest()
        );
        return orderNumDTO;
    }

}
