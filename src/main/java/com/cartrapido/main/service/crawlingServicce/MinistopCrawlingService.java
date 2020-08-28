package com.cartrapido.main.service.crawlingServicce;

import com.cartrapido.main.domain.repository.ProductRepository;
import com.cartrapido.main.web.dto.ProductDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MinistopCrawlingService {

    @Autowired
    private ProductRepository productRepository;

    private String store = "ministop";

    //WebDriver
    private WebDriver driver;
    private WebElement element;

    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:/drivers/chromedriver.exe";
    //크롤링 할 URL
    private String base_url;

    //@PostConstruct
    public void getGSDatas() {

        //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        //Driver SetUp
        driver = new ChromeDriver(options);
        lunchBoxCrawling();

    }

    public void lunchBoxCrawling() {
        try {

            driver.get("https://www.ministop.co.kr/");
            Thread.sleep(3000);

            element = driver.findElement(By.xpath("//*[@id=\"menu4\"]"));
            element.click();
            Thread.sleep(3000);

            String html = driver.getPageSource();

            Document doc = Jsoup.parse(html);
            System.out.println(doc);

//            Elements elements = doc.select("ul.prod_list");
//
//            int i;
//            for (i = 0; i < elements.select("li").size(); i++) {
//                System.out.println(i);
//
//                String productName = elements.select("p.tit").get(i).text();
//                System.out.println(productName);
//
//                String stringProductPrice = elements.select("span.cost").get(i).text();
//                String[] productPriceSplit = stringProductPrice.split(",");
//                String productPrice = "";
//                for (String str : productPriceSplit) {
//                    productPrice = productPrice + str;
//                }
//                productPrice = productPrice.replaceAll("원", "");
//
//                System.out.println(productPrice);
//
//                String image = elements.select("p.img img").get(i).attr("src");
//                System.out.println(image);
//
//                String itemId = "";
//
//                String productcontent = "";
//
//                ProductDTO productDTO = ProductDTO.builder()
//                        .itemId(itemId)
//                        .productName(productName)
//                        .productPrice(Integer.parseInt(productPrice))
//                        .productQty(10)
//                        .productContent(productcontent)
//                        .store(store)
//                        .category("LunchBox")
//                        .image(image)
//                        .build();
//
//                productRepository.save(productDTO.toEntity()).getProductId();
//            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }
    }

}
