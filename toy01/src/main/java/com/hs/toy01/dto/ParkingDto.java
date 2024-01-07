package com.hs.toy01.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ParkingDto {
	private int parkingNo;
	private String vehicleNo;
	private String enterDate;
	private String seasonTicket;

}
