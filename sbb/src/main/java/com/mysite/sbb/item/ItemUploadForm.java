package com.mysite.sbb.item;



import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemUploadForm {
	
	@NotEmpty(message="상품 이름은 필수항목입니다.")
    @Size(max=200)
    private String itemName;

    @NotEmpty(message="상품 설명은 필수항목입니다.")
    @Size(max=50, message="설명은 50자 내외입니다.")
    private String itemDetail;
    
    
    private int amount;
    
    
    private int price;
    
    
    @NotNull(message="상품 상태는 필수항목입니다.")
    private ItemStatus itemStatus;
    
   
   
}
