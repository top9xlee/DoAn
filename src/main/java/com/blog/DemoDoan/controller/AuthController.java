package com.blog.DemoDoan.controller;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.blog.DemoDoan.dto.RegisterRequest;
import com.blog.DemoDoan.service.AuthService;

import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
		authService.signup(registerRequest);
		return new ResponseEntity(OK);
	}
	@GetMapping("accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
		authService.verifyAccount(token);
		return new ResponseEntity<>("Account Activated Successully", OK);
	}
}