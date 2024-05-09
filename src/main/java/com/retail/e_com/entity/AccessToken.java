package com.retail.e_com.entity;


import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="AccessToken")

@Setter
@Getter
public class AccessToken {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int tokenId; 
private String token;
private LocalDateTime expiration;
private boolean isBlocked;
@ManyToOne
private User user;


}
