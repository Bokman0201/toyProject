package com.hs.social.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "custom.fileupload")
@Component
public class FileUploadProperties {
	private String home;
}
