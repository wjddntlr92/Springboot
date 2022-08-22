package com.mysite.sbb.item;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/item")
@Controller
public class ItemController {
	
	private final UserService userService;
	private final ItemService itemService;
	
	
	@GetMapping("/list")
	public String itemList() {
		
		return "/item/list";
	}
	
	@GetMapping("/upload")
	private String uploadItem(ItemUploadForm itemUploadForm) {
		return "/item/upload";
	}
	
	@PostMapping("/upload")
	private String uploadItem(@Valid ItemUploadForm itemUploadForm, BindingResult bindingResult, Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "/item/upload";
		}
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		this.itemService.createItem(itemUploadForm.getItemName(),itemUploadForm.getItemDetail(),siteUser, itemUploadForm.getPrice(),itemUploadForm.getAmount());
		
		
		return "redirect:/item/list";
	}

}
