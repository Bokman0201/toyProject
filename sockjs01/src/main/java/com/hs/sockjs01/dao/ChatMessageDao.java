package com.hs.sockjs01.dao;

import java.util.List;

import com.hs.sockjs01.dto.ChannelMemberDto;
import com.hs.sockjs01.dto.ChatMessageDto;

public interface ChatMessageDao {

	List<ChannelMemberDto> getMemberList(int channelNo);

	void saveMessage(ChatMessageDto chatMessageDto);

	int getCount(String userId, int chatRoomNo);

	List<ChatMessageDto> getMessageList(String userId, int chatRoomNo);

	void updateStatus(int chatRoomNo, String chatMessageReceiver);

}
