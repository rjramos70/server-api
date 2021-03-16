package com.company.connectionmanager.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.company.connectionmanager.api.exceptionhandler.Problem;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.databind.JsonNode;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
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
		
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.company.connectionmanager.api"))
					.paths(PathSelectors.any())
					.build()
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
				.globalResponseMessage(RequestMethod.POST, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.PUT, globalPostPutResponseMessages())
				.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.ignoredParameterTypes(JsonNode.class, ServletWebRequest.class)
				.apiInfo(apiInfo())
				.tags(
						new Tag("Connector", "Manage database connectors"),
						new Tag("Database", "Management of databases"),
						new Tag("Database Type", "Manage the types of database connections")
						 );
				
	}
	
	private List<ResponseMessage> globalGetResponseMessages(){
		return Arrays.asList(
				new ResponseMessageBuilder()
				.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("Internal server error")
				.responseModel(new ModelRef("Problem"))
				.build(),
			new ResponseMessageBuilder()
				.code(HttpStatus.NOT_ACCEPTABLE.value())
				.message("Resource has no representation that could be accepted by the consumer")
				.build(),
			new ResponseMessageBuilder()
				.code(HttpStatus.BAD_REQUEST.value())
				.message("Bad request")
				.responseModel(new ModelRef("Problem"))
				.build(),
			new ResponseMessageBuilder()
				.code(HttpStatus.NOT_FOUND.value())
				.message("Resource not found")
				.responseModel(new ModelRef("Problem"))
				.build()
		);
	}
	
	private List<ResponseMessage> globalPostPutResponseMessages(){
		return Arrays.asList(
				new ResponseMessageBuilder()
				.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.message("Internal server error")
				.responseModel(new ModelRef("Problem"))
				.build(),
			new ResponseMessageBuilder()
				.code(HttpStatus.NOT_ACCEPTABLE.value())
				.message("Resource has no representation that could be accepted by the consumer")
				.build(),
			new ResponseMessageBuilder()
				.code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
				.message("Unsupported Media Type")
				.responseModel(new ModelRef("Problem"))
				.build()
		);
	}
	
	private List<ResponseMessage> globalDeleteResponseMessages(){
		return Arrays.asList(
					new ResponseMessageBuilder()
						.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Internal server error")
						.responseModel(new ModelRef("Problem"))
						.build(),
					new ResponseMessageBuilder()
						.code(HttpStatus.BAD_REQUEST.value())
						.message("Bad request")
						.responseModel(new ModelRef("Problem"))
						.build(),
					new ResponseMessageBuilder()
						.code(HttpStatus.NOT_FOUND.value())
						.message("Resource not found")
						.responseModel(new ModelRef("Problem"))
						.build()
				);
	}
	
	private ApiInfo apiInfo() {
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
