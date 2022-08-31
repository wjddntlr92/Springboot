package com.mysite.sbb.item;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;





public interface ItemRepository extends JpaRepository<SiteItem,Long> {
	
	
	Page<SiteItem> findByitemNameLike(String itemName,Pageable pageable);
	Page<SiteItem> findAll(Pageable pageable);
}
