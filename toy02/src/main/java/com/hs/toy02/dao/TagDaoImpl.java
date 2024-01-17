package com.hs.toy02.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy02.dto.TagDto;
import com.hs.toy02.dto.TaggedProductDto;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Repository
public class TagDaoImpl implements TagDao{

	@Autowired
	private SqlSession sqlSession;
	@Override
	public int sequence() {
		System.out.println("try");
		return sqlSession.selectOne("tag.sequence");
	}

	@Override
	public void insertConnect(TaggedProductDto taggedProductDto) {
		sqlSession.insert("tag.insertConnect", taggedProductDto);
		
	}

	@Override
	public List<String> allList() {
		return sqlSession.selectList("tag.allList");
	}

	@Override
	public void addTag(TagDto tagDto) {
		sqlSession.insert("tag.addTag",tagDto);
		
	}

	@Override
	public int findNo(String tag) {
		return sqlSession.selectOne("tag.findNo",tag);
	}

}
