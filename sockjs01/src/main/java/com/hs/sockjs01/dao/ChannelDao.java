package com.hs.sockjs01.dao;

import java.util.List;

import com.hs.sockjs01.dto.ChannelDto;
import com.hs.sockjs01.dto.ChannelMemberDto;

public interface ChannelDao {

	void addChannel(ChannelDto channelDto);

	int sequence();

	List<ChannelDto> findMyChannel(String channelHost);

	ChannelDto channelInfo(int channelNo);

	List<ChannelDto> getChannels(String userId);

	void addChannelMember(ChannelMemberDto channelMemberDto);

}
