package com.getir.readingisgood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.payload.LoginPayload;
import com.getir.readingisgood.payload.ResponseBean;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.security.jwt.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	CustomerRepository customerRepository;
	
	@PostMapping("/register")
	public ResponseBean saveCustomer(@RequestBody Customer newCustomer) {
		ResponseBean response = new ResponseBean();
		
		if (customerRepository.existsByName(newCustomer.getName())) {
			response.setError("Error: Username is already taken!");
			response.setStatus(HttpStatus.BAD_REQUEST.toString());
			return response;
		}
		
		if (customerRepository.existsByEmail(newCustomer.getEmail())) {
			response.setError("Error: Email is already in use!");
			response.setStatus(HttpStatus.BAD_REQUEST.toString());
			return response;
		}
	    
		try {
			Customer customerToSave = new Customer();
			customerToSave.setName(newCustomer.getName());
			customerToSave.setEmail(newCustomer.getEmail());
			customerToSave.setPassword(encoder.encode(newCustomer.getPassword()));
			
	    	customerRepository.save(customerToSave);
	    	response.setStatus(HttpStatus.CREATED.toString());
	    }catch (Exception e) {
	    	response.setData(null);
	    	response.setError(HttpStatus.INTERNAL_SERVER_ERROR.toString());
	    }
	    
	    return response;
	}
	
	@PostMapping("/login")
	public ResponseBean login(@RequestBody LoginPayload loginData) {
		ResponseBean response = new ResponseBean();
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginData.getName(), loginData.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		response.setData(jwt);
		response.setStatus(HttpStatus.OK.toString());
		
		return response;
	}
	
}
