package com.retail.e_com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "cartProduct")
@Setter
@Getter
@AllArgsConstructor
public class CartProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private int cartProductId;
private int selectedQuantity;

@ManyToOne
private Product product;
}
