package com.hs.toy02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDetailDto {
	private int productNo;
	private String productDetailTitle;
	private String productDetailContent;

}
