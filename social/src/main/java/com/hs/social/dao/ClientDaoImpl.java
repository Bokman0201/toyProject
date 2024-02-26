package com.hs.social.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.social.dto.ClientDto;

@Repository
public class ClientDaoImpl implements ClientDao{
	
	@Autowired
	private SqlSession sqlSession;
	@Override
	public void clientSignin(ClientDto clientDto) {
		sqlSession.insert("client.singin",clientDto);
	}

}
