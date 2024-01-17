package com.hs.toy01.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class KakaoPayAprroveRequestVO {
	
	private String cid,tid, partnerOrderId,partnerUserId,pgToken;

}
