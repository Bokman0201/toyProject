package com.hs.toy02.dao;

import java.util.List;

import com.hs.toy02.dto.ProductDto;

public interface ProductDao {

	int sequence();

	void addProduct(ProductDto productDto);

	List<ProductDto> getProductList();

}
