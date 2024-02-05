package com.hs.sockjs01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.sockjs01.dto.ChannelDto;

@Repository
public class ChannelDaoImpl implements ChannelDao{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void addChannel(ChannelDto channelDto) {
		sqlSession.insert("channel.addChannel",channelDto);
	}
	@Override
	public int sequence() {
		return sqlSession.selectOne("channel.sequence");
	}
	
	@Override
	public List<ChannelDto> findMyChannel(String channelHost) {
		return sqlSession.selectList("channel.myChannelList",channelHost);
	}
	@Override
	public ChannelDto channelInfo(int channelNo) {
		return sqlSession.selectOne("channel.channelInfo" , channelNo);
	}
	@Override
	public List<ChannelDto> getChannels(String userId) {
		return sqlSession.selectList("channel.getChannels",userId);
	}
}
