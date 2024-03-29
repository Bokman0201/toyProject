package com.hs.toy02.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy02.dto.ProductDto;

@Repository
public class ProductDaoImpl implements ProductDao{

	@Autowired
	private SqlSession sqlSession;
	@Override
	public int sequence() {
		return sqlSession.selectOne("product.sequence");
	}

	@Override
	public void addProduct(ProductDto productDto) {
		sqlSession.insert("product.addProduct", productDto);
	}
	
	@Override
	public List<ProductDto> getProductList() {
		return sqlSession.selectList("productList");
	}

	@Override
	public ProductDto selectOne(int productNo) {
		return sqlSession.selectOne("product.selectOne",productNo);
	}
}
