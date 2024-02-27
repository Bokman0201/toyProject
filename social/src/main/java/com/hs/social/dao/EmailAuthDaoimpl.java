package com.hs.social.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.social.dto.EmailAuthDto;

@Repository
public class EmailAuthDaoimpl implements EmailAuthDao {
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void insertAuth(EmailAuthDto emailAuthDto) {
	        sqlSession.insert("email.auth", emailAuthDto);

	}
	@Override
	public void deleteAuth(String email) {
		sqlSession.delete("email.deleteAuth",email);
	}
	@Override
	public EmailAuthDto findAuth(String email) {
		
		return sqlSession.selectOne("email.findAuth",email);
	}
}
