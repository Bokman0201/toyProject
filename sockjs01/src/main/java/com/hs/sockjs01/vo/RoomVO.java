package com.hs.sockjs01.vo;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class RoomVO {
	
	
	private int chatRoomNo;
	
	@Builder.Default
	private Set<ClientVO> chatMembers = new CopyOnWriteArraySet<>();
	
	
	public void enter(ClientVO client) {
		
		chatMembers.add(client);
	}
	
	public void exit(ClientVO client) {
		chatMembers.remove(client);
	}
	public void send(ClientVO client,TextMessage message) throws IOException {
		for(ClientVO user : chatMembers) {
			user.sendMessage(message);
		}
	}
	
	
	

}
