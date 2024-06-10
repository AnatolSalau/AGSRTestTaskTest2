package com.example.client_credential_rest_template_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

      @Bean
      SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                  .csrf().disable()
                  .cors().disable()
                  .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())
                  .oauth2Login(withDefaults())
                  .oauth2Client(withDefaults());
            http.getSharedObject(AuthenticationManager.class);
            return http.build();
      }

      @Bean
      public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
            System.out.println();
            return config.getAuthenticationManager();
      }

      @Bean
      public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService clientService) {

            OAuth2AuthorizedClientProvider authorizedClientProvider =
                  OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

            AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                  new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, clientService);
            authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

            return authorizedClientManager;
      }
}