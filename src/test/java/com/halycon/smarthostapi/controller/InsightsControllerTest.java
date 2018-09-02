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
}
