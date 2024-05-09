package com.retail.e_com.serviceImpl;

import java.time.Duration;
import java.util.Date;
import java.util.Random;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.retail.e_com.authService.AuthService;
import com.retail.e_com.cache.CacheStore;
import com.retail.e_com.entity.AccessToken;
import com.retail.e_com.entity.Customer;
import com.retail.e_com.entity.RefreshToken;
import com.retail.e_com.entity.Seller;
import com.retail.e_com.entity.User;
import com.retail.e_com.enums.UserRole;
import com.retail.e_com.exception.InvalidCridentialException;
import com.retail.e_com.exception.InvalidOTPException;
import com.retail.e_com.exception.InvalidUserEmailException;
import com.retail.e_com.exception.InvalidUserRoleException;
import com.retail.e_com.exception.RegistrationSessionExpiredException;
import com.retail.e_com.exception.UserAlreadyExistEmailException;
import com.retail.e_com.jwt.JWTService;
import com.retail.e_com.mailservice.MailService;
import com.retail.e_com.repository.AccessRepository;
import com.retail.e_com.repository.RefreshRepository;
import com.retail.e_com.repository.UserRepository;
import com.retail.e_com.requestdto.AddressRequest;
import com.retail.e_com.requestdto.AuthRequest;
import com.retail.e_com.requestdto.OTPRequest;
import com.retail.e_com.requestdto.UserRequest;
import com.retail.e_com.responsedto.AddressResponse;
import com.retail.e_com.responsedto.AuthResponseDto;
import com.retail.e_com.responsedto.UserResponse;
import com.retail.e_com.security.SecurityConfig;
import com.retail.e_com.utility.MessageModel;
import com.retail.e_com.utility.ResponseStructure;
import com.retail.e_com.utility.SimpleResponseStructure;
import com.retail.e_com.exception.UserAlreadyLogoutException;
import com.retail.e_com.exception.UserAlreadyLoginException;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Service
public class AuthServiceImpl implements AuthService{
	private UserRepository userRepository;
	private CacheStore<String> otpCache;
	private CacheStore<User> userCache;
	private ResponseStructure<UserResponse> responseStructure;
	private SimpleResponseStructure simpleResponseStructure2;
	private MailService mailService;
	private AuthenticationManager authenticationManager;
	private JWTService jwtService;
	private AccessRepository accessTokenRepo;
	private  RefreshRepository refreshTokenRepo;
   private PasswordEncoder passwordEncoder;
   
 
	public AuthServiceImpl(UserRepository userRepository, CacheStore<String> otpCache, CacheStore<User> userCache,
		ResponseStructure<UserResponse> responseStructure, SimpleResponseStructure simpleResponseStructure2,
		MailService mailService, AuthenticationManager authenticationManager, JWTService jwtService,
		AccessRepository accessTokenRepo, RefreshRepository refreshTokenRepo, PasswordEncoder passwordEncoder) {
	super();
	this.userRepository = userRepository;
	this.otpCache = otpCache;
	this.userCache = userCache;
	this.responseStructure = responseStructure;
	this.simpleResponseStructure2 = simpleResponseStructure2;
	this.mailService = mailService;
	this.authenticationManager = authenticationManager;
	this.jwtService = jwtService;
	this.accessTokenRepo = accessTokenRepo;
	this.refreshTokenRepo = refreshTokenRepo;
	this.passwordEncoder = passwordEncoder;
}

	@Value("${myapp.jwt.access.expiration}")
	private long accessExpiration;


	@Value("${myapp.jwt.refresh.expiration}")
	private long refreshExpiration;

	@SuppressWarnings("unchecked")
	private <T extends User> T mapToChildEntity(UserRequest userRequestEntity) {
		UserRole  userRole=userRequestEntity.getUserRole();
		User user;
		switch (userRole) {
		case SELLER ->	user=new Seller();
		case CUSTOMER-> user=new Customer();
		default-> throw new InvalidUserRoleException("User Role Not Specified");		
		}


		user.setDisplayName(userRequestEntity.getDisplayName());
		user.setEmail(userRequestEntity.getEmail());
		user.setPassword(userRequestEntity.getPassword());
		user.setUsername(userRequestEntity.getEmail().split("@gmail.com")[0]);
		user.setUserRole(userRequestEntity.getUserRole());
		user.setDeleted(false);
		user.setEmailVerified(false);
		return (T)user;
	}

	private UserResponse mapToUserResponse(User user) {
		return UserResponse.builder()
				.displayName(user.getDisplayName())
				.email(user.getEmail())
				.userId(user.getUserId())
				.username(user.getUsername())
				.userRole(user.getUserRole())
				.isEmailVerified(user.isEmailVerified())
				.build();

	}	
	private String generateOTP() {
		return String.valueOf(new Random().nextInt(100000, 999999));
	}
	private void sendOTP(User user, String otp) throws MessagingException {
		MessageModel model = MessageModel.builder()
				.to(user.getEmail())
				.subject("OTP Verification")
				.text(
						"<p> Hi, <br>"
								+ "Thanks for your intrest in E-com,"
								+ "Please Verify your mail Id using the OTP Given below.</p>"
								+ "<br>"
								+ "<h1>"+otp+"</h1>"
								+ "<br>"
								+ "Please ignore if its not you"
								+ "<br>"
								+ "with best regards"
								+ "<h3>E-Com-Service</h3>"
								+ "<img src='https://entrackr.com/storage/2020/03/flipkart-grocery-image.jpg'/>"			
						).build();

		mailService.sendMailMessage(model);
	}

	@Override
	public ResponseEntity<SimpleResponseStructure> registerUsers (UserRequest userRequest) {

		if(userRepository.existsByEmail(userRequest.getEmail()))
			throw new UserAlreadyExistEmailException("user already exist");

		User user=mapToChildEntity(userRequest);
		String otp=generateOTP();
		otpCache.add(userRequest.getEmail(), otp);
		userCache.add(userRequest.getEmail(), user);
		try {
			sendOTP(user,otp);
		} catch (MessagingException e) {
			throw new  InvalidUserEmailException("Invalid Email");			
		}

		return ResponseEntity.ok(simpleResponseStructure2.setStatus(HttpStatus.ACCEPTED.value()).setMessage("verify the mail to complite the registration ,"+otp+" OTP  expaires in 1 minute"));

	}
	@Override
	public ResponseEntity<ResponseStructure<UserResponse>> verifyOTP(OTPRequest otpRequest) {

		if (otpCache.get(otpRequest.getEmail()) == null)
			throw new InvalidOTPException("OTP Expired");
		if (!otpCache.get(otpRequest.getEmail()).equals(otpRequest.getOtp()))
			throw new InvalidOTPException("OTP Invalid");

		User user = userCache.get(otpRequest.getEmail());
		if (user == null)
			throw new RegistrationSessionExpiredException("Session Expired ");
		user.setEmailVerified(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));;
       
		return ResponseEntity.status(HttpStatus.CREATED).body(responseStructure.setData(mapToUserResponse(userRepository.save(user)))
				.setMessgae("successful")
				.setStatus(HttpStatus.CREATED.value()));

	}


	private AuthResponseDto mapToauthResponse(User user) {
		return AuthResponseDto.builder().userId(user.getUserId()).username(user.getUsername())
				.userRole(user.getUserRole()).build();
	}






	@Override
	public ResponseEntity<ResponseStructure<AuthResponseDto>> userLogin(AuthRequest authRequest) {
		System.out.println(authRequest.getUsername());
		String userName= authRequest.getUsername().split("gmail.com")[0];
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken( userName,authRequest.getPassword()));
		if(!authentication.isAuthenticated()) throw new InvalidCridentialException("Credential not matching");
		SecurityContextHolder.getContext().setAuthentication(authentication);

		//generate access and refresh tokens
		HttpHeaders headers = new HttpHeaders();
		
		User user2 = userRepository.findByUsername(userName).map(user->{
			generateAccessToken(user,headers);
			generateRefreshToken(user,headers);
			return user;
		}).get();
		return ResponseEntity.ok()
				.headers(headers)
				.body(new ResponseStructure<AuthResponseDto>().setData(mapToauthResponse(user2)).setMessgae("Authentication Successful").setStatus(HttpStatus.OK.value()));
	}

	private void generateRefreshToken(User user, HttpHeaders headers) {
		String token = jwtService.generateRefreshToken(user.getUsername(),user.getUserRole().name());
		headers.add(HttpHeaders.SET_COOKIE, configureCookie("rt", token, refreshExpiration));
		// store the token to the database
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(token);
		refreshToken.setBlocked(false);
		refreshTokenRepo.save(refreshToken);

	}


	private String configureCookie(String name,String value,long maxAge) {
		return ResponseCookie.from(name, value)
				.domain("localhost")
				.path("/")
				.httpOnly(true)
				.secure(false)
				.maxAge(Duration.ofMillis(maxAge))
				.sameSite("Lax")
				.build().toString();
	}







	private void generateAccessToken(User user, HttpHeaders headers) {
		String token = jwtService.generateAccessToken(user.getUsername(),user.getUserRole().name());
		headers.add(HttpHeaders.SET_COOKIE, configureCookie("at", token, accessExpiration));
		AccessToken accessToken = new AccessToken();
		accessToken.setToken(token);
		accessToken.setBlocked(false);
		accessTokenRepo.save(accessToken);


	}
	
        
		@Override
		public ResponseEntity<SimpleResponseStructure> userLogout(String refreshToken, String accessToken) {

			if(accessToken==null&& refreshToken==null)
				throw new UserAlreadyLoginException("already login");
		 refreshTokenRepo.findByToken(refreshToken).ifPresent(rt->{
			 rt.setBlocked(true);
				System.out.println(rt);
				refreshTokenRepo.save(rt);
		 });
			
			
			accessTokenRepo.findByToken(accessToken).ifPresent(at->{
				
				at.setBlocked(true);
				System.out.println(at);
				accessTokenRepo.save(at);
				
			});
		
			
			HttpHeaders headers=new HttpHeaders();
			headers.add(HttpHeaders.SET_COOKIE, invalidCookie("at"));
			headers.add(HttpHeaders.SET_COOKIE, invalidCookie("rt"));
			
			return ResponseEntity.ok()
					.headers(headers)
					.body(simpleResponseStructure2.setStatus(HttpStatus.OK.value()).setMessage("Log out "));
		}

		
		
		private String invalidCookie(String name) {
			return ResponseCookie.from(name,"")
					.domain("localhost")
					.path("/")
					.httpOnly(true)
					.secure(false)
					.maxAge(0)
					.sameSite("Lax").build().toString();
		}
		@Override
		public ResponseEntity<ResponseStructure<AuthResponseDto>> refreshLogin(String accessToken,
				String refreshToken) {
			
			if(accessToken!=null)
			{
				accessTokenRepo.findByToken(accessToken).ifPresent(at->{
					at.setBlocked(true);
					accessTokenRepo.save(at);
				});	
			}
			if(refreshToken==null)
				throw new UserAlreadyLogoutException("require Login");
			
			Date date=jwtService.getIssueDate(refreshToken);
			String username=jwtService.getUserName(refreshToken);
			HttpHeaders headers=new HttpHeaders();
			return  userRepository.findByUsername(username).map(user->
				{
						if(date.before(new Date()))
						{
							generateRefreshToken(user, headers);
							refreshTokenRepo.findByToken(refreshToken).ifPresent(rt->{
								rt.setBlocked(true);
								refreshTokenRepo.save(rt);
							});
						}
						else
							headers.add(HttpHeaders.SET_COOKIE, configureCookie("rt",refreshToken,refreshExpiration));
						
						generateAccessToken(user, headers);
				 return ResponseEntity.ok().headers(headers).body(new ResponseStructure<AuthResponseDto>()
																	.setStatus(HttpStatus.OK.value())
																	.setMessgae("Refresh Successfull")
																	.setData(mapToauthResponse(user)));
			}).get();	
			
			
			
		}

		
	
	}