package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.OrderNum;
import com.cartrapido.main.domain.entity.OrderSheet;
import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.repository.OrderSheetRepository;
import com.cartrapido.main.domain.repository.ProductRepository;
import com.cartrapido.main.web.dto.OrderSheetDTO;
import com.cartrapido.main.web.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OrderSheetService {

    OrderSheetRepository orderSheetRepository;
    ProductRepository productRepository;

    public void updateOrderSheet(Long orderNum, HttpSession session) {
        List<OrderSheet> orderSheetList = orderSheetRepository.findAllByOrderNum(orderNum);
        ArrayList<Map> latlng = (ArrayList) session.getAttribute("market");
        for(OrderSheet orderSheet:orderSheetList){
            for(Map data:latlng){
                if(orderSheet.getStore().equals(data.get("market"))){
                    System.out.println(data.get("market"));
                    orderSheet.setLatitude((Double) data.get("lat"));
                    orderSheet.setLongitude((Double) data.get("lng"));
                    orderSheetRepository.save(orderSheet);
                }
            }
        }
    }


    @Transactional
    public void saveOrderSheet(OrderSheetDTO orderSheetDTO) {
        OrderSheet orderSheet = orderSheetRepository.save(orderSheetDTO.toEntitiy());

    }

    public List<OrderSheetDTO> getOrderSheetList(Long orderNum) {
        List<OrderSheet> orderSheetList = orderSheetRepository.findAllByOrderNum(orderNum);
        List<OrderSheetDTO> orderSheetDTOList = new ArrayList<>();

            for(OrderSheet orderSheet : orderSheetList){
                OrderSheetDTO orderSheetDTO = OrderSheetDTO.builder()
                        .orderNum(orderNum)
                        .productId(orderSheet.getProductId())
                        .amount(orderSheet.getAmount())
                        .build();
                Product product = productRepository.findAllByProductId(orderSheet.getProductId());
                orderSheetDTO.setOtherInfo(
                        product.getProductName(), product.getProductPrice(),
                        product.getStore(), product.getImage()
                );
                orderSheetDTOList.add(orderSheetDTO);
            }
        return orderSheetDTOList;
    }

    public int storeRank(String store){
        int count = orderSheetRepository.countAllByStore(store);
        return count;
    }

}
