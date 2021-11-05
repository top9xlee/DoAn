package com.blog.DemoDoan.service;

import com.blog.DemoDoan.exception.SpringException;
import com.blog.DemoDoan.model.NotificationEmail;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.blog.DemoDoan.dto.RegisterRequest;
import com.blog.DemoDoan.model.User;
import com.blog.DemoDoan.model.VerificationToken;
import com.blog.DemoDoan.repository.UserRepository;
import com.blog.DemoDoan.repository.VerificationTokenRepository;

import lombok.AllArgsConstructor;


import java.util.Optional;
import java.util.UUID;

import static com.blog.DemoDoan.util.Constants.ACTIVATION_EMAIL;
import static java.time.Instant.now;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final VerificationTokenRepository verificationTokenRepository;

	private final MailContentBuilder mailContentBuilder;

	private final MailService mailService;

	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(encodePassword(registerRequest.getPassword()));
		user.setCreated(now());
		user.setEnabled(false);

		userRepository.save(user);

		String token = generateVerificationToken(user);
		String message = mailContentBuilder.build("Thank you for signing up to Spring Reddit, please click on the below url to activate your account : "
				+ ACTIVATION_EMAIL + "/" + token);

		mailService.sendMail(new NotificationEmail("Please Activate your account", user.getEmail(), message));
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);
		return token;
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
		verificationTokenOptional.orElseThrow(() -> new SpringException("Invalid Token"));
		fetchUserAndEnable(verificationTokenOptional.get());
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringException("User Not Found with id - " + username));
		user.setEnabled(true);
		userRepository.save(user);
	}
}
