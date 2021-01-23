package com.circuitebreak.order;

import com.circuitebreak.order.model.OrderRequest;
import com.circuitebreak.order.model.OrderResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.stream.IntStream;

@SpringBootApplication
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
		OrderRequest request = OrderRequest.builder()
				.productCode("P002")
				.productName("Rose")
				.build();
		IntStream.rangeClosed(1, 10).parallel().forEach(i -> {
			ResponseEntity<OrderResponse> responseEntity = new RestTemplate()
					.exchange(new RequestEntity(request, HttpMethod.POST, URI.create("http://localhost:9092/orders")), OrderResponse.class);
		});
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
