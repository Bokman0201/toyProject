package com.hs.sockjs01.dao;

import java.util.List;

import com.hs.sockjs01.dto.UsersDto;

public interface UsersDao {

	void addUser(UsersDto usersDto);

	List<UsersDto> userList();

	String getPw(String userId);

	UsersDto selectOne(String userId);

}
