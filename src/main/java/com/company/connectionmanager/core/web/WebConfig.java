package com.company.connectionmanager.core.web;

import java.util.concurrent.TimeUnit;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Bean
	public Filter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}
	
	public static CacheControl getCacheControlMaxAge() {
		return CacheControl.maxAge(20, TimeUnit.SECONDS).cachePublic();
	}

	
}
