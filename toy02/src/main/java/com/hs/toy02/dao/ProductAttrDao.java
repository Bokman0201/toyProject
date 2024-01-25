package com.hs.toy02.dao;

import java.util.List;

import com.hs.toy02.dto.ProductAttrDto;

public interface ProductAttrDao {

	int sequence();

	boolean addAttr(ProductAttrDto productAttrDto);

	int selectNo(String productAttrSize, int productNo);

	List<ProductAttrDto> getAttrList(int productNo);

}
