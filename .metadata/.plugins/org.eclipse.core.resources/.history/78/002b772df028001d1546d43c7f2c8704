package com.mysite.sbb.cartItem;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.cart.Cart;
import com.mysite.sbb.cart.CartRepository;
import com.mysite.sbb.item.SiteItem;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartItemService {
	
		
	private final CartItemRepository cartItemRepository;
	
	
	public void createCartItem(Cart cart, SiteItem item, int amount ) {
		
		CartItem cartItem = new CartItem();
		cartItem.setSiteItem(item);
		cartItem.setAmount(amount);
		cartItem.setCart(cart);
		this.cartItemRepository.save(cartItem);
	}
	
	public Optional<CartItem> getCartItemById(Long id){
		
		return this.cartItemRepository.findById(id);
	}
	
	public Optional<CartItem> getPersonCartItem(SiteItem item, Cart cart){
		Optional<CartItem> cartItem = this.cartItemRepository.findBySiteItemAndCart(item, cart);
		return cartItem;
	}
	
	public List<CartItem> getAllCartItem(SiteItem item){
		List<CartItem> cartItem = this.cartItemRepository.findBySiteItem(item);
		return cartItem;
	}
		
	
	public void deletePersonCartItem(SiteItem item, Cart cart) {
		Optional<CartItem> cartItem = this.cartItemRepository.findBySiteItemAndCart(item, cart);
		if(cartItem.isPresent()) {
			this.cartItemRepository.delete(null);
		}
	}
	
	public void deleteAllCartItem(SiteItem item){
		List<CartItem> cartItems = this.cartItemRepository.findBySiteItem(item);
		for(CartItem cartItem : cartItems) {
			this.cartItemRepository.delete(cartItem);			
		}		
	}
	
	public void deleteCartItemById(Long id) {
		this.cartItemRepository.deleteById(id);
	}

}	
