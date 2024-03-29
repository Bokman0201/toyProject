package com.hs.social.restcontroller;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.social.dao.ClientDao;
import com.hs.social.dao.EmailAuthDao;
import com.hs.social.dto.ClientDto;
import com.hs.social.dto.EmailAuthDto;
import com.hs.social.vo.LoginRequestVO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/client")
@CrossOrigin
@Slf4j
@Tag(name = "클라이언트")
public class ClientRestController {
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private ClientDao clientDao;

	@Autowired
	private JavaMailSender sender;
	@Autowired
	private EmailAuthDao emailAuthDao;

	@PostMapping("/signin")
	public ResponseEntity<?> clientSignin(@RequestBody ClientDto clientDto) {

		ClientDto findDto = clientDao.findClient(clientDto.getClientEmail());

		if (findDto != null)
			return ResponseEntity.badRequest().build();

		String clientPw = clientDto.getClientPw();
		String encodePw = encoder.encode(clientPw);
		clientDto.setClientPw(encodePw);
		clientDao.clientSignin(clientDto);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/EmailAuthentication/{email}")
	public ResponseEntity<?> EmailAuthentication(@PathVariable String email) throws MessagingException, IOException {
		ClientDto findDto = clientDao.findClient(email);

		if (findDto != null)
			return ResponseEntity.badRequest().build();

		MimeMessage messeage = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(messeage, false, "UTF-8");

		helper.setTo(email);
		helper.setSubject("인증번호");
		// classpath에 있는 template 폴더의 email.html을 불러오는 코드
		ClassPathResource resource = new ClassPathResource("templates/email2.html");// 자원선택
		File target = resource.getFile();// 파일추출
		Scanner sc = new Scanner(target);// 읽을 수 있는 도구 연결
		StringBuffer buffer = new StringBuffer();// 저장할 수 있는 버퍼 생성

		while (sc.hasNextLine()) {// 더 읽을 줄이 있다면
			buffer.append(sc.nextLine());// 읽어서 버퍼에 추가
		}

		sc.close();

		String text = buffer.toString();// 결과를 1차 저장
		// 해석
		Document doc = Jsoup.parse(text);
		Element who = doc.getElementById("who");
		Element Authentication = doc.getElementById("Authentication");

		who.text("고객님");
		UUID uuid = UUID.randomUUID();

		// UUID를 8자리 숫자로 축약
		String authenticationNum = uuid.toString().replaceAll("-", "").substring(0, 8);

		Authentication.text(authenticationNum);

		Element link = doc.getElementById("link");
		link.attr("href", "https://www.google.com");// 가져온 태그의 href속성을 변경해라

		helper.setText(doc.toString(), true);

		EmailAuthDto emailAuthDto = new EmailAuthDto();
		emailAuthDto.setAuthenticationText(authenticationNum);
		emailAuthDto.setClientEmail(email);
		emailAuthDao.insertAuth(emailAuthDto);

		log.debug("dto = {}", emailAuthDto);
		sender.send(messeage);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/isMatchAuth/{email}/{code}")
	public ResponseEntity<?> isMatchAuth(@PathVariable String email, @PathVariable String code) {
		EmailAuthDto emailAuthDto = emailAuthDao.findAuth(email);

		if (emailAuthDto == null)
			ResponseEntity.notFound().build();

		String origin = emailAuthDto.getAuthenticationText();
		if (origin.equals(code)) {
			return ResponseEntity.ok(200);
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping("/findClient/{clientEmail}")
	public ClientDto fingClient(@PathVariable String clientEmail) {
		return clientDao.findClient(clientEmail);
	}

	@DeleteMapping("/deleteAuth/{email}")
	public void deleteAuth(@PathVariable String email) {
		emailAuthDao.deleteAuth(email);
	}

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody LoginRequestVO requestVO) {

		ClientDto findDto = clientDao.findClient(requestVO.getLoginId());

		String encodePw = findDto.getClientPw();
		String inputPw = requestVO.getLoginPw();
		
		boolean result = encoder.matches(inputPw,encodePw);
		
		//log.debug("result ={}", result);

		if (!result) {
			return ResponseEntity.notFound().build();
		}
		
		
		findDto.setClientPw(null);
		return ResponseEntity.ok(findDto);
	}
	
	

}
