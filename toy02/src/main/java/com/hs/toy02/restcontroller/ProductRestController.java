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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hs.toy02.dao.AttachDao;
import com.hs.toy02.dao.ProductAttrDao;
import com.hs.toy02.dao.ProductDao;
import com.hs.toy02.dao.ProductDetailDao;
import com.hs.toy02.dao.ProductInventoryDao;
import com.hs.toy02.dao.TagDao;
import com.hs.toy02.dto.AttachDto;
import com.hs.toy02.dto.ProductAttrDto;
import com.hs.toy02.dto.ProductDetailDto;
import com.hs.toy02.dto.ProductDto;
import com.hs.toy02.dto.ProductImageDto;
import com.hs.toy02.dto.ProductInventoryDto;
import com.hs.toy02.dto.TagDto;
import com.hs.toy02.dto.TaggedProductDto;
import com.hs.toy02.service.AttachService;
import com.hs.toy02.vo.DataRequestVO;
import com.hs.toy02.vo.ImageVO;
import com.hs.toy02.vo.ProductDetailVO;
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
	@Autowired 
	private ProductAttrDao productAttrDao;
	@Autowired
	private ProductInventoryDao productInventoryDao;

	
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
		try {
		    // 상품 추가
		    productDto.setProductNo(productNo);
		    productDao.addProduct(productDto);
		    
		    
		    ProductDto findDto = productDao.selectOne(productNo);

		    // 원하는 작업 추가: 예시로 로그 출력
		    

		    // 상품 설명 추가
		    ProductDetailDto productDetailDto = productVO.getProductDetailDto();
		    productDetailDto.setProductNo(productNo);
		    productDetailDao.addProductDetail(productDetailDto);
		    log.debug("Product added successfully with dto: {}", productDetailDto);
		    // 사이즈 및 재고 추가
		    List<DataRequestVO> requestList = productVO.getDataRequestVO();

		    for (DataRequestVO data : requestList) {
		        int productAttrNo = productAttrDao.sequence();
			    log.debug("dto!!!={}", findDto);
			    int proNo = findDto.getProductNo();
		        log.debug("no={}",productNo);

		        String productAttrSize = data.getProductAttrSize();
		        int productInventoryCount = data.getProductInventoryCount();
		        int productAttrPrice = data.getProductAttrPrice();

		        // 각각의 루프에서 새로운 객체 생성
		        ProductAttrDto productAttrDto = new ProductAttrDto();
		        ProductInventoryDto productInventoryDto = new ProductInventoryDto();

		        productAttrDto.setProductNo(proNo);
		        productAttrDto.setProductAttrNo(productAttrNo);
		        productAttrDto.setProductSize(productAttrSize);
		        productAttrDto.setProductAttrPrice(productAttrPrice);
		        boolean result = productAttrDao.addAttr(productAttrDto);
		        log.debug("result={}",result);
		        if(result) {
		        	int no = productAttrDao.selectNo(productAttrSize,productNo);
		        	productInventoryDto.setProductAttrNo(no);
		        	productInventoryDto.setProductInventoryCount(productInventoryCount);
		        	productInventoryDao.addInventory(productInventoryDto);
		        	
		        }
		    }

		    // 트랜잭션 커밋

		} catch (Exception e) {
		    // 트랜잭션 롤백 및 예외 처리
		    // 예외 처리는 상황에 따라 로깅, 사용자에게 알림 등을 포함할 수 있습니다.
		    log.error("Error during transaction", e);
		    // 롤백 로직 추가
		} finally {
		    // 트랜잭션 종료 또는 리소스 해제
		}
	

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
	
	//상품 상세 조회용
	@GetMapping("/productDetail/{productNo}")
	public ProductDetailVO productDetail(@PathVariable int productNo) {
		
		return productDetailDao.productDetail(productNo);
	}
	
	
	//attr 조회용
	@GetMapping("/productAttr/{productNo}")
	public List<ProductAttrDto> getAttrList(@PathVariable int productNo){
		return productAttrDao.getAttrList(productNo);
	}
	
	@GetMapping("/productInventory/{productAttrNo}")
	public  ProductInventoryDto getInventory(@PathVariable int productAttrNo) {
		return productInventoryDao.getInventory(productAttrNo);
	}
	
	
}
