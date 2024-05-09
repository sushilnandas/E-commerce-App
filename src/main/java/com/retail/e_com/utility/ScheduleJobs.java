package com.retail.e_com.utility;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.retail.e_com.entity.AccessToken;
import com.retail.e_com.entity.RefreshToken;
import com.retail.e_com.repository.AccessRepository;
import com.retail.e_com.repository.RefreshRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduleJobs {
	
	
	private AccessRepository accessTokenRepository;
	private RefreshRepository refreshTokenRepository;
	
	
	
	@Scheduled(fixedDelay = 3600000)
	public void removeExpiredAccessToken()
	{
		List<AccessToken> accessToekns= accessTokenRepository.findAllByExpirationLessThan(LocalDateTime.now());
		System.out.println(accessToekns);
		accessTokenRepository.deleteAll(accessToekns);
	}
	@Scheduled(fixedDelay = 1296000000)
	public void removeExpiredRefreshToken()
	{
		List<RefreshToken> refreshTokens= refreshTokenRepository.findAllByExpirationLessThan(LocalDateTime.now());
		System.out.println(refreshTokens);
		refreshTokenRepository.deleteAll(refreshTokens);
	}

}
