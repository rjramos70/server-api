package com.company.connectionmanager.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig implements WebMvcConfigurer{
	
	private final String API_DESCRIPTION = "APIs that enable the ability to record multiple "
			+ "connections to different databases and display their respective data and structures.";
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.company.connectionmanager.api"))
					.paths(PathSelectors.any())
					// .paths(PathSelectors.ant("/databases/*"))
					.build()
					.apiInfo(apiInfo())
					.tags(
							new Tag("Connector", "Manage database connectors"),
							new Tag("Database", "Management of databases"),
							new Tag("Database Type", "Manage the types of database connections")
						  );
				
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Connect Manager API Documentation")
				.description(API_DESCRIPTION)
				.version("1.0")
				.contact(new Contact("Renato Ramos", "https://www.linkedin.com/in/rjramos70/", "rjramos70@gmail.com"))
				.build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
		
	}

}
