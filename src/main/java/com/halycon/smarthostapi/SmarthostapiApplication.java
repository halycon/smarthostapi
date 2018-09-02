package com.halycon.smarthostapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class SmarthostapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmarthostapiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {

		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		messageConverter.setPrettyPrint(false);
		messageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON));
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		objectMapper.enable(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY);
		messageConverter.setObjectMapper(objectMapper);
		RestTemplate restTemplate = builder.build();

		restTemplate.getMessageConverters().removeIf(m -> m.getClass().getName().equals(MappingJackson2HttpMessageConverter.class.getName()));
		restTemplate.getMessageConverters().add(messageConverter);

		return restTemplate;

	}

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true);
    }
}
