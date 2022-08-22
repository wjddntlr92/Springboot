package com.mysite.sbb.item;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	
	public void createItem(String itemName, String itemDetail, SiteUser author, int price, int amount ) {
		
		SiteItem i = new SiteItem();
		i.setItemName(itemName);
		i.setItemDetail(itemDetail);
		i.setAuthor(author);
		i.setCreateDate(LocalDateTime.now());
		i.setPrice(price);
		i.setAmount(amount);
		this.itemRepository.save(i);
		
	}
	
	

}
