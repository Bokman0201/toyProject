package com.hs.sockjs01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hs.sockjs01.dao.ChatMessageDao;
import com.hs.sockjs01.dto.ChannelMemberDto;
import com.hs.sockjs01.dto.ChatMessageDto;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class MessageService {
	@Autowired
	private ChatMessageDao chatMessageDao;
	
	public void saveMessage(ChatMessageDto chatMessageDto)	{
		
		log.debug("message={}",chatMessageDto);
		chatMessageDao.saveMessage(chatMessageDto);
		
	}
	
	public List<ChannelMemberDto> getMemberList (int chatRoomNo){
		
		return chatMessageDao.getMemberList(chatRoomNo);
		
	}

}
