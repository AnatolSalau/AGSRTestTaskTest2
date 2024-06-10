package com.example.resource_server.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {
      @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
      private String jwkSetUri;

      @Bean
      public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                  .csrf(AbstractHttpConfigurer::disable)
                  .cors(AbstractHttpConfigurer::disable)
                  .authorizeHttpRequests(req -> {
                        req.requestMatchers("/swagger-ui/**", "/v3/**").permitAll();
                        req.anyRequest().authenticated();
                  })
                  .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt((jwtConfigurer -> jwtConfigurer
                                    .jwtAuthenticationConverter(
                                          jwt -> new JwtAuthenticationToken(
                                                jwt, getDualJwtAuthenticationConverter().convert(jwt)
                                          )
                                    )
                                    .decoder(jwtDecoder())
                              )
                        )
                  );
            return http.build();
      }

      private DelegatingJwtGrantedAuthoritiesConverter getDualJwtAuthenticationConverter() {
            JwtGrantedAuthoritiesConverter scope = new JwtGrantedAuthoritiesConverter();
            scope.setAuthoritiesClaimName("scope");
            JwtGrantedAuthoritiesConverter roles = new JwtGrantedAuthoritiesConverter();
            roles.setAuthorityPrefix("ROLE_");
            roles.setAuthoritiesClaimName("roles");
            return new DelegatingJwtGrantedAuthoritiesConverter(scope, roles);
      }

      @Bean
      public JwtDecoder jwtDecoder() {
            return NimbusJwtDecoder
                  .withJwkSetUri(jwkSetUri)
                  .jwsAlgorithm(SignatureAlgorithm.RS256).build();
      }
}
