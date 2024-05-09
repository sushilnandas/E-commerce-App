package com.retail.e_com.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retail.e_com.entity.AccessToken;
import com.retail.e_com.entity.User;

@Repository

public interface AccessRepository extends JpaRepository<AccessToken, Integer> {




	boolean existsByTokenAndIsBlocked(String token,boolean value);

	Optional<AccessToken> findByToken(String accessToken);

	List<AccessToken> findAllByExpirationLessThan(LocalDateTime now);
}
