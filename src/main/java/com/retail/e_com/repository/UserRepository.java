package com.retail.e_com.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retail.e_com.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	
	boolean existsByEmail(String email);
	Optional<User> findByEmail(String username);

//	Optional<User> findByUserName(String username);
	Optional<User> findByUsername(String username);
}
