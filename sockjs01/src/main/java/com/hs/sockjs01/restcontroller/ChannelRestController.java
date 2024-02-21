package com.hs.sockjs01.restcontroller;

import java.sql.Time;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.sockjs01.dao.ChannelDao;
import com.hs.sockjs01.dao.ChatMemberDao;
import com.hs.sockjs01.dto.ChannelDto;
import com.hs.sockjs01.dto.ChannelMemberDto;
import com.hs.sockjs01.dto.ChatMemberDto;
import com.hs.sockjs01.vo.ChannelMemberRequestVO;

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
		channelDao.addChannel(channelDto);

		// 채널 개설시 내아이디 넣기

		String host = channelDto.getChannelHost();

		ChannelMemberDto channelMemberDto = new ChannelMemberDto();
		channelMemberDto.setChannelNo(channelNo);
		channelMemberDto.setUserId(host);
		log.debug("dto={}", channelMemberDto);

		channelDao.addChannelMember(channelMemberDto);

	}

	@GetMapping("/findmyChannel/{channelHost}")
	public List<ChannelDto> findMyChannel(@PathVariable String channelHost) {

		return channelDao.findMyChannel(channelHost);
	}

	@GetMapping("/channelInfo/{channelNo}")
	public ChannelDto channelInfo(@PathVariable int channelNo) {

		return channelDao.channelInfo(channelNo);
	}

	@GetMapping("/getChannels/{userId}")
	public List<ChannelDto> getChannels(@PathVariable String userId) {

		return channelDao.getChannels(userId);
	}

	@PostMapping("/addMember")
	public void addMember(@RequestBody ChannelMemberRequestVO channelMemberRequestVO) {		
		//멤버 가져와서 리스에 들어있는 사람
		
		Set<String> memberList = channelMemberRequestVO.getMemberList();
		
		ChannelMemberDto channelMemberDto = new ChannelMemberDto();
		channelMemberDto.setChannelNo(channelMemberRequestVO.getChannelNo());
		for(String member : memberList) {
			channelMemberDto.setUserId(member);
			channelDao.addChannelMember(channelMemberDto);
		}
	}

}
