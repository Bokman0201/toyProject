package com.hs.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class EmailAuthDto {
	private int authId;
	private String clientEmail;
	private String authenticationText;
}
