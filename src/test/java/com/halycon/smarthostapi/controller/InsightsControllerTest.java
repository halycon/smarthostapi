package com.halycon.smarthostapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.halycon.smarthostapi.domain.RoomStock;
import com.halycon.smarthostapi.domain.RoomType;
import com.halycon.smarthostapi.domain.api.OccupancyOptimizationRequest;
import com.halycon.smarthostapi.domain.api.OccupancyOptimizationResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWebMvc
public class InsightsControllerTest {

    @Autowired
    public RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private BigDecimal[] prices;

    @Before
    public void init() {

        String url = "https://gist.githubusercontent.com/fjahr/b164a446db285e393d8e4b36d17f4143/raw/0eb2e48bf4d1b8ae97631aa341a9c72489a90e43/smarthost_hotel_guests.json";
        ResponseEntity<String> responseHttpGet = restTemplate.exchange(url,
                HttpMethod.GET, null, String.class);
        try {
            final JsonNode jsonNode = objectMapper.readTree(responseHttpGet.getBody());
            prices = objectMapper.readerFor(BigDecimal[].class).readValue(jsonNode);
        }catch (Exception e){}

    }

    @Test
    public void getDemoPricesFromExternalApi_ReturnsPriceList() {
        Assert.assertNotNull("prices is not null", prices);
        Assert.assertNotEquals("prices not empty ", prices.length,0);
    }

    @Test
    public void getOptimizedOccupancySummary_E5P7_Returns189_1054() {
        OccupancyOptimizationRequest request = new OccupancyOptimizationRequest();
        List<RoomStock> roomStocks = new ArrayList<>();
        roomStocks.add(new RoomStock(RoomType.ECONOMY,BigDecimal.ZERO,5));
        roomStocks.add(new RoomStock(RoomType.PREMIUM,BigDecimal.valueOf(100),7));
        request.setRoomStockList(roomStocks);
        request.setPrices(prices);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ResponseEntity<OccupancyOptimizationResponse> response = testRestTemplate.postForEntity("/getOptimizedOccupancySummary",
                new HttpEntity<>(request, headers), OccupancyOptimizationResponse.class);

        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assert.assertTrue("api response true", response.getBody().isSuccess());
        Assert.assertNotNull("prices summaries not null", response.getBody().getPriceSummaries());
        Assert.assertEquals("economy total price equals 189", response.getBody().getPriceSummaries().get(0).getTotalPrice(), BigDecimal.valueOf(189));
        Assert.assertEquals("economy allocation 4", response.getBody().getPriceSummaries().get(0).getAllocation(),4);
        Assert.assertEquals("premium total price equals 1054", response.getBody().getPriceSummaries().get(1).getTotalPrice(), BigDecimal.valueOf(1054));
        Assert.assertEquals("premium allocation 6", response.getBody().getPriceSummaries().get(1).getAllocation(),6);
    }


    @Test
    public void getOptimizedOccupancySummary_E7P2_Returns189_583() {
        OccupancyOptimizationRequest request = new OccupancyOptimizationRequest();
        List<RoomStock> roomStocks = new ArrayList<>();
        roomStocks.add(new RoomStock(RoomType.ECONOMY,BigDecimal.ZERO,7));
        roomStocks.add(new RoomStock(RoomType.PREMIUM,BigDecimal.valueOf(100),2));
        request.setRoomStockList(roomStocks);
        request.setPrices(prices);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ResponseEntity<OccupancyOptimizationResponse> response = testRestTemplate.postForEntity("/getOptimizedOccupancySummary",
                new HttpEntity<>(request, headers), OccupancyOptimizationResponse.class);

        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assert.assertTrue("api response true", response.getBody().isSuccess());
        Assert.assertNotNull("prices summaries not null", response.getBody().getPriceSummaries());
        Assert.assertEquals("economy total price equals 189", response.getBody().getPriceSummaries().get(0).getTotalPrice(), BigDecimal.valueOf(189));
        Assert.assertEquals("economy allocation 4", response.getBody().getPriceSummaries().get(0).getAllocation(),4);
        Assert.assertEquals("premium total price equals 583", response.getBody().getPriceSummaries().get(1).getTotalPrice(), BigDecimal.valueOf(583));
        Assert.assertEquals("premium allocation 2", response.getBody().getPriceSummaries().get(1).getAllocation(),2);
    }


    @Test
    public void getOptimizedOccupancySummary_E3P3_Returns167_738() {
        OccupancyOptimizationRequest request = new OccupancyOptimizationRequest();
        List<RoomStock> roomStocks = new ArrayList<>();
        roomStocks.add(new RoomStock(RoomType.ECONOMY,BigDecimal.ZERO,3));
        roomStocks.add(new RoomStock(RoomType.PREMIUM,BigDecimal.valueOf(100),3));
        request.setRoomStockList(roomStocks);
        request.setPrices(prices);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ResponseEntity<OccupancyOptimizationResponse> response = testRestTemplate.postForEntity("/getOptimizedOccupancySummary",
                new HttpEntity<>(request, headers), OccupancyOptimizationResponse.class);

        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assert.assertTrue("api response true", response.getBody().isSuccess());
        Assert.assertNotNull("prices summaries not null", response.getBody().getPriceSummaries());
        Assert.assertEquals("economy total price equals 167", response.getBody().getPriceSummaries().get(0).getTotalPrice(), BigDecimal.valueOf(167));
        Assert.assertEquals("economy allocation 3", response.getBody().getPriceSummaries().get(0).getAllocation(),3);
        Assert.assertEquals("premium total price equals 738", response.getBody().getPriceSummaries().get(1).getTotalPrice(), BigDecimal.valueOf(738));
        Assert.assertEquals("premium allocation 3", response.getBody().getPriceSummaries().get(1).getAllocation(),3);
    }

    @Test
    public void getOptimizedOccupancySummary_E3P7_Returns189_1054() {
        OccupancyOptimizationRequest request = new OccupancyOptimizationRequest();
        List<RoomStock> roomStocks = new ArrayList<>();
        roomStocks.add(new RoomStock(RoomType.ECONOMY,BigDecimal.ZERO,3));
        roomStocks.add(new RoomStock(RoomType.PREMIUM,BigDecimal.valueOf(100),7));
        request.setRoomStockList(roomStocks);
        request.setPrices(prices);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ResponseEntity<OccupancyOptimizationResponse> response = testRestTemplate.postForEntity("/getOptimizedOccupancySummary",
                new HttpEntity<>(request, headers), OccupancyOptimizationResponse.class);

        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assert.assertTrue("api response true", response.getBody().isSuccess());
        Assert.assertNotNull("prices summaries not null", response.getBody().getPriceSummaries());
        Assert.assertEquals("economy total price equals 189", response.getBody().getPriceSummaries().get(0).getTotalPrice(), BigDecimal.valueOf(189));
        Assert.assertEquals("economy allocation 4", response.getBody().getPriceSummaries().get(0).getAllocation(),4);
        Assert.assertEquals("premium total price equals 1054", response.getBody().getPriceSummaries().get(1).getTotalPrice(), BigDecimal.valueOf(1054));
        Assert.assertEquals("premium allocation 6", response.getBody().getPriceSummaries().get(1).getAllocation(),6);
    }

    @Test
    public void getOptimizedOccupancySummary_E2P7K1_Returns189_680_374() {
        OccupancyOptimizationRequest request = new OccupancyOptimizationRequest();
        List<RoomStock> roomStocks = new ArrayList<>();
        roomStocks.add(new RoomStock(RoomType.ECONOMY,BigDecimal.ZERO,2));
        roomStocks.add(new RoomStock(RoomType.PREMIUM,BigDecimal.valueOf(100),7));
        roomStocks.add(new RoomStock(RoomType.KINGSUITE,BigDecimal.valueOf(300),1));
        request.setRoomStockList(roomStocks);
        request.setPrices(prices);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        ResponseEntity<OccupancyOptimizationResponse> response = testRestTemplate.postForEntity("/getOptimizedOccupancySummary",
                new HttpEntity<>(request, headers), OccupancyOptimizationResponse.class);

        Assert.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assert.assertTrue("api response true", response.getBody().isSuccess());
        Assert.assertNotNull("prices summaries not null", response.getBody().getPriceSummaries());
        Assert.assertEquals("economy total price equals 189", response.getBody().getPriceSummaries().get(0).getTotalPrice(), BigDecimal.valueOf(189));
        Assert.assertEquals("economy allocation 4", response.getBody().getPriceSummaries().get(0).getAllocation(),4);
        Assert.assertEquals("premium total price equals 680", response.getBody().getPriceSummaries().get(1).getTotalPrice(), BigDecimal.valueOf(680));
        Assert.assertEquals("premium allocation 5", response.getBody().getPriceSummaries().get(1).getAllocation(),5);
        Assert.assertEquals("king total price equals 374", response.getBody().getPriceSummaries().get(2).getTotalPrice(), BigDecimal.valueOf(374));
        Assert.assertEquals("king allocation 1", response.getBody().getPriceSummaries().get(2).getAllocation(),1);
    }

}
