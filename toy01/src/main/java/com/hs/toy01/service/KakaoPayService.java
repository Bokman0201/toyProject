package com.hs.toy01.service;

import com.hs.toy01.vo.KakaoPayReadyRequestVO;
import com.hs.toy01.vo.KakaoPayReadyResponseVO;

public interface KakaoPayService {

	KakaoPayReadyResponseVO ready(KakaoPayReadyRequestVO requestVO) throws Exception;

}
