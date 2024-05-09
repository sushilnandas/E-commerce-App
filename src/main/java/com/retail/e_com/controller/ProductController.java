package com.retail.e_com.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.e_com.authService.ProductService;
import com.retail.e_com.entity.Product;
import com.retail.e_com.enums.Category;
import com.retail.e_com.repository.ProductRepository;
import com.retail.e_com.requestdto.ProductRequest;
import com.retail.e_com.requestdto.SearchFilter;
import com.retail.e_com.responsedto.ProductResponse;
import com.retail.e_com.utility.ProductSpecification;
import com.retail.e_com.utility.ResponseStructure;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "http://localhost:5173/", allowCredentials = "true")
@AllArgsConstructor
public class ProductController {

	
	private ProductService productService;

	@PreAuthorize("hasAuthority('SELLER')")
	@PostMapping("/products")
	public ResponseEntity<ResponseStructure<ProductResponse>> addProduct(@RequestBody ProductRequest productRequest) {
		return productService.addProducts(productRequest);
	}

	@PreAuthorize("hasAuthority('SELLER')")
	@PutMapping("/products/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(@RequestBody ProductRequest productRequest,
			@PathVariable int productId) {
		System.out.println(productId);
		return productService.updateProduct(productRequest, productId);
	}

	@GetMapping("/products/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> findProductById(@PathVariable int productId) {
		return productService.findProductById(productId);
	}

	
	@GetMapping("product/prodcuts")
	public List<Product> getMethodName(SearchFilter searchFilter,int page,String ase, String sortby) {
		
		return productService.findAll(searchFilter, page, ase, sortby);
	}
	
	@GetMapping("/products/category")
	public List<Category> findCategory(SearchFilter searchfilter){
		return Arrays.asList(Category.values());
		
	}
	

	
	
}
