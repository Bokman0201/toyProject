package com.hs.social.dao;

import com.hs.social.dto.AttachDto;

public interface AttachDao {

	int nextNumber();

	void insert(AttachDto attachDto);

}
