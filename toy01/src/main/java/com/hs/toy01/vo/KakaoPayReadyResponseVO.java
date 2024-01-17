package com.hs.toy01.vo;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class KakaoPayReadyResponseVO {
	private String tid;//결제 고유번호
	private String nextRedirectAppUrl;//모바일용 결제 주소
	private String nextRedirectMobileUrl;//모바일 웹용 결제 페이지주소
	private String nextRedirectPcUrl;//pc 웹용 결제 페이지 주소
	private String androidAppScheme;//카카오페이 결제 를위한 안드로이드 스키마
	private String iosAppScheme;//ios스키마
	private Date createdAt;//결제 준비를 요청한시각
	
}
