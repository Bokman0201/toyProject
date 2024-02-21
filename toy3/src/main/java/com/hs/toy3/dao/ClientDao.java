package com.hs.toy3.dao;

import java.util.List;

import com.hs.toy3.dto.ClientDto;
import com.hs.toy3.dto.CompanyDto;

public interface ClientDao {

	void addClient(ClientDto clientDto);

	ClientDto findClient(String clientEmail);

	CompanyDto findCompany(String inviteCode);

	List<ClientDto> clientList(long companyId);

}
