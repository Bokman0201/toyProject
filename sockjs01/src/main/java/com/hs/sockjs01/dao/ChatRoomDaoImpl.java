package com.hs.sockjs01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.sockjs01.dto.ChatRoomsDto;

@Repository
public class ChatRoomDaoImpl implements ChatRoomDao{

	@Autowired
	private SqlSession sqlSession;
	@Override
	public int sequence() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("chatRooms.sequence");
	}
	@Override
	public void addRoom(ChatRoomsDto chatRoomsDto) {
		sqlSession.insert("chatRooms.addRoom",chatRoomsDto);
	}
	
	@Override
	public List<ChatRoomsDto> roomList(int channelNo) {
		return sqlSession.selectList("chatRooms.roomList",channelNo);
	}

}
