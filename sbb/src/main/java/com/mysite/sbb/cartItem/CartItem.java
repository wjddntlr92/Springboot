package com.mysite.sbb.cartItem;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.mysite.sbb.cart.Cart;
import com.mysite.sbb.item.SiteItem;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class CartItem {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	
	 @ManyToOne(fetch = FetchType.LAZY)
	 private Cart cart;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 private SiteItem siteItem;
	
	 private int amount;

}
