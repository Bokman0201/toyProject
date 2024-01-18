package com.hs.toy02.dao;

import java.util.List;

import com.hs.toy02.dto.CategoryDto;

public interface CategoryDao {

	void addCategory(CategoryDto categoryDto);

	List<CategoryDto> categoryList();

	List<CategoryDto> childCategory(int parentCategoryId);

}
