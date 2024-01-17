package com.hs.toy02.dao;

import java.util.List;

import com.hs.toy02.dto.TagDto;
import com.hs.toy02.dto.TaggedProductDto;

public interface TagDao {

	int sequence();

	void insertConnect(TaggedProductDto taggedProductDto);

	List<String> allList();

	void addTag(TagDto tagDto);

	int findNo(String tag);

}
