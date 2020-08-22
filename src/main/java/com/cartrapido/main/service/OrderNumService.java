package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.repository.OrderNumRepository;
import com.cartrapido.main.web.dto.OrderNumDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
                    orderNum.getLongitude(), orderNum.getLatitud(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay()
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
                    orderNum.getLongitude(), orderNum.getLatitud(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay()
            );
            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }

    public List<OrderNumDTO> getPaidOrder(String userEmail, int pay) {
        List<OrderNum> orderNumList = orderNumRepository.findAllByUserEmailAndPay(userEmail,pay);
        List<OrderNumDTO> orderNumDTOList = new ArrayList<>();
        for (OrderNum orderNum : orderNumList) {
            OrderNumDTO orderNumDTO = new OrderNumDTO(
                    orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getLongitude(), orderNum.getLatitud(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay()
            );
            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }
    public List<OrderNumDTO> shopperGetPaidOrder(int pay) {
        List<OrderNum> orderNumList = orderNumRepository.findAllByPay(pay);
        List<OrderNumDTO> orderNumDTOList = new ArrayList<>();
        for (OrderNum orderNum : orderNumList) {
            OrderNumDTO orderNumDTO = new OrderNumDTO(
                    orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getLongitude(), orderNum.getLatitud(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay()
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
                    orderNum.getLongitude(), orderNum.getLatitud(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay()
            );
            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }

    public void updatePay(Long orderNum){
        OrderNum orderNum1 = orderNumRepository.findByOrderNum(orderNum);
        orderNum1.setPay(1);
        orderNumRepository.save(orderNum1);
    }

    public void deleteOrder(Long orderNum) {
        orderNumRepository.deleteById(orderNum);
    }

    public OrderNumDTO getOrderNum(Long orderNum1){
        OrderNum orderNum = orderNumRepository.findByOrderNum(orderNum1);
        OrderNumDTO orderNumDTO = new OrderNumDTO(
                orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                orderNum.getLongitude(), orderNum.getLatitud(),
                orderNum.getDeliveryCost(), orderNum.getProductTot(),
                orderNum.getPay()
        );
        return orderNumDTO;
    }

}
