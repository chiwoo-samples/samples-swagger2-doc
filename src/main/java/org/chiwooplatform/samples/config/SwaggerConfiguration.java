package org.chiwooplatform.samples.config;

import static springfox.documentation.builders.PathSelectors.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        Docket doclet = new Docket(DocumentationType.SWAGGER_2);
        doclet.forCodeGeneration(true);
        doclet.apiInfo(apiInfo());
        doclet.select().apis(RequestHandlerSelectors.any()).paths(forCode()).build();
        if ("TEST".equals(System.getProperty("xxxx"))) {
            doclet.select().paths(allPaths()).build();
        }
        return doclet;
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Example Open API")
                .description("Example API showing usege of Swagger for documenting Spring MVC Controller")
                .contact(new Contact("contact", "http://www.contact.com", "webmaster@contact.com")).version("1.0")
                .build();
    }

    private Predicate<String> forCode() {
        return ant("/codes/*");
    }

    private Predicate<String> allPaths() {
        return PathSelectors.any();
    }
}
