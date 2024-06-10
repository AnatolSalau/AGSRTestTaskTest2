package com.example.authorization_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@Configuration
public class OauthClientsConfug {

      @Bean
      public ClientSettings clientSettings() {
            return ClientSettings.builder()
                  .requireAuthorizationConsent(false)
                  .requireProofKey(false)
                  .build();
      }

      @Bean
      public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
            return new JdbcRegisteredClientRepository(jdbcTemplate);
      }
}
