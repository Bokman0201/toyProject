package com.hs.toy02.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy02.dto.AttachDto;
import com.hs.toy02.dto.ProductImageDto;

@Repository
public class AttachDaoImpl implements AttachDao{
	@Autowired 
	private SqlSession sqlSession;
	
	@Override
	public int sequence() {
		return sqlSession.selectOne("attach.sequence");
	}

	@Override
	public void addImage(AttachDto attachDto) {
		sqlSession.insert("attach.addAttach",attachDto);
	}

	@Override
	public void addConnecter(ProductImageDto productImageDto) {
		sqlSession.insert("attach.addConnecter",productImageDto);
		
	}
	
	@Override
	public List<AttachDto> getImageList(int productNo) {
		return sqlSession.selectList("attach.getImageList",productNo);
	}

}
