package com.hs.toy3.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy3.dto.CompanyDto;

@Repository
public class CompanyDaoImpl implements CompanyDao{
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public long sequence() {
		return sqlSession.selectOne("company.sequence");
	}
	
	@Override
	public void addCompany(CompanyDto companyDto) {
		sqlSession.insert("company.addCompany", companyDto);
	}

	@Override
	public void updateHost(long companyId, String clientEmail) {
		Map<String, Object> params = Map.of("companyId",companyId,"clientEmail",clientEmail);
		sqlSession.update("company.updateHost",params);
	}
	@Override
	public CompanyDto selectOne(long companyId) {
		return sqlSession.selectOne("company.findCompany",companyId);
	}
}
