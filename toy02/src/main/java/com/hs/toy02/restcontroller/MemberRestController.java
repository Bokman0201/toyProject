package com.hs.toy02.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.toy02.dao.MemberDao;
import com.hs.toy02.dto.MemberDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
@Tag(name = "member")
@Slf4j
@RestController
@RequestMapping("/member")
@CrossOrigin
public class MemberRestController {
	
	@Autowired
	private MemberDao memberDao;
	
	@PostMapping("/memberJoin")
	public ResponseEntity<?> memberJoin(@RequestBody MemberDto memberDto){
		memberDao.memberJoin(memberDto);
		return ResponseEntity.ok("ok");
	}
	
	@PostMapping("/memberList")
	public MemberDto memberList (@RequestBody MemberDto memberDto){
		
		return memberDao.memberList(memberDto.getMemberId());
	}
	
	@PostMapping("/memberLogin")
	public ResponseEntity<?> memberLogIn(@RequestBody MemberDto memberDto){
		if(memberDto == null) {
			
			return ResponseEntity.notFound().build();
		}
		
		MemberDto findDto = memberDao.selectOne(memberDto.getMemberId());
		
		if(memberDto.getMemberPw().equals(findDto.getMemberPw())) {
			
			return ResponseEntity.ok(findDto);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

}
