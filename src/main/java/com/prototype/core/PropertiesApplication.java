package com.prototype.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class PropertiesApplication {
	@Getter
	@Value("${spring.application.name}")
	private String appName;	
}
