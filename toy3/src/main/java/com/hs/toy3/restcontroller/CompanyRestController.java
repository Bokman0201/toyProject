package com.hs.toy3.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.toy3.dao.ClientDao;
import com.hs.toy3.dao.CompanyDao;
import com.hs.toy3.dto.ClientDto;
import com.hs.toy3.dto.CompanyDto;
import com.hs.toy3.vo.CompanyJoinVO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/company")
@CrossOrigin
@Slf4j
@Tag(name = "company")
public class CompanyRestController {
	
	@Autowired 
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private CompanyDao companyDao;
	@Autowired
	private ClientDao clientDao;
	
	@PostMapping("/companyJoin")
	public ResponseEntity<?> companyJoin(@RequestBody CompanyJoinVO companyJoinVO){
		
		long companyId = companyDao.sequence();
		
		CompanyDto companyDto = companyJoinVO.getCompanyDto();
		companyDto.setCompanyId(companyId);
		companyDao.addCompany(companyDto);
		log.debug("companyDto={}", companyDto);

		if(companyDto != null) {
			ClientDto clientDto = companyJoinVO.getClientDto();
			
			log.debug("client={}",clientDto);
			
			String originPw = clientDto.getClientPw();
			
			String encodedPw = encoder.encode(originPw);
			
			log.debug(encodedPw);
			
			clientDto.setClientPw(encodedPw);
			
			clientDto.setCompanyId(companyId);
			
			
			clientDao.addClient(clientDto);
			
			companyDao.updateHost(companyDto.getCompanyId(), clientDto.getClientEmail());
			log.debug("clientDto={}", clientDto);

		}
		
		return ResponseEntity.ok(null);
		

		
		
	}
	
	@GetMapping("/findClient/{clientEmail}")
	public ResponseEntity<?> findClient(@PathVariable String clientEmail){
		
		ClientDto clientDto = clientDao.findClient(clientEmail);
		
		if(clientDto != null) {
			return ResponseEntity.notFound().build();
		}
		else {
			return ResponseEntity.ok(200);
		}
	}
	
	@GetMapping("/findCompany/{companyId}")
	public CompanyDto findCompany(@PathVariable long companyId) {
		
		return companyDao.selectOne(companyId);
	}
}
