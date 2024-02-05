package com.hs.sockjs01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class UsersDto {
	
	private String userId;
	private String userPw;
	private String userName;
	private String userNick;
	private String userJoinDate;
	

}
