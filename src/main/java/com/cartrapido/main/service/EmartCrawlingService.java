package com.cartrapido.main.service;

import com.cartrapido.main.domain.repository.ProductRepository;
import com.cartrapido.main.web.dto.ProductDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class EmartCrawlingService {

    @Autowired
    private ProductRepository productRepository;

    private String[] array_url = {"http://emart.ssg.com/category/main.ssg?dispCtgId=6000095739", //EMART_FRUIT_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095740", //EMART_VEGETABLE_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095498", //EMART_RICEANDNUTS_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095499", //EMART_MEATANDEGGS_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095501", //EMART_MILK_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095500", //EMART_SEAFOOD_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095502", //EMART_CONVENIENCEFOOD_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095503", //EMART_KIMCHIANDMEALKIT_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095507",//EMART_DRINK_URL
            "http://emart.ssg.com/category/main.ssg?dispCtgId=6000095508" //EMART_COFFEEANDTEA_URL
    };

    private String[] array_category = {"Fruit", //EMART_FRUIT_URL
            "Vegetable", //EMART_VEGETABLE_URL
            "Rice&Nuts", //EMART_RICEANDNUTS_URL
            "Meat&Eggs", //EMART_MEATANDEGGS_URL
            "Milk", //EMART_MILK_URL
            "Seafood", //EMART_SEAFOOD_URL
            "Conveniencefood", //EMART_CONVENIENCEFOOD_URL
            "SideDish&Mealkit", //EMART_KIMCHIANDMEALKIT_URL
            "Drink",//EMART_DRINK_URL
            "Coffee&Tea" //EMART_COFFEEANDTEA_URL
    };

//    @PostConstruct
//    public void getEmartFruitDatas() {
//
//        productRepository.deleteAll();
//
//        //for(String url : array_url) {
//        for(int j = 0; j < array_url.length ; j++){
//
//            try {
//                Document doc = Jsoup.connect(array_url[j]).get();
//
//                Elements elements = doc.select("div#ty_thmb_view ul");
//
//                for (Element element : elements) {
//                    int i;
//                    for (i = 0; i < elements.select("li.cunit_t232").size(); i++) {
//                        String productId = elements.select("input[name=attnTgtIdnfNo1]").get(i).attr("value");
//                        String productName = elements.select("em.tx_en").get(i).text();
//                        String stringProductPrice = elements.select("em.ssg_price").get(i).text();
//                        String[] ProductPriceSplit = stringProductPrice.split(",");
//                        String productPrice = "";
//                        for (String str : ProductPriceSplit) {
//                            productPrice = productPrice + str;
//                        }
//                        System.out.println(productPrice);
//                        String image = elements.select("a.clickable img.i1").get(i).attr("src");
//                        System.out.println(image);
//
//                        ProductDTO productDTO = ProductDTO.builder()
//                                .productId(Long.parseLong(productId))
//                                .productName(productName)
//                                .productPrice(Integer.parseInt(productPrice))
//                                .productQty(10)
//                                .productContent("aa")
//                                .store("emart")
//                                .category(array_category[j])
//                                .image(image)
//                                .build();
//
//                        productRepository.save(productDTO.toEntity());
//                    }
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
