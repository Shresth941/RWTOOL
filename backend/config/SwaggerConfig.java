package RwTool.rwtool.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Wealth Core - RW Tool API")
                        .version("1.0.0")
                        .description("API docs for capstone project: Reports, Ingest, Users, Roles, Favorites"));
    }
}
