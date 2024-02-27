package com.hs.social.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.social.dto.AttachDto;

@Repository
public class ClientImageDaoImpl implements ClientImageDao {
	@Autowired 
	private SqlSession sqlSession;
	@Override
	public void addImage(int attachNo, String clientEmail) {
		Map<String, Object> params = Map.of("attachNo", attachNo, "clientEmail", clientEmail);
		
		sqlSession.insert("clientImg.addImage",params);
	}
	@Override
	public AttachDto findImg(String clientEmail) {
		return sqlSession.selectOne("clientImg.findImg",clientEmail);
	}
}
