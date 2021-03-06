package com.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger Configuration.
 *
 *
 * @author Sergey Ignatyuk
 * @version 1.0
 */

@Configuration
@Import({BeanValidatorPluginsConfiguration.class})
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Transport Management System")
                .description("Spring REST API for Transport Management System")
                .version("1.0")
                .contact(new Contact("Sergey Ignatyuk", "https://github.com/SergeyIgnatyuk", "signatuk89@gmail.com"))
                .build();
    }
}
