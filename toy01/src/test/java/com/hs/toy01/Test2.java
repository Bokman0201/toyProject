package com.hs.toy01;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.headers.Header;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class Test2 {
	//96840846b96d838d2924361acbeb4b71

	@Test
	public void test() throws URISyntaxException {
		
		//변하는 정보 중요정보 분리 모듈화
		
		//결제 마다 변하는 정보 - 상품명 가격 주문번호 구매자ID
		
		String itemName = "노트북";
		long itemPrice = 100000;
		String partnerOrderId = UUID.randomUUID().toString();
		String partnerUserId = "testuser1"; 
		
		
		//[1]restTemplate
		//[2] WebClient
		
		//전송도구 생성
		RestTemplate template = new RestTemplate();
		
		//주소설정
		URI uri = new URI("https://kapi.kakao.com/v1/payment/ready");
		//헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK 96840846b96d838d2924361acbeb4b71");
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		//바디 설정
		MultiValueMap<String , String > body = new LinkedMultiValueMap<>();
		
		body.add("cid", "TC0ONETIME");
		body.add("partner_order_id", partnerOrderId);
		body.add("partner_user_id", partnerUserId);
		body.add("item_name", itemName);
		body.add("quantity", String.valueOf(itemPrice));//수량 영원히 바꾸지 않음
		body.add("total_amount", "3000");//판매금액
		body.add("tax_free_amount", "0");//비과세 면세상품
		body.add("approval_url", "http://localhost:3000");
		body.add("cancel_url", "http://localhost:3000");
		body.add("fail_url", "http://localhost:3000");


		//요청 발송(Entity)
		HttpEntity entity = new HttpEntity(body, headers);
		
		Map response =  template.postForObject(uri, entity, Map.class);
		
		log.debug("res={}",response.get("next_redirect_pc_url"));
		
	}

}
