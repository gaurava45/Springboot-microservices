package com.example.stock.stockservice.resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.stock.stockservice.feign.client.StockClient;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@RestController
@RequestMapping("/rest/stock")
public class StockResource {

    //@Autowired
    //RestTemplate restTemplate;
    
    @Autowired
    StockClient stockClient;

    @GetMapping("/{username}")
    public List<Quote> getStock(@PathVariable("username") final String userName) {

		
		/*
		 * ResponseEntity<List<String>> quoteResponse =
		 * restTemplate.exchange("http://db-service/rest/db/" + userName,
		 * HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() { });
		 * 
		 * 
		 * List<String> quotes = quoteResponse.getBody();
		 */
		 
    	
    	List<String> quotes = stockClient.getQuotes(userName);
    	
        return quotes
                .stream()
                .map(quote -> {
                    Stock stock = getStockPrice(quote);
                    return new Quote(quote, stock.getQuote().getPrice());
                })
                .collect(Collectors.toList());
    }

    private Stock getStockPrice(String quote) {
        try {
            return YahooFinance.get(quote);
        } catch (IOException e) {
            e.printStackTrace();
            return new Stock(quote);
        }
    }

    private class Quote {
        private String quote;
        private BigDecimal price;

        public Quote(String quote, BigDecimal price) {
            this.quote = quote;
            this.price = price;
        }

        public String getQuote() {
            return quote;
        }

        public void setQuote(String quote) {
            this.quote = quote;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }
}
