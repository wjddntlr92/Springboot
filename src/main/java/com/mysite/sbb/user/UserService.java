package com.mysite.sbb.user;


import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService 
{
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username, String email, String password)
	{
	SiteUser user = new SiteUser();
	user.setUsername(username);			
	user.setPassword(passwordEncoder.encode(password));
	user.setEmail(email);
	this.userRepository.save(user);
	
	return user;
	}
	
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
		
		if(siteUser.isPresent()) {
			return siteUser.get();
		}
		else {
			throw new DataNotFoundException("siteuser not found");
		}
	}
}
	
