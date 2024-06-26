package com.example.client_credential_rest_template_service.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ServiceShutdownComponent {

      private final ApplicationContext appContext;

      @Autowired
      ServiceShutdownComponent(ApplicationContext appContext) {
            this.appContext = appContext;
      }

      /*
       * Invoke with `0` to indicate no error or different code to indicate
       * abnormal exit. es: shutdownManager.initiateShutdown(0);
       **/
      public void initiateShutdown(int returnCode) {
            SpringApplication.exit(appContext, () -> returnCode);
      }
}