package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.repository.ProductRepository;
import com.cartrapido.main.web.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductService {
    private ProductRepository productRepository;


    @Transactional
    public List<ProductDTO> getProductList(){
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();

        for(Product product : products){
            ProductDTO productDTO = ProductDTO.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .productQty(product.getProductQty())
                    .productContent(product.getProductContent())
                    .store(product.getStore())
                    .category(product.getCategory())
                    .image(product.getImage())
                    .build();

                productDTOList.add(productDTO);

        }



        return productDTOList;
    }

    @Transactional
    public List<ProductDTO> getStoreProductList(String store){
        List<Product> products = productRepository.findAllByStore(store);
        List<ProductDTO> productDTOList = new ArrayList<>();

        for(Product product : products){
            ProductDTO productDTO = ProductDTO.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .productQty(product.getProductQty())
                    .productContent(product.getProductContent())
                    .store(product.getStore())
                    .category(product.getCategory())
                    .image(product.getImage())
                    .build();

            productDTOList.add(productDTO);

        }



        return productDTOList;
    }




}
