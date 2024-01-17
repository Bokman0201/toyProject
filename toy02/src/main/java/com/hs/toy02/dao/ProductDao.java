package com.hs.toy02.dao;

import com.hs.toy02.dto.ProductDto;

public interface ProductDao {

	int sequence();

	void addProduct(ProductDto productDto);

}
