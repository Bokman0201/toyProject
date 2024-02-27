package com.hs.social.restcontroller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.social.dao.ClientImageDao;
import com.hs.social.dto.AttachDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/attach")
@CrossOrigin
@Slf4j
@Tag(name = "attach")
public class AttachRestController {
	@Autowired
	private ClientImageDao clientImageDao;

	@GetMapping("/clientImg/{clientEmail}")
	public ResponseEntity<ByteArrayResource> getClientImg(@PathVariable String clientEmail)  throws IOException{
		AttachDto attachDto = clientImageDao.findImg(clientEmail);
		
		String home = System.getProperty("user.home");
		File dir = new File(home, "social");
		File target = new File(dir, String.valueOf(attachDto.getAttachNo()));

//					System.out.println("파일크기 = " + target.length());

		byte[] data = FileUtils.readFileToByteArray(target);// 실제 파일 정보 불러오기
//					System.out.println("파일크기2 = " + data.length);
//					System.out.println("파일크기3 = " + attachDto.getAttachSize());
		ByteArrayResource resource = new ByteArrayResource(data);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_ENCODING, StandardCharsets.UTF_8.name())
				.contentLength(attachDto.getAttachSize())
				.header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
						.filename(attachDto.getAttachName(), StandardCharsets.UTF_8).build().toString())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);	}
	

}
