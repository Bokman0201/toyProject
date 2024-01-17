package com.hs.toy02.vo;

import java.util.List;

import com.hs.toy02.dto.ProductDto;
import com.hs.toy02.dto.TagDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVO {
	private ProductDto productDto;
	private List<String> tagList;

}
