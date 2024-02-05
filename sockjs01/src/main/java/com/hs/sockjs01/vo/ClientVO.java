package com.hs.sockjs01.vo;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class ClientVO {
	@JsonIgnore
	private WebSocketSession session;
	private String userId, userNick;
	//private String messageTime;
	
	public ClientVO(WebSocketSession session) {
		this.session = session;
		
		Map<String, Object> attr = session.getAttributes();
		this.userId = (String) attr.get("userId");
		this.userNick = (String) attr.get("userNick");
	}

	public void sendMessage(TextMessage message) throws IOException {
		
		session.sendMessage(message);
		
	}
	
}
