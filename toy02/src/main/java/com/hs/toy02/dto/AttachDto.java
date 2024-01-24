package com.hs.toy02.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AttachDto {
	private int attachNo;
	private String attachName;
	private long  attachSize;
	private String attachType;
	private Date attachTime;
}
