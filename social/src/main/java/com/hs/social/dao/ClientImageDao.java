package com.hs.social.dao;

import com.hs.social.dto.AttachDto;

public interface ClientImageDao {

	void addImage(int attachNo, String clientEmail);

	AttachDto findImg(String clientEmail);

}
