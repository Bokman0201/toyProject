package com.hs.toy01.dao;

import java.util.List;

import com.hs.toy01.dto.ParkingDto;
import com.hs.toy01.dto.ReceiptDto;

public interface ParkingDao {

	 void insert(ParkingDto parkingDto);

	List<ParkingDto> allList();

	List<ParkingDto> searchList(String vehicleNo);

	ParkingDto selectOne(String vehicleNo);

	
	void createReceipt(ReceiptDto receiptDto);

	ReceiptDto searchReceipt(String vehicleNo);
}
