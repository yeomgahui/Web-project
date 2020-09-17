package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.repository.OrderNumRepository;
import com.cartrapido.main.web.dto.OrderExtraInfoDTO;
import com.cartrapido.main.web.dto.OrderNumDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
            OrderNumDTO orderNumDTO =OrderNumDTO.builder()
                    .orderNum(orderNum.getOrderNum())
                    .userEmail(orderNum.getUserEmail())
                    .shopper(orderNum.getShopper())
                    .deliveryCost(orderNum.getDeliveryCost())
                    .productTot(orderNum.getProductTot())
                    .pay(orderNum.getPay())
                    .createdDate(orderNum.getCreatedDate())
                    .build();
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
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay(), orderNum.getCreatedDate()
            );
            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }

    public List<OrderNumDTO> getPaidOrder(String userEmail, String pay) {
        List<OrderNum> orderNumList = orderNumRepository.findAllByUserEmailAndPay(userEmail,pay);
        List<OrderNumDTO> orderNumDTOList = new ArrayList<>();
        for (OrderNum orderNum : orderNumList) {
            OrderNumDTO orderNumDTO = new OrderNumDTO(
                    orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay(), orderNum.getCreatedDate()
            );
            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }
    public List<OrderNumDTO> shopperGetPaidOrder(String pay) {
        List<OrderNum> orderNumList = orderNumRepository.findAllByPay(pay);
        List<OrderNumDTO> orderNumDTOList = new ArrayList<>();
        for (OrderNum orderNum : orderNumList) {
            OrderNumDTO orderNumDTO = new OrderNumDTO(
                    orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay(), orderNum.getCreatedDate()
            );
            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }


    public String acceptOrder(Long orderNum, String shopperEmail) {
        OrderNum orderNum1 = orderNumRepository.findByOrderNum(orderNum);
        orderNum1.setShopper(shopperEmail);

        return orderNumRepository.save(orderNum1).getUserEmail();
    }

    public List<OrderNumDTO> getMyOrderNumListShopper(String shopperEmail) {
        List<OrderNum> orderNumList = orderNumRepository.findAllByShopper(shopperEmail);
        List<OrderNumDTO> orderNumDTOList = new ArrayList<>();
        for (OrderNum orderNum : orderNumList) {
            OrderNumDTO orderNumDTO = new OrderNumDTO(
                    orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay(), orderNum.getCreatedDate()
            );
            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }

    public void updatePay(Long orderNum,HttpSession session){
//        HashMap clientLatlng = (HashMap) session.getAttribute("clientLatlng");
        OrderNum orderNum1 = orderNumRepository.findByOrderNum(orderNum);
        orderNum1.setPay("true");
//        orderNum1.setLongitude((Double) clientLatlng.get("lng"));
//        orderNum1.setLatitude((Double) clientLatlng.get("lat"));
        orderNumRepository.save(orderNum1);
        OrderNum orderNum2 = orderNumRepository.findByOrderNum(orderNum);
    }

    public void deleteOrder(Long orderNum) {
        orderNumRepository.deleteById(orderNum);
    }

    public OrderNumDTO getOrderNum(Long orderNum1){
        OrderNum orderNum = orderNumRepository.findByOrderNum(orderNum1);
        OrderNumDTO orderNumDTO = OrderNumDTO.builder()
                .orderNum(orderNum.getOrderNum())
                .userEmail(orderNum.getUserEmail())
                .shopper(orderNum.getShopper())
                .deliveryCost(orderNum.getDeliveryCost())
                .productTot(orderNum.getProductTot())
                .pay(orderNum.getPay())
                .address(orderNum.getAddress())
                .detailAddress(orderNum.getDetailAddress())
                .agree(orderNum.getAgree())
                .request(orderNum.getRequest())
                .createdDate(orderNum.getCreatedDate())
                .build();
        return orderNumDTO;
    }

    public void saveAddress(OrderExtraInfoDTO orderExtraInfoDTO) {
        OrderNum orderNum1 = orderNumRepository.findByOrderNum(orderExtraInfoDTO.getOrderNum());
        orderNum1.setAddress(orderExtraInfoDTO.getAddress());
        orderNum1.setDetailAddress(orderExtraInfoDTO.getDetailAddress());
        orderNum1.setAgree(orderExtraInfoDTO.getAgree());
        orderNum1.setRequest(orderExtraInfoDTO.getRequest());
        orderNumRepository.save(orderNum1);
    }

    public Long getTotalNumOrder(){
        return orderNumRepository.count();
    }

}
