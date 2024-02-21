package com.hs.toy3.dao;

import java.time.LocalDate;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy3.dto.AttendDto;

@Repository
public class AttendDaoImpl implements AttendDao{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public AttendDto findForCheckIn(LocalDate currentDate, String clientEmail) {
		Map<String, Object> params = Map.of("currentDate",currentDate,"clientEmail",clientEmail);
		return sqlSession.selectOne("attend.findAttend",params);
	}

	@Override
	public void insertAttend(String clientEmail) {
		sqlSession.insert("attend.insertAttend",clientEmail);
	}

}
