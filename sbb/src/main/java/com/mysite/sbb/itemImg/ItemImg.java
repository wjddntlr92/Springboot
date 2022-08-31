package com.mysite.sbb.itemImg;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import com.mysite.sbb.item.SiteItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ItemImg {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private SiteItem siteItem;
	
	@Column
	private String itemImgOriginName;
	
	@Column
	private String itemImgUploadName;
	
	@Column
	private String itemImgUploadPath;
	
	private int mainImg;	
	
	@CreatedDate
    private LocalDateTime createDate;
	
	private LocalDateTime modifyDate;
	

}
