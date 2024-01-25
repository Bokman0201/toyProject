package com.hs.toy02.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDetailVO {

	private int productNo,productPrice,productSellCount;
	private String productName,
	productSeller,productUploadDate,productDetailTitle,productDetailContent;
}
