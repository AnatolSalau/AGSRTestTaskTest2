package com.example.client_credential_rest_template_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.isNull;

@Component
public class OAuth2ClientInterceptor implements ClientHttpRequestInterceptor {

      private final OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager;
      private final ClientRegistration clientRegistration;
      private final AuthenticationManager authenticationManager;

      @Autowired
      public OAuth2ClientInterceptor(OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager, ClientRegistrationRepository clientRegistrationRepository, AuthenticationManager authenticationManager) {
            this.oAuth2AuthorizedClientManager = oAuth2AuthorizedClientManager;
            this.clientRegistration = clientRegistrationRepository.findByRegistrationId("spring");
            this.authenticationManager = authenticationManager;
      }

      @Override
      public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
      ) throws IOException {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                  Collection<GrantedAuthority> authorities = new ArrayList<>();
                  authorities.add(new SimpleGrantedAuthority("anonymousUser"));
                  AnonymousAuthenticationToken anonymousAuthenticationToken = new AnonymousAuthenticationToken(
                        "1956335972", "anonymousUser", authorities
                  );
                  authentication = anonymousAuthenticationToken;

            }

            OAuth2AuthorizeRequest oAuth2AuthorizeRequest = OAuth2AuthorizeRequest
                  .withClientRegistrationId(clientRegistration.getRegistrationId())
                  .principal(authentication)
                  .build();

            OAuth2AuthorizedClient client = oAuth2AuthorizedClientManager.authorize(oAuth2AuthorizeRequest);

            if (isNull(client)) {
                  throw new IllegalStateException("Missing credentials");
            }

            request.getHeaders().add(HttpHeaders.AUTHORIZATION,
                  "Bearer " + client.getAccessToken().getTokenValue());
            return execution.execute(request, body);
      }
}