package com.wanhe.apartment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("公寓管理项目API")
                        .version("1.0")
                        .description("公寓管理项目的接口文档"));
    }

    @Bean
    public GroupedOpenApi userAPI() {
        return GroupedOpenApi.builder().group("角色信息管理").
                pathsToMatch("/api/**").
                build();
    }

    /*@Bean
    public GroupedOpenApi systemAPI() {
        return GroupedOpenApi.builder().group("产品信息管理").
                pathsToMatch("/product/**").
                build();
    }*/
}
