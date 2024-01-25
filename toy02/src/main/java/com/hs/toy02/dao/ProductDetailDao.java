package com.hs.toy02.dao;

import com.hs.toy02.dto.ProductDetailDto;
import com.hs.toy02.vo.ProductDetailVO;

public interface ProductDetailDao {

	void addProductDetail(ProductDetailDto productDetailDto);

	ProductDetailVO productDetail(int productNo);

}
