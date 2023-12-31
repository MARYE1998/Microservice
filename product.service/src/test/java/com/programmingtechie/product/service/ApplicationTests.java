package com.programmingtechie.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmingtechie.product.service.dto.ProductRequest;
import com.programmingtechie.product.service.model.Product;
import com.programmingtechie.product.service.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ApplicationTests {

@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
@Autowired
private MockMvc mockMvc;
@Autowired
private ObjectMapper objectMapper;
@Autowired
private ProductRepository productRepository;
@DynamicPropertySource
static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry)
{
dynamicPropertyRegistry.add("spring.data.mongodb.uri" , mongoDBContainer::getReplicaSetUrl);


}
	@Test
	void shouldCreateProduct() throws Exception {

	ProductRequest productRequest =  getProductRequest();
		String productResquestString = objectMapper.writeValueAsString(productRequest);

	mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
			.contentType(MediaType.APPLICATION_JSON)
			.content(productResquestString))
			.andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.findAll().size());



	}

	private ProductRequest getProductRequest() {
	return ProductRequest.builder()
			.name("iphone 13")
			.description("iphone 13")
			.price(BigDecimal.valueOf(1200))
			.build();
	}

}
