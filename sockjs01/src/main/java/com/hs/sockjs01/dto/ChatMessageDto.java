package com.hs.sockjs01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ChatMessageDto {
	
    private Long chatMessageNo;
    private int chatRoomNo;
    private String chatMessageSender; 
    private String chatMessageReceiver; 
    private String chatMessageContent;
    private String chatMessageSendDate;
    private String chatMessageStatus;
}
