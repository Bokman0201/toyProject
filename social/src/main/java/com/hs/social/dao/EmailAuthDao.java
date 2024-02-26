package com.hs.social.dao;

import com.hs.social.dto.EmailAuthDto;

public interface EmailAuthDao {

	void insertAuth(EmailAuthDto emailAuthDto);

	void deleteAuth(String email);

	EmailAuthDto findAuth(String email);

}
