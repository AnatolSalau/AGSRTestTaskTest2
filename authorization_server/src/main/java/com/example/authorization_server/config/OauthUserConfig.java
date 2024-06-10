package com.example.authorization_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class OauthUserConfig {
      @Bean
      public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder, JdbcUserDetailsManager jdbcUserDetailsManager) {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
            provider.setPasswordEncoder(passwordEncoder);
            provider.setUserDetailsService(jdbcUserDetailsManager);
            return new ProviderManager(provider);
      }

      @Bean
      public DaoAuthenticationProvider inMemoryDaoAuthenticationProvider(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
            DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setUserDetailsService(userDetailsService);
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
            return daoAuthenticationProvider;
      }
}
