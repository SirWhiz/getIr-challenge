package com.getir.readingisgood.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.repository.CustomerRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	CustomerRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		Customer user = userRepository.findByName(name)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with name: " + name));

		return UserDetailsImpl.build(user);
	}

}
