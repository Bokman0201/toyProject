package com.hs.sockjs01.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.sockjs01.dao.ChatRoomDao;
import com.hs.sockjs01.dto.ChatRoomsDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/chatRoom")
@CrossOrigin
@Slf4j
@Tag(name = "chatRoom")
public class ChatRoomRestController {

	
	@Autowired
	private ChatRoomDao chatRoomDao;
	
	@PostMapping("/addRoom")
	public void addChat(@RequestBody ChatRoomsDto chatRoomsDto){
		int roomNo = chatRoomDao.sequence();
		
		chatRoomsDto.setChatRoomNo(roomNo);
		
		chatRoomDao.addRoom(chatRoomsDto);
	}
	
	@GetMapping("/roomList/{channelNo}")
	public List<ChatRoomsDto> roomList(@PathVariable int channelNo){
		return chatRoomDao.roomList(channelNo);
	}
}
