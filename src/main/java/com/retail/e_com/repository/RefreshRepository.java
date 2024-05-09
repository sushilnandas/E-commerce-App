package com.retail.e_com.repository;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retail.e_com.entity.RefreshToken;
import com.retail.e_com.entity.User;


@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken, Integer>{
	
	boolean existsByTokenAndIsBlocked(String token,boolean value);

	Optional<RefreshToken> findByToken(String refreshToken);

	List<RefreshToken> findAllByExpirationLessThan(LocalDateTime now);

}
