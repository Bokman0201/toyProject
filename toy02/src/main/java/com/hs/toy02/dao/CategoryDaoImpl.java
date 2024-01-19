package com.hs.toy02.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.toy02.dto.CategoryDto;

@Repository
public class CategoryDaoImpl implements CategoryDao{

	@Autowired
	private SqlSession sqlSession;
	@Override
	public void addCategory(CategoryDto categoryDto) {
		sqlSession.insert("category.addCategory", categoryDto);		
	}
	@Override
	public List<CategoryDto> categoryList() {
		return sqlSession.selectList("category.categoryList");
	}
	@Override
	public List<CategoryDto> childCategory(int parentCategoryId) {
		return sqlSession.selectList("category.childCategory",parentCategoryId);
	}
	@Override
	public List<CategoryDto> selectedCategory(int categoryId) {
		return sqlSession.selectList("category.selectedCategory", categoryId);
	}

}
