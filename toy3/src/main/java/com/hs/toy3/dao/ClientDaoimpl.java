package com.hs.toy3.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy3.dto.ClientDto;
import com.hs.toy3.dto.CompanyDto;

@Repository
public class ClientDaoimpl implements ClientDao{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void addClient(ClientDto clientDto) {
		sqlSession.insert("client.addClient",clientDto);
	}

	@Override
	public ClientDto findClient(String clientEmail) {
		return sqlSession.selectOne("client.findClient",clientEmail);
	}

	@Override
	public CompanyDto findCompany(String inviteCode) {
		return sqlSession.selectOne("client.findCompany",inviteCode);
	}
	
	@Override
	public List<ClientDto> clientList(long companyId) {
		return sqlSession.selectList("client.list",companyId);
		
		
	}
}
