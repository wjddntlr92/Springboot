package com.mysite.sbb.item;


import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<SiteItem,Long> {
	
	

}
