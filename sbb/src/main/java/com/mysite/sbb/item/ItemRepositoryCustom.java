package com.mysite.sbb.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
	
	Page<SiteItem> findBySearchOption(Pageable pageable, String kw);

}
