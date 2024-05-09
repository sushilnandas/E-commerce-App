package com.retail.e_com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.retail.e_com.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>,JpaSpecificationExecutor<Product> {

	boolean existsByProductId(int productId);

}
