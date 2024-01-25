package com.hs.toy02.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy02.dto.ProductAttrDto;

@Repository
public class ProductAttrDaoImpl implements ProductAttrDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int sequence() {
		return sqlSession.selectOne("productAttr.sequence");
	}

	@Override
	public boolean addAttr(ProductAttrDto productAttrDto) {

		return sqlSession.insert("productAttr.addAttr", productAttrDto) > 0;
	}

	@Override
	public int selectNo(String productAttrSize, int productNo) {
		Map<String, Object> params = Map.of("productSize",productAttrSize , "productNo", productNo);
		
		System.out.println("params = ===="+params);
		return sqlSession.selectOne("productAttr.selectNo",params);
	}

	@Override
	public List<ProductAttrDto> getAttrList(int productNo) {
		return sqlSession.selectList("productAttr.getAttrList",productNo);
	}

}
