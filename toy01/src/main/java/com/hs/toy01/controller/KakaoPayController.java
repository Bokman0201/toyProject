package com.hs.toy01.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hs.toy01.dto.ReceiptDto;
import com.hs.toy01.service.KakaoPayService;
import com.hs.toy01.vo.KakaoPayAprroveRequestVO;
import com.hs.toy01.vo.KakaoPayReadyRequestVO;
import com.hs.toy01.vo.KakaoPayReadyResponseVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/pay")
public class KakaoPayController {
	
	@Autowired KakaoPayService service;
	
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
		
		return "redirect:success";
	}
	

}
