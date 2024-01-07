package com.hs.toy01.VO;

import java.util.List;

import com.hs.toy01.dto.ParkingDto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ParkingVO {
	private List<ParkingDto> parkingDto;
	private int totalTime;

}
