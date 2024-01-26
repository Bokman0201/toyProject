package com.hs.toy02.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy02.dto.ProductInventoryDto;

@Repository
public class ProductInventoryDaoImpl implements ProductInventoryDao {
	
	@Autowired
	private SqlSession sqlSession ;
	
	
	@Override
	public void addInventory(ProductInventoryDto productInventoryDto) {
		sqlSession.insert("productInventory.addInventory",productInventoryDto);
	}


	@Override
	public ProductInventoryDto getInventory(int productAttrNo) {
		return sqlSession.selectOne("productInventory.getInventory",productAttrNo);
	}
}
