package com.mysite.sbb.cartItem;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.sbb.cart.Cart;
import com.mysite.sbb.item.SiteItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	
	List<CartItem> findBySiteItem(SiteItem siteItem);
	
	Optional<CartItem> findBySiteItemAndCart(SiteItem siteItem,Cart cart);

}
