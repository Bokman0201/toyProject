package com.hs.toy3.dao;

import com.hs.toy3.dto.EmailAuthDto;

public interface EmailAuthDao {

	void insertAuth(EmailAuthDto emailAuthDto);

	void deleteAuth(String email);

	EmailAuthDto findAuth(String email);

}
