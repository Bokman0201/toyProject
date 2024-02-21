package com.hs.toy3.dao;

import java.time.LocalDate;

import com.hs.toy3.dto.AttendDto;

public interface AttendDao {

	AttendDto findForCheckIn(LocalDate currentDate, String clientEmail);

	void insertAttend(String clientEmail);

}
