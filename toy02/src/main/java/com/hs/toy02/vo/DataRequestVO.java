package com.hs.toy02.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DataRequestVO {

	private String productAttrSize;
	private int productInventoryCount;
	private int productAttrPrice;
}
