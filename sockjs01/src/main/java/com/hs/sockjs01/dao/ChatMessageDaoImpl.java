package com.hs.sockjs01.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.sockjs01.dto.ChannelMemberDto;
import com.hs.sockjs01.dto.ChatMessageDto;

@Repository
public class ChatMessageDaoImpl implements ChatMessageDao{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<ChannelMemberDto> getMemberList(int channelNo) {
		return sqlSession.selectList("chatMessage.getMemberList",channelNo);
	}

	@Override
	public void saveMessage(ChatMessageDto chatMessageDto) {
		sqlSession.insert("chatMessage.saveMessage",chatMessageDto);
	}

	@Override
	public int getCount(String userId, int chatRoomNo) {
		Map<String,Object> params =Map.of("userId",userId, "chatRoomNo", chatRoomNo); 
		return sqlSession.selectOne("chatMessage.getCount",params);
	}

	@Override
	public List<ChatMessageDto> getMessageList(String userId, int chatRoomNo) {
		Map<String,Object> params =Map.of("userId",userId, "chatRoomNo", chatRoomNo); 
		return sqlSession.selectList("chatMessage.chatMessageList",params);
	}
	
	@Override
	public void updateStatus(int chatRoomNo, String chatMessageReceiver) {
		Map<String,Object> params =Map.of("chatRoomNo",chatRoomNo, "chatMessageReceiver", chatMessageReceiver); 

		sqlSession.update("chatMessage.updateStatus",params);
	}
	
}
