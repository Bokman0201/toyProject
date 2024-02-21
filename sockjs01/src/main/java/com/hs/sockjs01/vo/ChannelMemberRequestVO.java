package com.hs.sockjs01.vo;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ChannelMemberRequestVO {
	
	private Set<String> memberList;
	private int channelNo;

}
