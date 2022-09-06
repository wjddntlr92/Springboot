package com.mysite.sbb.cart;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.user.SiteUser;

public interface CartRepository extends JpaRepository<Cart, Long> {
	
	
	Optional<Cart> findBySiteUser(SiteUser siteUser);

}
