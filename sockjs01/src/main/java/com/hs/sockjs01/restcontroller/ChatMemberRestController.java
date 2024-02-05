package com.hs.sockjs01.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.sockjs01.dao.ChatMemberDao;
import com.hs.sockjs01.dto.ChatMemberDto;
import com.hs.sockjs01.vo.ChatMemberRequestVO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/chatMember")
@RestController
@Slf4j
@Tag(name = "chatMember")
@CrossOrigin
public class ChatMemberRestController {
	
	@Autowired
	private ChatMemberDao chatMemberDao;
	
	@PostMapping("/addMember")
	public void addMember(@RequestBody ChatMemberRequestVO requestVO) {
		
		ChatMemberDto chatMemberDto = new ChatMemberDto();
		
		List<String> members = requestVO.getUserId();
		for(String member : members) {
			chatMemberDto.setChatMemeberId(member);
			chatMemberDto.setChatRoomNo(requestVO.getChatRoomNo());
			if(chatMemberDto != null) {
				chatMemberDao.addMember(chatMemberDto);
			}
		}
		
		
		
	}

}
