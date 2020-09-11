package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.OrderNumHistory;
import com.cartrapido.main.domain.repository.OrderNumHistoryRepository;
import com.cartrapido.main.domain.repository.OrderNumRepository;
import com.cartrapido.main.web.dto.OrderExtraInfoDTO;
import com.cartrapido.main.web.dto.OrderNumDTO;
import com.cartrapido.main.web.dto.OrderNumHistoryDTO;
import com.cartrapido.main.web.dto.SalesOfMonthDTO;
import lombok.AllArgsConstructor;
import org.hibernate.event.spi.SaveOrUpdateEventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                    orderNum.getOriOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                    orderNum.getDeliveryCost(), orderNum.getProductTot(),
                    orderNum.getPay()
            );

            orderNumDTOList.add(orderNumDTO);
        }
        return  orderNumDTOList;
    }

    public Page<OrderNumHistory> getMyOrderNumListPage(String userEmail, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 3);
        System.out.println();
        return orderNumHistoryRepository.findAllByUserEmail(userEmail,pageable);
    }

/*    public List<OrderNumHistoryDTO> getMyOrderNumList(String userEmail, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 3);

        List<OrderNumHistory> orderNumList = orderNumHistoryRepository.findAllByUserEmail(userEmail,pageable);
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
    }*/


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
                orderNum.getAgree(), orderNum.getRequest(), orderNum.getCreatedDate()
        );
        return orderNumDTO;
    }

    public OrderNumHistoryDTO findByOriOrderNum(Long orderNum1) {
        OrderNumHistory orderNum = orderNumHistoryRepository.findByOriOrderNum(orderNum1);
        OrderNumHistoryDTO orderNumDTO = new OrderNumHistoryDTO(
                orderNum.getOrderNum(), orderNum.getUserEmail(), orderNum.getShopper(),
                orderNum.getDeliveryCost(), orderNum.getProductTot(),
                orderNum.getPay(), orderNum.getAddress(), orderNum.getDetailAddress(),
                orderNum.getAgree(), orderNum.getRequest(), orderNum.getCreatedDate()
        );
        return orderNumDTO;
    }
    public Long getTotalNumHistory(){
        return orderNumHistoryRepository.count();
    }
    //중계 수수료 계산
    public String getTotalRevenue(){
       Long revenue= orderNumHistoryRepository.selectTotals();
       Long revenueBrut = Math.round((double)revenue*0.1);
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(revenueBrut);
    }
    @Transactional
    public List<SalesOfMonthDTO> salesOfMonth(){
        List<Object[]> result = orderNumHistoryRepository.salesOfMonth();
        System.out.println(result.size());
        List<SalesOfMonthDTO> sales = new ArrayList<SalesOfMonthDTO>();
        for(Object[] obj : result){
            SalesOfMonthDTO smDTO = new SalesOfMonthDTO();
            smDTO.setMonth(obj[0]);
            smDTO.setSalesMonth(obj[1]);
            sales.add(smDTO);
        }
        return sales;
    }
}
