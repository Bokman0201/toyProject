package com.hs.toy02.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy02.dto.MemberDto;

@Repository
public class MemberDaoImpl implements MemberDao{
	@Autowired
	private SqlSession sqlSession;
	@Override
	public void memberJoin(MemberDto memberDto) {
		
		sqlSession.insert("member.memberJoin",memberDto);
		
	}
	@Override
	public MemberDto selectOne(String memberId) {
		return sqlSession.selectOne("member.selectOne",memberId);
	}
	@Override
	public MemberDto memberList(String memberId) {
		return sqlSession.selectOne("member.memberList", memberId);
	}
	

}
