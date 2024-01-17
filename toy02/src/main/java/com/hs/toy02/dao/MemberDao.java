package com.hs.toy02.dao;

import java.util.List;

import com.hs.toy02.dto.MemberDto;

public interface MemberDao {

	void memberJoin(MemberDto memberDto);

	MemberDto selectOne(String memberId);

	MemberDto memberList(String memberId);

}
