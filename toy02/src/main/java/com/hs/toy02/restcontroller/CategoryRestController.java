package com.hs.toy02.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.toy02.dao.CategoryDao;
import com.hs.toy02.dto.CategoryDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
@Tag(name = "category")
@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/category")
public class CategoryRestController {
	
	@Autowired
	private CategoryDao categoryDao;
	
	@PostMapping("/addCategory")
	public void addCategory(@RequestBody CategoryDto categoryDto) {
		log.debug("dto={}", categoryDto);
		
		categoryDao.addCategory(categoryDto);
	}
	@GetMapping("/categoryList")
	public List<CategoryDto> categoryList(){
		
		return categoryDao.categoryList();
	}
	
	@GetMapping("/childCategory/{parentCategoryId}")
	public List<CategoryDto> childCategory(@PathVariable int parentCategoryId){
		
		return categoryDao.childCategory(parentCategoryId);
	}

}
