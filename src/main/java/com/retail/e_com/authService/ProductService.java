package com.retail.e_com.authService;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.retail.e_com.entity.Product;
import com.retail.e_com.requestdto.ProductRequest;
import com.retail.e_com.requestdto.SearchFilter;
import com.retail.e_com.responsedto.ProductResponse;
import com.retail.e_com.utility.ProductSpecification;
import com.retail.e_com.utility.ResponseStructure;



public interface ProductService {

	ResponseEntity<ResponseStructure<ProductResponse>> addProducts(ProductRequest productRequest);
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(ProductRequest productRequest,int productId);
	public ResponseEntity<ResponseStructure<ProductResponse>> findProductById(int productId);
	public List<Product> findAll(SearchFilter searchFilter, int page, String asc, String sortBy);
}
