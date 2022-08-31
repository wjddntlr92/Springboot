package com.mysite.sbb.item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.itemImg.ItemImgService;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ItemService {
	
	private final ItemRepository itemRepository;
	
	
	public SiteItem createItem(String itemName, String itemDetail, SiteUser author, int price, int amount ) {
		
		SiteItem i = new SiteItem();
		i.setItemName(itemName);
		i.setItemDetail(itemDetail);
		i.setAuthor(author);
		i.setCreateDate(LocalDateTime.now());
		i.setPrice(price);
		i.setAmount(amount);
		i.setItemStatus(ItemStatus.SELL);
		
		return this.itemRepository.save(i);
		
	}
	
	public Page<SiteItem> getItemList(int page){
		List<Sort.Order> oders = new ArrayList<>();
		oders.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 9, Sort.by(oders));
		
		return this.itemRepository.findAll(pageable);
		
	}
	
	public SiteItem getItem(Long id) {
		Optional<SiteItem> item =	this.itemRepository.findById(id);
		if(item.isPresent()) {
			return item.get();
		}
		else {
			throw new DataNotFoundException("Item Not Found");
		}
		
	}
	
	public void deleteItem(SiteItem item) {
				
		this.itemRepository.delete(item);
	}
	
	
	public void modifyItem(SiteItem item, ItemUploadForm itemUploadForm) {
		item.setItemName(itemUploadForm.getItemName());
		item.setItemDetail(itemUploadForm.getItemDetail());
		item.setAmount(itemUploadForm.getAmount());
		item.setPrice(itemUploadForm.getPrice());
		item.setModifyDate(LocalDateTime.now());
		item.setItemStatus(itemUploadForm.getItemStatus());
		
		
		this.itemRepository.save(item);
	}

}
