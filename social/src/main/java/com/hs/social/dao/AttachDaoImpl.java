package com.hs.social.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.social.dto.AttachDto;

@Repository
public class AttachDaoImpl implements AttachDao {
	@Autowired 
	private SqlSession sqlSession;
	@Override
	public int nextNumber() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("attach.nextNum");
	}
	@Override
	public void insert(AttachDto attachDto) {
		sqlSession.insert("attach.insert", attachDto);

	}
}
