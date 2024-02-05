package com.hs.sockjs01.dao;

import java.util.List;

import com.hs.sockjs01.dto.ChatRoomsDto;

public interface ChatRoomDao {

	void addRoom(ChatRoomsDto chatRoomsDto);

	int sequence();

	List<ChatRoomsDto> roomList(int channelNo);

}
