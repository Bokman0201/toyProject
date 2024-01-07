package com.hs.toy01.restcontroller;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.toy01.dao.ParkingDao;
import com.hs.toy01.dto.ParkingDto;
import com.hs.toy01.dto.ReceiptDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/home")
@Slf4j
@CrossOrigin
public class HomeRestController {
	@Autowired private ParkingDao dao;
	
	
	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody ParkingDto dto){
		
		dao.insert(dto);
		return ResponseEntity.ok("ok");
		
	}
	
	@GetMapping("/list")
	public List<ParkingDto> allList(){
		return dao.allList();
	}
	@GetMapping("/searchList/{vehicleNo}")
	public List<ParkingDto> searchList(@PathVariable String vehicleNo){
		
		return dao.searchList(vehicleNo);
	}
	
	@GetMapping("/computeTime/{vehicleNo}")
	public ReceiptDto computeTime(@PathVariable String vehicleNo) {
		
		ParkingDto selectDto = dao.selectOne(vehicleNo);

		String enterDate = selectDto.getEnterDate();
		LocalDateTime enterDateTime = LocalDateTime.parse(enterDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		LocalDateTime nowDate = LocalDateTime.now();

		Duration duration = Duration.between(enterDateTime, nowDate);
		
		long days = 0;
		long hours = duration.toHours();  // 총 시간을 시간 단위로 변환
		long minutes = duration.toMinutes() % 60;  // 남은 분 계산

		long rate = 0;
		boolean free = hours<1&& minutes <=30 && minutes<=10;
		boolean tenOver = hours<1&& minutes <=30;
		boolean basicOver = hours<24&&hours<24 && minutes>30;
		boolean dayOver = hours>=24;
		
		long totalMin = hours * 60 + minutes;
		
		int addRate = 1000;
		
		if(free) {
			rate = 0;
		}
		else if(tenOver) {
			rate = 5000;
		}
		else if (basicOver) {
		    int basic = 5000;
		    long hourToMin = (hours * 60 + minutes) - 30; 
		    
		    int additionalCharges = (int) (hourToMin / 10) * addRate;

		    rate = basic + additionalCharges;
		}
		else if(dayOver) {
			long hourRate = 50000;
			
			long totalHour = totalMin / 60;
			days = totalHour / 24;
			hours = totalHour % 24;
			minutes = totalMin % 60;

			rate = hourRate*days + (((hours * 60) + minutes)/10)*addRate;
		}
		
		ReceiptDto receiptDto = ReceiptDto.builder()
				.vihecleNo(selectDto.getVehicleNo())
				.days(days)
				.hour(hours)
				.minutes(minutes)
				.price(rate)
				.build();
		
		return receiptDto;
		
	}

}
