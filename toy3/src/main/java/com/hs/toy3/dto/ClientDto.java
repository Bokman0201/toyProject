package com.hs.toy3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ClientDto {
	private long companyId;
	private String clientEmail;
	private String clientPw;
	private String clientName;
	private String clientBirth;
	private String clientJoin;
}
