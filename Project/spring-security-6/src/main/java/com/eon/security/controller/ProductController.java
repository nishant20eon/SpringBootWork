package com.eon.security.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")

//http://localhost:8080/product/
public class ProductController {
	

	private record Product(Integer productId, String productName, double price) {
		
	}
	
	List<Product> products = new ArrayList<>(
			List.of(new Product(1,"Iphone",25000.00),
					new Product(2,"MacBook",250000.00))
			);
	
	@GetMapping("/getPro")
	public List<Product> getProducts() {
		return products;
	}
	@PostMapping("/addProd")
	public Product saveProduct(@RequestBody Product product) {
		products.add(product);
		return product;
	}
	
}
