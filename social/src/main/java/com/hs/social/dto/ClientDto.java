package com.hs.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ClientDto {
	private String clientEmail ;
	private String clientName ;
	private String clientPw ;
	private String clientJoinDate ;
}
