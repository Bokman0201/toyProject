package com.hs.toy02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDto {
	private int productNo;
	private String productName;
	private int productPrice;
	private String productSeller;
	private String productUploadDate;
	private int productSellCount;
}
