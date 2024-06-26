package com.example.authorization_server.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Configuration
public class SecurityConfig {

      @Bean
      @Order(1)
      public SecurityFilterChain asSecurityFilterChain(HttpSecurity http) throws Exception {

            OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

            http.getConfigurer(
                  OAuth2AuthorizationServerConfigurer.class).oidc(Customizer.withDefaults()
            );

            http.exceptionHandling(e -> e
                  .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            );
            http.csrf(AbstractHttpConfigurer::disable);
            http.cors(AbstractHttpConfigurer::disable);

            return http.build();
      }

      @Bean
      @Order(2)
      public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                  .authorizeHttpRequests(ahr -> ahr.requestMatchers("/swagger-ui/**", "/v3/**").permitAll())
                  .authorizeHttpRequests(ahr -> ahr.anyRequest().authenticated())
                  .csrf(AbstractHttpConfigurer::disable)
                  .cors(AbstractHttpConfigurer::disable)
                  .formLogin(Customizer.withDefaults());

            return http.build();
      }

      @Bean
      JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
            return new JdbcUserDetailsManager(dataSource);
      }

      @Bean
      public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
      }

      @Bean
      public OAuth2TokenCustomizer<JwtEncodingContext> jwtTokenCustomizer() {
            return (context) -> {
                  if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                        context.getClaims().claims((claims) -> {
                              Set<String> roles = AuthorityUtils.authorityListToSet(context.getPrincipal().getAuthorities());

                              roles
                                    .stream()
                                    .map(c -> c.replaceFirst("^ROLE_", ""))
                                    .collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
                              claims.put("roles", roles);

                              Set<String> scope = (Set<String>) claims.get("scope");

                              scope.stream()
                                    .filter(oneScope -> oneScope.equals("Practitioner"))
                                    .findFirst()
                                    .ifPresent((role) -> {
                                          claims.put("roles", role);
                                    });
                        });
                  }
            };
      }

      @Bean
      public AuthorizationServerSettings authorizationServerSettings() {
            return AuthorizationServerSettings.builder().build();
      }

      @Bean
      public TokenSettings tokenSettings() {
            return TokenSettings.builder().build();
      }


      @Bean
      public JWKSource<SecurityContext> jwkSource() {
            RSAKey rsaKey = generateRsa();
            JWKSet jwkSet = new JWKSet(rsaKey);
            return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
      }

      public static RSAKey generateRsa() {
            KeyPair keyPair = generateRsaKey();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
      }

      static KeyPair generateRsaKey() {
            KeyPair keyPair;
            try {
                  KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                  keyPairGenerator.initialize(2048);
                  keyPair = keyPairGenerator.generateKeyPair();
            } catch (Exception ex) {
                  throw new IllegalStateException(ex);
            }
            return keyPair;
      }
}
