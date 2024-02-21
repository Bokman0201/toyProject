package com.hs.toy3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class InviteMemberDto {

	private long companyId;
	private String inviteMemberEmail;
	private String inviteDate;
	private String inviteStatus;
}
