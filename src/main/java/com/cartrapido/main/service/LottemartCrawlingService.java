package com.cartrapido.main.service;

import com.cartrapido.main.domain.repository.ProductRepository;
import com.cartrapido.main.web.dto.ProductDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class LottemartCrawlingService {

    @Autowired
    private ProductRepository productRepository;

    private String[] array_url = {
            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&collection_id=7&mallId=4&platform=pc&login=Y&u9=eachmall&u1=LTMT&u2=LM10100002&u3=LM10100002", //Lotte_Fruit&Nuts_URL
//            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM10100009&login=Y&mallId=4", //Lotte_Seafood_URL
//            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM10100014&login=Y&mallId=4", ///Lotte_Meat&Eggs_URL
//            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM10100016&login=Y&mallId=4", //Lotte_Vegetable_URL
//            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM10100011&login=Y&mallId=4", //Lotte_Rice_URL
//            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM10100013&login=Y&mallId=4", //Lotte_Milk&ConvenienceFood_URL
//            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM10100006&login=Y&mallId=4", //Lotte_Water&Juice_URL
//            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM40004031&login=Y&mallId=4", //Lotte_Drink_URL
//            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM10100003&login=Y&mallId=4", //Lotte_Bakery&FastFood_URL
//            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM10100004&login=Y&mallId=4", //Lotte_Noodles&InstantFood_URL
//            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM10100017&login=Y&mallId=4", //Lotte_Sauce&Can_URL
//            "https://www.lotteon.com/search/render/render.ecn?render=nqapi&platform=pc&collection_id=301&u9=navigate&u8=LM10100001&login=Y&mallId=4" //Lotte_HealthFood_URL
    };

    private String[] array_category = {
            "Fruit&Nuts",
            "Seafood",
            "Meat&Eggs",
            "Vegetable",
            "Rice",
            "Milk&ConvenienceFood",
            "Water&Juice",
            "Drink",
            "Bakery&FastFood",
            "Noodles&InstantFood",
            "Sauce&Can",
            "HealthFood"
    };

    @PostConstruct
    public void getLottermartDatas() {

//        List<LocalDateTime> list = productRepository.emartDate();
//
//        if (list.size() != 0) {
//            LocalDate latest = LocalDate.from(list.get(0));
//            LocalDate today = LocalDate.from(LocalDateTime.now());
//
//            if (today.isAfter(latest)) {
//
//                productRepository.deleteAll();

        System.out.println("lotte");
                for (int j = 0; j < array_url.length; j++) {

                    try {

                        Document doc = Jsoup.connect(array_url[j])
                                .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
                                .header("scheme", "https")
                                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                                .header("accept-encoding", "gzip, deflate, br")
                                .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6")
                                .header("cache-control", "no-cache")
                                .header("pragma", "no-cache")
                                .header("upgrade-insecure-requests", "1")
                                .get();
                        System.out.println(doc);
                        Elements elements = doc.select("section[data-area=bes_ct_pd] ul");
                        //for (Element element1 : elements) {
                            int i;
                            for (i = 0; i < doc.select("section[data-area=bes_ct_pd] ul").size(); i++) {

                                System.out.println(i);

                                String productName = elements.select("div.srchProductUnitTitle").get(i).text();
                                System.out.println(productName);

                                String stringProductPrice = elements.select("div.srchProductUnitPriceArea strong").get(i).text();
                                String[] ProductPriceSplit = stringProductPrice.split(",");
                                String productPrice = "";
                                for (String str : ProductPriceSplit) {
                                    productPrice = productPrice + str;
                                }
                                System.out.println(productPrice);

                                String image = elements.select("div.srchThumbImageWrap img").get(i).attr("src");
                                System.out.println(image);

                                String itemId = "";
                                System.out.println(itemId);


                                String productcontent = "";
                                //if (itemId.equals("0000010006566")) {

//                                   String array_contentUrl = "http://emart.ssg.com/item/itemView.ssg?itemId=" + itemId;
//                                   System.out.println(array_contentUrl);
//
//                                    Document doc1 = Jsoup.connect(array_contentUrl).get();
//
//                                    productcontent = doc1.select("table[summary='상품 필수정보 보여주는 표']").html();
                                //}

                                ProductDTO productDTO = ProductDTO.builder()
                                        .itemId(itemId)
                                        .productName(productName)
                                        .productPrice(Integer.parseInt(productPrice))
                                        .productQty(10)
                                        .productContent(productcontent)
                                        .store("lottemart")
                                        .category(array_category[j])
                                        .image(image)
                                        .build();

                                //productRepository.save(productDTO.toEntity()).getProductId();
                            }
                        //}

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
//    }
//
//}
