package com.hs.sockjs01.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.sockjs01.dao.ChatMessageDao;
import com.hs.sockjs01.dto.ChatMessageDto;

@RestController
@CrossOrigin
@RequestMapping("/message")
public class ChatMessageRestController {
	
	@Autowired
	private ChatMessageDao chatMessageDao;
	
	@GetMapping("/getCount/{userId}/{chatRoomNo}")
	public int getCount(@PathVariable String userId,@PathVariable int chatRoomNo ) {
		
		return chatMessageDao.getCount(userId, chatRoomNo);
	}
	
	@GetMapping("/chatMessageList/{userId}/{chatRoomNo}")
	public List<ChatMessageDto> chatMessageList(@PathVariable String userId,@PathVariable int chatRoomNo ){
		
		return chatMessageDao.getMessageList(userId, chatRoomNo);
	}
	
	@PutMapping("/updateStatus/{chatRoomNo}/{chatMessageReceiver}")
	public void updateStatus(@PathVariable int chatRoomNo,@PathVariable String chatMessageReceiver) {
		
		chatMessageDao.updateStatus(chatRoomNo,chatMessageReceiver);
		
	}

}
