package com.hs.sockjs01.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatMemberRequestVO {
	private int chatRoomNo;
	private List<String> userId;
}
