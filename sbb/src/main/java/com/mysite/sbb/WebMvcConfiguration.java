package com.mysite.sbb;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		
		
		registry.addResourceHandler("/images/**").addResourceLocations("file:///c:/sts-workspace/sbb/src/main/resources/static/item/");
	}

}
