package com.mysite.sbb.item;

import java.io.IOException;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.cartItem.CartItemService;
import com.mysite.sbb.itemImg.ItemImgService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/item")
@Controller
public class ItemController {
	
	private final UserService userService;
	private final ItemService itemService;
	private final ItemImgService itemImgService;
	private final CartItemService cartItemService;
	
	
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/upload")
	public String uploadItem(ItemUploadForm itemUploadForm) {
		return "/item/form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/upload")
	public String uploadItem(@Valid ItemUploadForm itemUploadForm, BindingResult bindingResult, @RequestParam("imgs") List<MultipartFile> imgs, Principal principal) throws IOException {
		if(bindingResult.hasErrors()) {
			return "/item/form";
		}
		
		
		
		if(this.itemImgService.imgFileCheck(imgs)==false) {
			bindingResult.reject("이미지이상", "이미지 파일만 첨부 가능합니다.");
			return "/item/form";
		}				
		
		SiteUser siteUser = this.userService.getUser(principal.getName());
		SiteItem siteItem = this.itemService.createItem(itemUploadForm.getItemName(),itemUploadForm.getItemDetail(),siteUser, itemUploadForm.getPrice(),itemUploadForm.getAmount());
		
		this.itemImgService.createImg(imgs, siteItem);
		
		return "redirect:/item/list";
	}
	
	
	@GetMapping("/list")
	public String showItemList(Model model, 
			@RequestParam(value="page", defaultValue="0") int page, 
			@RequestParam(value="kw", defaultValue="") String kw) {
		
		Page<SiteItem> paging = this.itemService.getItemList(page);
	
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		
		
		return "/item/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/detail/{id}")
	public String showItemDetail(@PathVariable("id") Long id, Model model) {
		
		SiteItem item = this.itemService.getItem(id);
		model.addAttribute("item", item);
		model.addAttribute("addItemSuccess", null);
		
		return "/item/detail";
		
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete/{id}")
	public String deleteItem(@PathVariable("id") Long id, Principal principal) {
		SiteItem item = this.itemService.getItem(id);
		if(!item.getAuthor().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제할 권한이 없습니다.");
		}
		
		this.itemImgService.deleteImg(item);
		this.cartItemService.deleteAllCartItem(item);	
		
		this.itemService.deleteItem(item);
		
		
		return "redirect:/item/list";
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/modify/{id}")
	public String modifyItem(@PathVariable("id") Long id, Principal principal, ItemUploadForm itemUploadForm) {
		
		
		
		SiteItem item = this.itemService.getItem(id);
		if(!item.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정할 권한이 없습니다.");
		}
		
		itemUploadForm.setItemName(item.getItemName());
		itemUploadForm.setItemDetail(item.getItemDetail());
		itemUploadForm.setAmount(item.getAmount());
		itemUploadForm.setPrice(item.getPrice());
		itemUploadForm.setItemStatus(item.getItemStatus());
		
		
		
		return"/item/form";
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/modify/{id}")
	public String modifyItem(@Valid ItemUploadForm itemUploadForm,
							BindingResult bindingResult,
							@PathVariable("id") Long id,
							@RequestParam("imgs") List<MultipartFile> imgs,
							Principal principal
							) throws IOException {
		if(bindingResult.hasErrors()) {
			return "/item/form";
		}
		
	
		
		if(this.itemImgService.imgFileCheck(imgs)==false) {	
			
			
						
			
			bindingResult.reject("이미지이상", "이미지 파일만 첨부 가능합니다.");
			return "/item/form";
			
		}				
		
		SiteItem item = this.itemService.getItem(id);
		
		if(!item.getAuthor().getUsername().equals(principal.getName())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정할 권한이 없습니다.");
		}
		
		this.itemImgService.deleteImg(item);
		this.itemImgService.createImg(imgs,item);
		this.itemService.modifyItem(item, itemUploadForm);
		
		
		return "redirect:/item/list";
	}

}
