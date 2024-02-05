package com.hs.sockjs01.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.sockjs01.dao.UsersDao;
import com.hs.sockjs01.dto.UsersDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@CrossOrigin
@Slf4j
@Tag(name = "user")
public class UserRestController {
	@Autowired
	private UsersDao usersDao;
	@Autowired 
	private BCryptPasswordEncoder encoder;
	
	@PostMapping("/userJoin")
	public void userJoin(@RequestBody UsersDto usersDto){
		String originPw = usersDto.getUserPw();
		String encodePw = encoder.encode(originPw);
		usersDto.setUserPw(encodePw);
		log.debug("encode={}",encodePw);
		usersDao.addUser(usersDto);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> memberLogin(@RequestBody UsersDto usersDto){
			
		String inputPw = usersDto.getUserPw();
		String dbPw = usersDao.getPw(usersDto.getUserId());
		
		log.debug(dbPw);
		
		
		boolean result = encoder.matches(inputPw, dbPw);
		
		if(result) {
			UsersDto findDto = usersDao.selectOne(usersDto.getUserId());
			
			return ResponseEntity.ok(findDto);
		}
		else {
			return ResponseEntity.notFound().build();
		}
		
	}
	

	
	@GetMapping("/userList")
	public List<UsersDto> userList (){
		log.debug("try");
		return usersDao.userList();
	}

}
