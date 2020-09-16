package com.cartrapido.main.service;

import com.cartrapido.main.domain.entity.Product;
import com.cartrapido.main.domain.entity.QnA;
import com.cartrapido.main.domain.repository.ProductRepository;
import com.cartrapido.main.web.dto.ProductDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductService {

    @Autowired
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
                    .wishPoint(product.getWishPoint())
                    .build();

                productDTOList.add(productDTO);

        }

        return productDTOList;
    }
    @Transactional
    public Long getTotalNumProduct(){

        return productRepository.count();
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
                    .wishPoint(product.getWishPoint())
                    .build();

            productDTOList.add(productDTO);

        }

        return productDTOList;
    }

    @Transactional
    public List<ProductDTO> getStoreCategoryProductList(String store, String category){
        List<Product> products = productRepository.findAllByStoreAndCategory(store, category);
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
                    .wishPoint(product.getWishPoint())
                    .build();

            productDTOList.add(productDTO);

        }
        return productDTOList;
    }

    public ProductDTO getProductInfo(Long productId) {
        Product product = productRepository.findAllByProductId(productId);
        ProductDTO productDTO = ProductDTO.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productQty(product.getProductQty())
                .productContent(product.getProductContent())
                .store(product.getStore())
                .category(product.getCategory())
                .image(product.getImage())
                .wishPoint(product.getWishPoint())
                .build();
        return productDTO;
    }

    //페이징 적용
    @Transactional
    public List<ProductDTO> getProductList(Pageable pageable){

        Page<Product> pagingProduct = productRepository.findAll(pageable);
        List<Product> products = pagingProduct.getContent();

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
                    .wishPoint(product.getWishPoint())
                    .build();

            productDTOList.add(productDTO);

        }
        return productDTOList;
    }

    @Transactional
    public Page<Product> pagingProduct(Pageable pageable) {
        Page<Product> pagingProduct = productRepository.findAll(pageable);
        return pagingProduct;
    }

    //페이지 적용
    @Transactional
    public List<ProductDTO> getStoreProductList(String store, Pageable pageable){

        Page<Product> pagingStoreProduct = productRepository.findAllByStore(store, pageable);
        List<Product> storeProducts = pagingStoreProduct.getContent();

        List<ProductDTO> productDTOList = new ArrayList<>();

        for(Product product : storeProducts){
            ProductDTO productDTO = ProductDTO.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .productQty(product.getProductQty())
                    .productContent(product.getProductContent())
                    .store(product.getStore())
                    .category(product.getCategory())
                    .image(product.getImage())
                    .wishPoint(product.getWishPoint())
                    .build();

            productDTOList.add(productDTO);

        }
        return productDTOList;
    }

    @Transactional
    public Page<Product> pagingStoreProduct(String store, Pageable pageable) {
        Page<Product> pagingStoreProduct = productRepository.findAllByStore(store, pageable);
        return pagingStoreProduct;
    }

    //페이지 적용
    @Transactional
    public List<ProductDTO> getCategoryProductList(String store, String category, Pageable pageable){

        Page<Product> pagingCategoryProduct = productRepository.findAllByStoreAndCategory(store, category, pageable);
        List<Product> categoryProducts = pagingCategoryProduct.getContent();

        List<ProductDTO> productDTOList = new ArrayList<>();

        for(Product product : categoryProducts){
            ProductDTO productDTO = ProductDTO.builder()
                    .productId(product.getProductId())
                    .productName(product.getProductName())
                    .productPrice(product.getProductPrice())
                    .productQty(product.getProductQty())
                    .productContent(product.getProductContent())
                    .store(product.getStore())
                    .category(product.getCategory())
                    .image(product.getImage())
                    .wishPoint(product.getWishPoint())
                    .build();

            productDTOList.add(productDTO);

        }
        return productDTOList;
    }

    @Transactional
    public Page<Product> pagingCategoryProduct(String store, String category, Pageable pageable) {
        Page<Product> pagingCategoryProduct = productRepository.findAllByStoreAndCategory(store, category, pageable);
        return pagingCategoryProduct;
    }

    @Transactional
    public List<String> getCategoryList(String mart){
        List<String> getCategoryList = productRepository.findDistinctByStore(mart);

//        String category="";
//        List<String> categoryList = new ArrayList<String>();
//
//        for(CategoryMapping str: getCategoryList){
//            category= str.toString();
//            categoryList.add(category);
//        }
        return getCategoryList;
    }

    public void updateWishPoint(Long productId){
        Product product = productRepository.findAllByProductId(productId);
        product.setWishPoint(product.getWishPoint()+1);
        productRepository.save(product);
    }
}
