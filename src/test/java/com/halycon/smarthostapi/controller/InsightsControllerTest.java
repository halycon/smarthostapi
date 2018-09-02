package com.halycon.smarthostapi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableWebMvc
public class InsightsControllerTest {

    @Autowired
    public RestTemplate restTemplate;

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
}
