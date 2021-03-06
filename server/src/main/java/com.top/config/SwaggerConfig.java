package com.top.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/.*"))
                .build();//regex-正则表达式
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("topchat的测试接口文档")
                .contact(new Contact("mandy","","hellomandy@126.com"))
                .description("这是我的swaggerui生成的接口文档")
                .version("1.0.0")
                .build();
    }
}
