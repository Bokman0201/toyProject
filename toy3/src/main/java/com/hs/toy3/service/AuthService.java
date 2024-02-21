package com.hs.toy3.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
	@Scheduled(fixedDelay = 5 * 60 * 1000)
	public void expireAuth(String email) {
		//email받아와서 파기
	}

}
