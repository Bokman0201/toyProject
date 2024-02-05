package com.hs.sockjs01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.sockjs01.dto.UsersDto;

@Repository
public class UsersDaoImpl implements UsersDao{

	@Autowired
	private SqlSession sqlSession; 
	@Override
	public void addUser(UsersDto usersDto) {
		sqlSession.insert("users.userJoin",usersDto);
	}
	
	@Override
	public List<UsersDto> userList() {
		return sqlSession.selectList("users.userList");
	}
	@Override
	public String getPw(String userId) {
		return sqlSession.selectOne("users.getPw",userId);
	}

	@Override
	public UsersDto selectOne(String userId) {
		return sqlSession.selectOne("users.selectOne", userId);
	}
}
