package com.hs.toy02.vo;

import java.util.List;

import com.hs.toy02.dto.ProductAttrDto;
import com.hs.toy02.dto.ProductDetailDto;
import com.hs.toy02.dto.ProductDto;
import com.hs.toy02.dto.ProductInventoryDto;
import com.hs.toy02.dto.TagDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductVO {
	
	
	private ProductDto productDto;
	private List<String> tagList;
	private ImageVO imageVO;
	private ProductDetailDto productDetailDto;
	private int productNo;
	//private String[] productAttrSize;
	private int productAttrNo;
	
	private List<DataRequestVO> dataRequestVO;
	
	
	//private int productInventoryCount[];

}
