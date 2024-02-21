package com.hs.toy3.vo;

import com.hs.toy3.dto.ClientDto;
import com.hs.toy3.dto.CompanyDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CompanyJoinVO {

	private CompanyDto companyDto;
	private ClientDto clientDto;
}
