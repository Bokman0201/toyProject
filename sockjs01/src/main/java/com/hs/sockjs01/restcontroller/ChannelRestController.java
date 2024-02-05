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

import com.hs.sockjs01.dao.ChannelDao;
import com.hs.sockjs01.dto.ChannelDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/channel")
@CrossOrigin
@Tag(name = "channel")
@Slf4j
public class ChannelRestController {
	@Autowired
	private ChannelDao channelDao;
	
	@PostMapping("/addChannel")
	public void addChannel(@RequestBody ChannelDto channelDto) {
		
		int channelNo = channelDao.sequence();
		channelDto.setChannelNo(channelNo);
		log.debug("no={}",channelNo);
		log.debug("dto={}",channelDto);

		channelDao.addChannel(channelDto);
		
	}
	
	@GetMapping("/findmyChannel/{channelHost}")
	public List<ChannelDto> findMyChannel(@PathVariable String channelHost){
		
		return channelDao.findMyChannel(channelHost);
	}
	
	@GetMapping("/channelInfo/{channelNo}")
	public ChannelDto channelInfo(@PathVariable int channelNo) {
		
		return channelDao.channelInfo(channelNo);
	}
	
	@GetMapping("/getChannels/{userId}")
	public List<ChannelDto> getChannels(@PathVariable String userId){
		
		return channelDao.getChannels(userId);
	}

}
