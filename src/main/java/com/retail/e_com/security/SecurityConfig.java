package com.retail.e_com.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.retail.e_com.jwt.JWTFilter;
import com.retail.e_com.jwt.JWTService;
import com.retail.e_com.jwt.RefreshFilter;
import com.retail.e_com.repository.AccessRepository;
import com.retail.e_com.repository.RefreshRepository;
import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
	//private CustomUserDetailsService customUserDetailsService;

	//******************-----------Interface injection-------------------*****************//
	private CustomUserDetailsService customUserDetailsService;
	private JWTFilter jwtFilter;
	private AccessRepository accessTokenRepository;
	private RefreshRepository refreshTokenRepository;
	private JWTService jwtService;

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(customUserDetailsService);
		return provider;
	}

//	@Bean
//	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		/**
//		 * csrf-> Cross-site Request Forgery
//		 * we are authorize the specified url using requestMatchers if the url match the permitAll
//		 * if request url is not specified url the users should be authenticated.
//		 * 
//		 */
//		return http.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(auth -> auth.requestMatchers("/**")
//						.permitAll()
//						.anyRequest()
//						.authenticated())
//				.sessionManagement(management->{management
//								.sessionCreationPolicy(SessionCreationPolicy.STATELESS);})
//				.authenticationProvider(authenticationProvider())
//				.addFilterBefore(new JWTFilter(accessTokenRepository, refreshTokenRepository, jwtService), UsernamePasswordAuthenticationFilter.class)
//				.build();
//
//	}
	@Bean
	@Order(1)
	SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
		/**
		 * csrf-> Cross-site Request Forgery we are authorize the specified url using
		 * requestMatchers if the url match the permitAll if request url is not
		 * specified url the users should be authenticated.
		 * 
		 */
		return http.csrf(csrf -> csrf.disable())
				.securityMatchers(
						matcher -> matcher.requestMatchers("/api/v1/login/**", "/api/v1/verify-email/**", "/api/v1/register/**"))
				.sessionManagement(management -> {
					management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				})
				.build();

	}

	@Bean
	@Order(2)
	SecurityFilterChain refreshFilterChain(HttpSecurity http) throws Exception {
		/**
		 * csrf-> Cross-site Request Forgery we are authorize the specified url using
		 * requestMatchers if the url match the permitAll if request url is not
		 * specified url the users should be authenticated.
		 * 
		 */
		return http.csrf(csrf -> csrf.disable())
				.securityMatchers(matcher -> matcher.requestMatchers("/api/v1/refreshlogin/**"))
				.authorizeHttpRequests(auth -> auth.anyRequest().authenticated()).sessionManagement(management -> {
					management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				}).authenticationProvider(authenticationProvider())
				.addFilterBefore(new RefreshFilter(refreshTokenRepository, jwtService),
						UsernamePasswordAuthenticationFilter.class)
				.build();

	}

	@Bean
	@Order(3)
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		/**
		 * csrf-> Cross-site Request Forgery we are authorize the specified url using
		 * requestMatchers if the url match the permitAll if request url is not
		 * specified url the users should be authenticated.
		 * 
		 */
		return http.csrf(csrf -> csrf.disable()).securityMatchers(matcher -> matcher.requestMatchers("/**"))
				.authorizeHttpRequests(auth -> {
					auth.anyRequest().authenticated();
				}).sessionManagement(management -> {
					management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				}).authenticationProvider(authenticationProvider())
				.addFilterBefore(new JWTFilter(accessTokenRepository, refreshTokenRepository, jwtService),
						UsernamePasswordAuthenticationFilter.class)
				.build();

	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();

	}
}
