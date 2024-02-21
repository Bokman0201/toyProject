package com.hs.toy3.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy3.dto.InviteMemberDto;

@Repository
public class InviteMemberDaoImpl implements InviteMemberDao{

	@Autowired
	private SqlSession sqlSession;
	@Override
	public void inviteMember(long companyId, String memberEmail) {
		
		Map<String, Object> params = Map.of("companyId", companyId,"memberEmail",memberEmail);
		sqlSession.insert("invite.inviteMember",params);
	}
	@Override
	public void inviteCancel(String memberEmail) {
		sqlSession.delete("invite.inviteCancel",memberEmail);
	}
	
	@Override
	public List<InviteMemberDto> invitedMemberList(long companyId) {
		return sqlSession.selectList("invite.invitedMember", companyId);
	}
}
