package com.retail.e_com.entity;

import java.util.List;

import com.retail.e_com.enums.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int userId;
	private String username;
	private String displayName;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	private boolean isEmailVerified;
	private boolean isDeleted;
	
    @OneToMany
	private List<AccessToken> accessToken;
    
    @OneToMany
	private List<RefreshToken> refreshToken;
}
