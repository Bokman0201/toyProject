package com.hs.toy01.vo;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class KakaoPayReadyRequestVO {
	private String partnerUserId;
	private String partnerOrderId;
	private String itemName;
	private int itemPrice;
	
	
	
	

}
