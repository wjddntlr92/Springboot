package com.mysite.sbb.cart;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.h2.util.json.JSONArray;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mysite.sbb.cartItem.CartItem;
import com.mysite.sbb.cartItem.CartItemDTO;
import com.mysite.sbb.cartItem.CartItemService;
import com.mysite.sbb.item.ItemService;
import com.mysite.sbb.item.SiteItem;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import lombok.RequiredArgsConstructor;


@RequestMapping("/cart")
@Controller
@RequiredArgsConstructor
public class CartController {
	
	private final UserService userService;
	private final CartService cartService;	
	private final ItemService itemService;
	private final CartItemService cartItemService;
	
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping("/list")
	public String showCartList(Model model, Principal principal) {
		SiteUser siteUser = this.userService.getUser(principal.getName());
		Cart cart = this.cartService.getCart(siteUser);
		int totalPrice = this.cartService.getTotalPrice(cart);
		
		model.addAttribute("cart", cart);
		model.addAttribute("totalPrice", totalPrice);
		
		return "/cart/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/create")
	public String createCartItem(@Valid CartItemDTO cartItemDTO, BindingResult bindingResult ,Principal principal, RedirectAttributes redirect, Model model) {
				
		SiteItem siteItem = this.itemService.getItem(cartItemDTO.getItemId());
		SiteUser siteUser = this.userService.getUser(principal.getName());
		Cart cart = this.cartService.getCart(siteUser);
		Optional<CartItem> cartItem = this.cartItemService.getPersonCartItem(siteItem, cart);
		
		if(cartItem.isPresent()) {
			bindingResult.reject("중복에러", "이미 장바구에 추가되었습니다.");
			
			
			redirect.addFlashAttribute("bindingResult", bindingResult);
			return String.format("redirect:/item/detail/%s",cartItemDTO.getItemId());		}
		
		
		
		this.cartItemService.createCartItem(cart, siteItem, cartItemDTO.getItemAmount());
		
		model.addAttribute("addItemSuccess", "성공");
		
		return String.format("redirect:/item/detail/%s", cartItemDTO.getItemId());
		
	}

	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String deletePersonCartItem(@PathVariable("id") Long id ,Principal principal) {		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		Optional<CartItem> cartItem = this.cartItemService.getCartItemById(id);
		
		if(cartItem.isPresent()) {
			this.cartItemService.deleteCartItemById(id);
			
			return "redirect:/cart/list/";
		}
		
		return "redirect:/cart/list/";
	}
	
	
}
