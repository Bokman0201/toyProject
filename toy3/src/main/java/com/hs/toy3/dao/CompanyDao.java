package com.hs.toy3.dao;

import com.hs.toy3.dto.CompanyDto;

public interface CompanyDao {

	long sequence();

	void addCompany(CompanyDto companyDto);

	void updateHost(long companyId, String clientEmail);

	CompanyDto selectOne(long companyId);


}
