package com.hs.toy01.restcontroller;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hs.toy01.dao.ParkingDao;
import com.hs.toy01.dto.ParkingDto;
import com.hs.toy01.dto.ReceiptDto;
import com.hs.toy01.service.KakaoPayService;
import com.hs.toy01.vo.KakaoPayAprroveRequestVO;
import com.hs.toy01.vo.KakaoPayReadyRequestVO;
import com.hs.toy01.vo.KakaoPayReadyResponseVO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
@Tag(name = "홈")
@RestController
@RequestMapping("/home")
@Slf4j
@CrossOrigin
public class HomeRestController {
	@Autowired private ParkingDao dao;
	@Autowired private KakaoPayService service;
	
	
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
		boolean basicOver = hours<24&&hours<24;
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
		    log.debug("rate={}",rate);
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
				.hours(hours)
				.minutes(minutes)
				.price(rate)
				.build();
		
		
		return receiptDto;
		
	}
	
	@PostMapping("/paymentReady")
	public String paymentReady(@RequestBody ReceiptDto receiptDto, HttpSession httpSession) throws Exception {
		
		log.debug("dto={}",receiptDto);
		
		KakaoPayReadyRequestVO requestVO = KakaoPayReadyRequestVO.builder()
				.partnerOrderId(UUID.randomUUID().toString())
				.partnerUserId(receiptDto.getVihecleNo())
				.itemName(receiptDto.getVihecleNo())
				.itemPrice((int)receiptDto.getPrice())
				.build();
		
		
		KakaoPayReadyResponseVO res = service.ready(requestVO);
		
		httpSession.setAttribute("approve", res);
		
		log.debug("session={}", httpSession.getAttribute("approve"));
		
		return res.getNextRedirectPcUrl();
		
	}
	
	@GetMapping("/success")
	public String success(HttpSession session , @RequestParam String pg_token) {
		
		KakaoPayAprroveRequestVO request = (KakaoPayAprroveRequestVO) session.getAttribute("appove");
		
		return "";
	}
	
	
	
	

}
