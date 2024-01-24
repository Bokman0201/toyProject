package com.hs.toy02.restcontroller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hs.toy02.dao.AttachDao;
import com.hs.toy02.dao.ProductDao;
import com.hs.toy02.dao.ProductDetailDao;
import com.hs.toy02.dao.TagDao;
import com.hs.toy02.dto.AttachDto;
import com.hs.toy02.dto.ProductDetailDto;
import com.hs.toy02.dto.ProductDto;
import com.hs.toy02.dto.ProductImageDto;
import com.hs.toy02.dto.TagDto;
import com.hs.toy02.dto.TaggedProductDto;
import com.hs.toy02.service.AttachService;
import com.hs.toy02.vo.ImageVO;
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
	@Autowired
	private AttachDao attachDao;
	@Autowired
	private ProductDetailDao productDetailDao;
	
	@Autowired
	private AttachService attachService; 
	
	@PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void productImage(@ModelAttribute ImageVO imageVO) throws IllegalStateException, IOException {
		
		log.debug("VO={}",imageVO);
		MultipartFile[] attaches = imageVO.getAttach();
		
		log.debug("attaches={}",imageVO);
		for(MultipartFile attach : attaches) {
			log.debug("attach={}",attach.getOriginalFilename());
		    
		    int attachNo = attachDao.sequence();

		    String home = System.getProperty("user.home");
		    File dir = new File(home, "toy2upload");
		    dir.mkdirs();
		    File target = new File(dir, String.valueOf(attachNo));
		    attach.transferTo(target);
		    
		    AttachDto attachDto = new AttachDto();
		    attachDto.setAttachNo(attachNo);
		    attachDto.setAttachName(attach.getOriginalFilename());
		    attachDto.setAttachSize(attach.getSize());
		    attachDto.setAttachType(attach.getContentType());
		    
		    attachDao.addImage(attachDto);
		    log.debug("dto={}",attachDto);
		    
		    
		    ProductImageDto productImageDto = new ProductImageDto();
		    productImageDto.setAttachNo(attachNo);
		    productImageDto.setProductNo(imageVO.getProductNo());
		    attachDao.addConnecter(productImageDto);
		    
		    
		}
		
	}

	

	@PostMapping(value = "/addProduct")
	public ResponseEntity<?> addProduct(@RequestBody ProductVO productVO)  {
		ProductDto productDto = productVO.getProductDto();
		int productNo = productDao.sequence();
		// 이미지 추가

		//상품 추가 
		productDto.setProductNo(productNo);
		productDao.addProduct(productDto);
		
		//상품 설명 추가 
		ProductDetailDto productDetailDto = productVO.getProductDetailDto();
		productDetailDto.setProductNo(productNo);
		productDetailDao.addProductDetail(productDetailDto);

		//태그 추가
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

	
	@GetMapping("/productList")
	public List<ProductDto> getProductList(){
		
		return productDao.getProductList();
	}
}
