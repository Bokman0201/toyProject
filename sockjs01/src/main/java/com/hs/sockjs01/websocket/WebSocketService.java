package com.hs.sockjs01.websocket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hs.sockjs01.dto.ChannelMemberDto;
import com.hs.sockjs01.dto.ChatMessageDto;
import com.hs.sockjs01.service.MessageService;
import com.hs.sockjs01.vo.ClientVO;
import com.hs.sockjs01.vo.RoomVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebSocketService extends TextWebSocketHandler {

	private ObjectMapper mapper = new ObjectMapper();

	private RoomVO members = new RoomVO();

	private Set<RoomVO> roomList = new CopyOnWriteArraySet<>();

	private Set<ClientVO> waitingRoom = new HashSet<>();
	
     // 현재 한국 시간으로 LocalDateTime 객체 생성
    Date currentDate = new Date();
    // Create a SimpleDateFormat object with the desired format
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // Format the Date object using the SimpleDateFormat
    String formattedDate = dateFormat.format(currentDate);

	@Autowired
	private MessageService messageService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//log.debug("session= {}", session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		Map params = mapper.readValue(message.getPayload(), Map.class);

		String type = (String) params.get("type");
		ClientVO client = new ClientVO(session);

		if (type.equals("login")) {
			session.getAttributes().put("userId", params.get("userId"));
			session.getAttributes().put("userNick", params.get("userNick"));

			client = new ClientVO(session);

			boolean existusr = false;

			for (ClientVO user : waitingRoom) {
				if (user.getUserId() == client.getUserId()) {
					existusr = true;
					break;
				}
			}

			if (!existusr) {
				waitingRoom.add(client);
			}

			//
			// 0이면 대기실

			//log.debug("client={}", client);
			//log.debug("waitingRoom={}", waitingRoom);

			for (ClientVO user : waitingRoom) {
				if (!user.getUserId().equals(client.getUserId())) {

					Map<String, Object> map = new HashMap<>();
					map.put("userId", client.getUserId());
					map.put("userNick", client.getUserNick());
					map.put("type", "login");

					String messageJSON = mapper.writeValueAsString(map);

					TextMessage tm = new TextMessage(messageJSON);
					user.sendMessage(tm);
				}
			}
		}

		else if (type.equals("message")) {
			//log.debug(message.getPayload());
			String chatMessageSender = (String) params.get("sender");
			String content = (String) params.get("content");
			Integer roomNo = (Integer) params.get("roomNo");

			Map<String, Object> map = new HashMap<>();
			map.put("type", "message");

			map.put("chatMessageContent", content);
			map.put("chatMessageSender", chatMessageSender);
			map.put("userNick", client.getUserNick());
			map.put("chatRoomNo",roomNo);
			map.put("chatMessageSendDate",formattedDate.toString() );
			
			String messageJSON = mapper.writeValueAsString(map);

			TextMessage tm = new TextMessage(messageJSON);

			List<ChannelMemberDto> list = messageService.getMemberList(roomNo);
			for (RoomVO room : roomList) {
			    if (room.getChatRoomNo() == roomNo) {
			        for (ChannelMemberDto member : list) {
						ChatMessageDto messageDto= new ChatMessageDto();
			            messageDto.setChatMessageContent(content);
			            messageDto.setChatMessageReceiver(member.getUserId());
			            messageDto.setChatMessageSender(chatMessageSender);
			            messageDto.setChatRoomNo(roomNo);

			            Set<ClientVO> clientList = room.getChatMembers();
			            boolean isMemberFound = false;
			            for (ClientVO c : clientList) {
			                if (c.getUserId().equals(member.getUserId())) {
			                    messageDto.setChatMessageStatus("0");
			                    isMemberFound = true;
			                    break; 
			                }
			            }

			            if (!isMemberFound) {
			                messageDto.setChatMessageStatus("1");
			            }

			            messageService.saveMessage(messageDto);
			        }
			        
			        room.send(client, tm);
			    }
			}
			//log.debug("memberList={}", list);
		}

		else if (type.equals("enter")) {
			//log.debug("enter={}", message.getPayload());
			int chatRoomNo = (int) params.get("chatRoomNo");
			//log.debug("room={}", roomList);
			String userId = (String) params.get("userId");

			// 방이 존재하는지 확인
			RoomVO roomVO = null;
			for (RoomVO existingRoom : roomList) {
				if (existingRoom.getChatRoomNo() == chatRoomNo) {
					roomVO = existingRoom;
					break;
				}
			}
			if (roomVO == null) {
				roomVO = RoomVO.builder().chatRoomNo(chatRoomNo).build();
				roomList.add(roomVO);
			}
			for (RoomVO room : roomList) {
				int roomNo = room.getChatRoomNo();


				// 현재 방인지 확인
				if (roomNo != chatRoomNo) {
					// 현재 방에서 userId로 지정된 클라이언트를 나가게 처리
					Set<ClientVO> members = room.getChatMembers();
					for (ClientVO user : members) {
						if (user.equals(client)) {
							room.exit(client);
						}
					}
				}
			}

			// Add the client to the room
			roomVO.enter(client);
			//log.debug("list={}", roomList);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 접속 종료시 세션 제거
		ClientVO client = new ClientVO(session);

		//log.debug("sessionclose={}", session);
		waitingRoom.remove(client);
		members.exit(client);
		for (RoomVO room : roomList) {
			Set<ClientVO> members = room.getChatMembers();
			for (ClientVO user : members) {
				if (user.equals(client)) {
					room.exit(client);
				}
			}
		}
	}
}
