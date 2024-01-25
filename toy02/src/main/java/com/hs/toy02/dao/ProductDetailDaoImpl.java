package com.hs.toy02.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy02.dto.ProductDetailDto;
import com.hs.toy02.vo.ProductDetailVO;

@Repository
public class ProductDetailDaoImpl implements ProductDetailDao{
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void addProductDetail(ProductDetailDto productDetailDto) {
		sqlSession.insert("productDetail.insertProductDetail",productDetailDto);
	}
	
	@Override
	public ProductDetailVO productDetail(int productNo) {
		return sqlSession.selectOne("productDetail.productDetail",productNo);
	}

}
