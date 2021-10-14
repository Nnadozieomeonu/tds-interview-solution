package com.tds.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.function.Predicate;


@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tds.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("TDS Software engineering Interview Solution")
                .description("API Documentation for TDS Software engineering Interview")
                .termsOfServiceUrl("http://javainuse.com")
                .contact(new Contact("Nnadozie Godfrey Omeonu","https://www.timedatasecurity.com/","nnadozieome@gmail.com")).license("TDS Interview License")
                .licenseUrl("javainuse@gmail.com").version("1.0").build();
    }
}
