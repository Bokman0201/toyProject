package com.hs.toy02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MemberDto {
	
	private String memberId;
	private String memberPw;
	private String memberNick;
	private String memberPost;
	private String memberAddr1;
	private String memberAddr2;
	private String memberJoinDate;
	private String memberSeller;

}
