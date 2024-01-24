package com.hs.toy02.dao;

import java.util.List;

import com.hs.toy02.dto.AttachDto;
import com.hs.toy02.dto.ProductImageDto;

public interface AttachDao {

	int sequence();

	void addImage(AttachDto attachDto);

	void addConnecter(ProductImageDto productImageDto);

	List<AttachDto> getImageList(int productNo);

}
