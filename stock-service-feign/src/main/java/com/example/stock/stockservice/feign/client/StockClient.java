package com.example.stock.stockservice.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "db-service")
public interface StockClient {
	
	@GetMapping("/rest/db/{username}")
	public List<String> getQuotes(@PathVariable("username") final String userName);

}

