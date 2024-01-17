package com.hs.toy01;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.hs.toy01.configuration.KakaoPayProperties;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class Test4 {
@Autowired private KakaoPayProperties kakaoPayProperties;

@Test
public void test() throws URISyntaxException {
	RestTemplate template = new RestTemplate();

	URI uri = new URI("https://kapi.kakao.com/v1/payment/approve");
	
	HttpHeaders headers = new HttpHeaders();
	headers.add("Authorization", "KakaoAK "+kakaoPayProperties.getKey());
	headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

	
	MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
	body.add("cid", kakaoPayProperties.getCid());
	body.add("tid", "T59bce451e9c44019e86");
	body.add("partner_order_id", "91e394f2-fb53-4c88-ab86-836b7a1be5c6");
	body.add("partner_user_id", "testuser1" );
	body.add("pg_token", "50e55d5d7d9f1a5608db");

	HttpEntity entity = new HttpEntity(body,headers);
	
	
	Map response = template.postForObject(uri, entity, Map.class);
	log.debug("response={}",response);
	
}
}
