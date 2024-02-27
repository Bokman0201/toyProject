package com.hs.social.restcontroller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hs.social.dao.AttachDao;
import com.hs.social.dao.ClientImageDao;
import com.hs.social.dto.AttachDto;
import com.hs.social.vo.ClientImgVO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/clientImage")
@CrossOrigin
@Slf4j
@Tag(name = "clientImage")
public class ClientImageRestController {
	//attachDao
	@Autowired
	private AttachDao attachDao;
	//clientImageDao
	@Autowired
	private ClientImageDao clientImageDao;

	@PostMapping(value = "updateImg", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
	public ResponseEntity<?> updateImg(@ModelAttribute ClientImgVO clientImgVO) throws IllegalStateException, IOException {
		//attach에 넣고 
		
		int attachNo = attachDao.nextNumber();
		log.debug("no={}",attachNo);
		MultipartFile attach = clientImgVO.getAttach();

		String home = System.getProperty("user.home");
		File dir = new File(home, "social");
		dir.mkdirs();
		File target = new File(dir, String.valueOf(attachNo));
		attach.transferTo(target);
		
		AttachDto attachDto = new AttachDto();
		attachDto.setAttachNo(attachNo);
		attachDto.setAttachName(attach.getOriginalFilename());
		attachDto.setAttachSize(attach.getSize());
		attachDto.setAttachType(attach.getContentType());
		attachDao.insert(attachDto);
		
		//log.debug("memberDto={}",memberProfileDto);
		//번호 가져오고 
		//이미지랑 연결하고 
		//
		clientImageDao.addImage(attachNo,clientImgVO.getClientEmail());
		
		return ResponseEntity.ok(attachNo);

	}
}
