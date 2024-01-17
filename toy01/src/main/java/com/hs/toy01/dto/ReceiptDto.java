package com.hs.toy01.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ReceiptDto {
	private String vihecleNo;
	private long days;
	private long hours;
	private long minutes;
	private long price;

}

