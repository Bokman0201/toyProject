package com.hs.toy02.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.toy02.dao.ProductDao;
import com.hs.toy02.dao.TagDao;
import com.hs.toy02.dto.ProductDto;
import com.hs.toy02.dto.TagDto;
import com.hs.toy02.dto.TaggedProductDto;
import com.hs.toy02.vo.ProductVO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "product")
@CrossOrigin
@RequestMapping("/product")
@Slf4j
@RestController
public class ProductRestController {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private TagDao tagDao;

	

	@PostMapping("/addProduct")
	public ResponseEntity<?> addProduct(@RequestBody ProductVO productVO) {
		// 이미지 추가
		// ProductImages
		// 이미지에 대표이미지항목 생성
		// 대표이미지가 1 인것이 최 상단에 렌더링 되도록 설정

		// 이미지 connector추가

		// 상품추가
		ProductDto productDto = productVO.getProductDto();
		int productNo = productDao.sequence();

		productDto.setProductNo(productNo);

		productDao.addProduct(productDto);

		// 상품 설명 추가
		// ProductDetail

		// 상품 테그 추가

		List<String> list = productVO.getTagList();
		log.debug("list={}",list);
		List<String> allTagList = tagDao.allList();
		log.debug("alllist={}",allTagList);

		// 리스트에 들어온 이름으로 select해서 같은게 있으면 그번호 저장 없으면 tag에 먼저 저장후 번호를 가져와서 저장
		if (!list.isEmpty()) {
			for (String tag : list) {
				if (!allTagList.contains(tag)) {
					int tagNo = tagDao.sequence();
					tagDao.addTag(TagDto.builder().tagName(tag).tagNo(tagNo).build());
					// connector
				} 
				int tagNo = tagDao.findNo(tag);
				tagDao.insertConnect(TaggedProductDto.builder().tagNo(tagNo).productNo(productNo)
						.build());
			}
		}
		
		return ResponseEntity.ok(productNo);

		

	}

}
