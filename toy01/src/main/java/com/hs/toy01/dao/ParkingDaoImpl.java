package com.hs.toy01.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy01.dto.ParkingDto;

@Repository
public class ParkingDaoImpl implements ParkingDao {

	@Autowired
	SqlSession session;

	@Override
	public void insert(ParkingDto parkingDto) {

		session.insert("parking.insert", parkingDto);
	}

	@Override
	public List<ParkingDto> allList() {
		return session.selectList("parking.allList");
	}

	@Override
	public List<ParkingDto> searchList(String vehicleNo) {
		return session.selectList("parking.searchList", vehicleNo);
	}
	@Override
	public ParkingDto selectOne(String vehicleNo) {
		return session.selectOne("parking.selectOne",vehicleNo);
	}
}
