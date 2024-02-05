package com.hs.sockjs01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatRoomsDto {
	private int channelNo;
	private int chatRoomNo;
	private String chatRoomName;
}
