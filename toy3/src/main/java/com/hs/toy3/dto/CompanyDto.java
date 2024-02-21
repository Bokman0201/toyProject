package com.hs.toy3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyDto {

	private long companyId;
	private String companyName;
	private String companyInviteCode;
	private String companyPost;
	private String companyAddr1;
	private String companyAddr2;
	private String companyTel;
	private String companySubscriptionStatus;
	private String companyHost;
}
