package com.hs.toy3.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.toy3.dao.ClientDao;
import com.hs.toy3.dao.InviteMemberDao;
import com.hs.toy3.dto.ClientDto;
import com.hs.toy3.dto.InviteMemberDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/invite")
@CrossOrigin
@Slf4j
@Tag(name = "invite")
public class InviteMemberRestController {
	
	@Autowired
	private InviteMemberDao inviteMemberDao;
	@Autowired
	private ClientDao clientDao;
	
	@PostMapping("/inviteMember/{memberEmail}/{companyId}")
	public ResponseEntity<?> inviteMember(@PathVariable String memberEmail , @PathVariable long companyId) {
		
		List<ClientDto> list = clientDao.clientList(companyId);
		
		for(ClientDto client : list) {
			log.debug("list={}",list);
			if(client.getClientEmail().equals(memberEmail) ) {
				log.debug("result={}",client.getClientEmail()== memberEmail);
				return ResponseEntity.badRequest().build();
			}
		}
		//회사 멤버 조회 후 이미 있는 멤버인지 확인 
		//있는 아이디면 리턴
		
		//성공시 이메일전송 회원 가입 페이지로 이동
		
		inviteMemberDao.inviteMember(companyId,memberEmail);
		
		
		return ResponseEntity.ok(null);
	}
	
	@DeleteMapping("inviteCancel/{memberEmail}")
	public void inviteCancel(@PathVariable String memberEmail) {
		
		
		inviteMemberDao.inviteCancel(memberEmail);
	}
	
	@GetMapping("/invitedMemberList/{companyId}")
	public List<InviteMemberDto> invitedMember(@PathVariable long companyId){
		
		return inviteMemberDao.invitedMemberList(companyId);
	}

}
