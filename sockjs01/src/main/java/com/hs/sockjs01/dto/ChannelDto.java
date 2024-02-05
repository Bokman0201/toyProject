package com.hs.sockjs01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ChannelDto {

	private int channelNo;
	private String channelName;
	private String channelHost;
	private String channelCreateDate;
}
