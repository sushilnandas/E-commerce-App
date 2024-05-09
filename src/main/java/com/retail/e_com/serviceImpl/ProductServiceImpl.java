package com.retail.e_com.serviceImpl;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.retail.e_com.authService.ProductService;
import com.retail.e_com.entity.Product;
import com.retail.e_com.entity.Seller;
import com.retail.e_com.enums.AvailabiltyStatus;
import com.retail.e_com.exception.ProductNotFoundByIdException;
import com.retail.e_com.exception.UserNotFoundByUsernameException;
import com.retail.e_com.repository.ProductRepository;
import com.retail.e_com.repository.SellerRepository;
import com.retail.e_com.repository.UserRepository;
import com.retail.e_com.requestdto.ProductRequest;
import com.retail.e_com.requestdto.SearchFilter;
import com.retail.e_com.responsedto.ProductResponse;
import com.retail.e_com.utility.ProductSpecification;
import com.retail.e_com.utility.ResponseStructure;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
	private ProductRepository productRepository;
	private UserRepository userRepository;
	private SellerRepository sellerRepository;


	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> addProducts(ProductRequest productRequest) {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();

		return userRepository.findByUsername(name).map(user -> {

			Product product = productRepository.save(mapToProduct(new Product(), productRequest));
			Seller seller = ((Seller) user);
			seller.getProducts().add(product);
			Seller save = sellerRepository.save(seller);
			return ResponseEntity.ok()
					.body(new ResponseStructure<ProductResponse>().setData(mapToProductResponse(product))
							.setMessgae("product added").setStatus(HttpStatus.OK.value()));
		}).orElseThrow(()-> new UserNotFoundByUsernameException("user not found by username"));
	}

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(ProductRequest productRequest,int productId) {

		return productRepository.findById(productId).map(product->{
			Product product2 = productRepository.save(mapToProduct(product, productRequest));
			return ResponseEntity.ok()
					.body(new ResponseStructure<ProductResponse>().setData(mapToProductResponse(product2))
							.setMessgae("product updated").setStatus(HttpStatus.OK.value()));

		}).get();

	}

	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> findProductById(int productId) {
		return productRepository.findById(productId).map(product->{
			return ResponseEntity.ok()
					.body(new ResponseStructure<ProductResponse>().setData(mapToProductResponse(product))
							.setMessgae("product updated").setStatus(HttpStatus.OK.value()));
		}).orElseThrow(()->new ProductNotFoundByIdException("product with this id not present"));
	}



	private Product mapToProduct(Product product, ProductRequest productRequest) {

		product.setProductName(productRequest.getProductName());
		product.setCategory(productRequest.getCategory());
		product.setProductDescription(productRequest.getProductDescription());
		product.setProductPrice(productRequest.getProductPrice());
		product.setQuantity(productRequest.getQuantity());
		if(productRequest.getQuantity()>=10)
			product.setAvailabiltyStatus(AvailabiltyStatus.AVAILABLE);
		else if(productRequest.getQuantity()<10 &&productRequest.getQuantity()>0)
			product.setAvailabiltyStatus(AvailabiltyStatus.FEWAVAILABLE);
		else
			product.setAvailabiltyStatus(AvailabiltyStatus.NOTAVAIlABLE);

		return product;
	}

	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.productId(product.getProductId())
				.productName(product.getProductName())
				.productPrice(product.getProductPrice())
				.quantity(product.getQuantity())
				.category(product.getCategory())
				.productDescription(product.getProductDescription())
				.availabiltyStatus(product.getAvailabiltyStatus())
				.build();
	}

	@Override
	public List<Product> findAll(SearchFilter searchFilter,int page,String asc, String sortBy) {

		Pageable pageable = (Pageable)PageRequest.of(page, 10, Sort.by(Direction.ASC, sortBy));

		Page<Product> page1 = productRepository.findAll(new ProductSpecification(searchFilter).buildSpecification(),pageable);

		List<Product> products = page1.getContent();

		return products;
	}

}

