package com.hs.sockjs01.websocket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hs.sockjs01.vo.ClientVO;
import com.hs.sockjs01.vo.RoomVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WebSocketService extends TextWebSocketHandler {

	private ObjectMapper mapper = new ObjectMapper();

	private RoomVO members = new RoomVO();

	private Set<RoomVO> roomList = new CopyOnWriteArraySet<>();

	private Map<Integer, RoomVO> chatRooms = new HashMap<>();

	private Set<ClientVO> waitingRoom = new HashSet<>();

	// private Map<Integer, V>

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("session= {}", session);
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

			log.debug("client={}", client);
			log.debug("waitingRoom={}", waitingRoom);

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
			log.debug(message.getPayload());
			String chatMessageSender = (String) params.get("sender");
			String content = (String) params.get("content");
			Integer roomNo = (Integer) params.get("roomNo");

			Map<String, Object> map = new HashMap<>();
			map.put("content", content);
			map.put("userId", chatMessageSender);
			map.put("userNick", client.getUserNick());
			map.put("type", "message");
			String messageJSON = mapper.writeValueAsString(map);

			TextMessage tm = new TextMessage(messageJSON);

			for (RoomVO room : roomList) {
				if (room.getChatRoomNo() == roomNo) {
					log.debug("members ={} ", room.getChatMembers());
					room.send(client, tm);
				}
			}
		}

		else if (type.equals("enter")) {
			log.debug("enter={}", message.getPayload());
			int chatRoomNo = (int) params.get("chatRoomNo");
			log.debug("room={}", roomList);
			String userId = (String)params.get("userId");

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
			    log.debug("roomNo={},chatRoomNo={}",roomNo,chatRoomNo);
			    
			    boolean result =roomNo != chatRoomNo;
			    log.debug("result={}",result);

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
			log.debug("list={}", roomList);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 접속 종료시 세션 제거
		ClientVO client = new ClientVO(session);

		log.debug("sessionclose={}", session);
		waitingRoom.remove(client);
		members.exit(client);
//		Iterator<Map.Entry<Integer, ClientVO>> iterator = chatRooms.entrySet().iterator();
//		while (iterator.hasNext()) {
//		    Map.Entry<Integer, ClientVO> entry = iterator.next();
//		    ClientVO clientInMap = entry.getValue();
//
//		    if (clientInMap.getUserId().equals(client.getUserId())) {
//		        iterator.remove(); // 해당 클라이언트를 제거
//		    }
//		}		log.debug("chatRoomMap={}",chatRooms);
	}
}
