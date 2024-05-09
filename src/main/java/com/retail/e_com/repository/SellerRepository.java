package com.retail.e_com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retail.e_com.entity.Seller;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {

}
