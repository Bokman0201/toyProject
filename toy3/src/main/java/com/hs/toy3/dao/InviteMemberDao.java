package com.hs.toy3.dao;

import java.util.List;

import com.hs.toy3.dto.InviteMemberDto;

public interface InviteMemberDao {

	void inviteMember( long companyId, String memberEmail);

	void inviteCancel(String memberEmail);

	List<InviteMemberDto> invitedMemberList(long companyId);

}
