package com.example.authorization_server.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OpenApiConfig {

      @Bean
      public GroupedOpenApi actuatorOpenApi() {
            String paths[] = {"/actuator", "/actuator/info", "/actuator/health"};
            return GroupedOpenApi
                  .builder()
                  .group("Actuator")
                  .pathsToMatch(paths)
                  .build();
      }

      @Bean
      public OpenAPI customOpenAPI() {
            return new OpenAPI()
                  .components(
                        new Components()
                              .addSecuritySchemes("spring_oauth", new SecurityScheme()
                                    .type(SecurityScheme.Type.OAUTH2)
                                    .description("Oauth2 flow")
                                    .flows(new OAuthFlows()
                                          .authorizationCode(new OAuthFlow()
                                                .authorizationUrl("http://127.0.0.1:8082/oauth2/authorize")
                                                .tokenUrl("http://127.0.0.1:8082/oauth/token")
                                                .scopes(new Scopes()
                                                      .addString("Patient.Read", "for read operations")
                                                      .addString("Patient.Write", "for write operations")
                                                ))))

                  )
                  .security(Arrays.asList(
                        new SecurityRequirement().addList("spring_oauth")))
                  .info(new Info()
                        .title("Authorization server in AGSR test task")
                        .description("Authorization server in AGSR test task")
                        .contact(new Contact().email("salovanatoly5@mail.ru").name("Developer: Salov Anatoly"))
                        .version("2.0")
                  );
      }
}