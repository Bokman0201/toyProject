package com.hs.social.dao;

import com.hs.social.dto.ClientDto;

public interface ClientDao {

	void clientSignin(ClientDto clientDto);

	ClientDto findClient(String clientEmail);

}
