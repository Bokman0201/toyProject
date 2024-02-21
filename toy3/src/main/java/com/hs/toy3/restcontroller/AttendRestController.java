package com.hs.toy3.restcontroller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.toy3.dao.AttendDao;
import com.hs.toy3.dto.AttendDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/attend")
@CrossOrigin
@Slf4j
@Tag(name = "Attend")
public class AttendRestController {

	@Autowired
	private AttendDao attendDao;
	
    LocalDate currentDate = LocalDate.now();

	@PostMapping("/checkIn/{clientEmail}")
	public ResponseEntity<?> checkIn(@PathVariable String clientEmail){
		
        log.debug("date={}",currentDate);
		
		AttendDto findAttend = attendDao.findForCheckIn(currentDate,clientEmail);
		
		if(findAttend == null) {
			attendDao.insertAttend(clientEmail);
			return ResponseEntity.ok(null);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/getAttend/{clientEmail}")
	public AttendDto getAttend(@PathVariable String clientEmail) {
		AttendDto findAttend = attendDao.findForCheckIn(currentDate,clientEmail);
        log.debug("date={}",currentDate);
		log.debug("dto={}",findAttend);
		return findAttend;
	}
}
