package com.hs.toy02.restcontroller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.toy02.dao.AttachDao;
import com.hs.toy02.dto.AttachDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/attach")
@CrossOrigin
@Tag(name = "attach")
@Slf4j
public class AttachRestController {

	
	@Autowired
	private AttachDao attachDao;
	
	@GetMapping("/getImage/{productNo}")
	public ResponseEntity<Map<Integer, String>> downloadProfileImages(@PathVariable int productNo) throws IOException {
		
		List<AttachDto> attachList = attachDao.getImageList(productNo);
		log.debug("list={}", attachList);
		
		if(attachList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Map<Integer, String> imageContentMap = new HashMap<>();
		
		////여기부터 다시 하면됨
		
		for(AttachDto attachDto : attachList) {
	        File target = new File(System.getProperty("user.home"), "toy2upload/" + attachDto.getAttachNo());
	        
	        byte[] data = Files.readAllBytes(target.toPath());
	        String base64Encoded = Base64.getEncoder().encodeToString(data);
	        
	        imageContentMap.put(attachDto.getAttachNo(),base64Encoded);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		
		
	    return new ResponseEntity<Map<Integer, String>>(imageContentMap, headers, HttpStatus.OK);
	
	}

	
}
