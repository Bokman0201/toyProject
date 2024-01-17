package com.hs.toy01.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hs.toy01.configuration.KakaoPayProperties;
import com.hs.toy01.vo.KakaoPayReadyRequestVO;
import com.hs.toy01.vo.KakaoPayReadyResponseVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoPayServiceImpl implements KakaoPayService {
	@Autowired private KakaoPayProperties kakaoPayProperties;
	@Override
		public KakaoPayReadyResponseVO ready(KakaoPayReadyRequestVO requestVO) throws Exception {
		//전송도구 생성
		RestTemplate template = new RestTemplate();
		
		//Snake_case를 CaMelCase로
		
		
		//주소설정
		URI uri = new URI("https://kapi.kakao.com/v1/payment/ready");
		//헤더 설정
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK "+kakaoPayProperties.getKey());
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		//바디 설정
		MultiValueMap<String , String > body = new LinkedMultiValueMap<>();
		
		body.add("cid", kakaoPayProperties.getCid());
		body.add("partner_order_id", requestVO.getPartnerOrderId());
		body.add("partner_user_id", requestVO.getPartnerUserId());
		body.add("item_name", requestVO.getItemName());
		body.add("quantity", "1");//수량 영원히 바꾸지 않음
		body.add("total_amount", String.valueOf(requestVO.getItemPrice()));//판매금액
		body.add("tax_free_amount", "0");//비과세 면세상품

		String path = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();

		body.add("approval_url", "http://localhost:3000/200");
		body.add("cancel_url", path + "/cancel");
		body.add("fail_url", path + "/fail");

		// 요청 발송
		HttpEntity entity = new HttpEntity(body, headers);
		KakaoPayReadyResponseVO response = template.postForObject(uri, entity, KakaoPayReadyResponseVO.class);
		
		log.debug("res={}",response);

		return response;
			
		}

}
