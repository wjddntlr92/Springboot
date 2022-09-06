package com.mysite.sbb.item;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


import com.querydsl.jpa.impl.JPAQueryFactory;


public class ItemRepositoryImpl extends QuerydslRepositorySupport implements ItemRepositoryCustom {
	
	public final JPAQueryFactory queryFactory;
	
	
	
	public ItemRepositoryImpl(JPAQueryFactory queryFactory) {
		super(SiteItem.class);
		this.queryFactory = queryFactory;		
				
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public Page<SiteItem> findBySearchOption(Pageable pageable, String kw){
		
		List<SiteItem> result = queryFactory
				.selectFrom(QSiteItem.siteItem)
				.where(QSiteItem.siteItem.itemName.contains(kw))				
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		Long count = queryFactory
				.select(QSiteItem.siteItem.count())
				.from(QSiteItem.siteItem)
				.where(QSiteItem.siteItem.itemName.contains(kw))				
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchOne();
		
		return new PageImpl<>(result,pageable,count);
				
		
				
	}
}
