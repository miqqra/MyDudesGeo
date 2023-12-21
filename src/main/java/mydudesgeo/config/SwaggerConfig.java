package mydudesgeo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import static springfox.documentation.builders.PathSelectors.any;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("public-api")
                .apiInfo(apiInfo())
                .select()
                .paths(any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("MyDudesGeo API")
                .description("API of MyDudesGeo microservice")
                .build();
    }

//    @Bean
//    public OpenAPI apiInfo() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("MyDudesGeo API")
//                        .description("API of MyDudesGeo microservice")
//                        .version("1.0.0"));
//    }
//
//    @Bean
//    public GroupedOpenApi httpApi() {
//        return GroupedOpenApi.builder()
//                .group("http")
//                .pathsToMatch("/**")
//                .build();
//    }
//
//    @Bean
//    public List<SecurityRequirement> securityRequirements(){
//        return List.of(new SecurityRequirement().addList("admin", "admin"));
//    }
}

