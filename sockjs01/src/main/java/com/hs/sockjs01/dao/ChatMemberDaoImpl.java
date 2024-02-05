package com.hs.sockjs01.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.sockjs01.dto.ChatMemberDto;

@Repository
public class ChatMemberDaoImpl implements ChatMemberDao{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void addMember(ChatMemberDto chatMemberDto) {
		sqlSession.insert("chatMember.addMember", chatMemberDto);
	}

}
