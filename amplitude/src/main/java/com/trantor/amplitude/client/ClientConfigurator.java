package com.trantor.amplitude.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfigurator {

  @Value("${api.connection.timeout}")
  private Integer timeout;

  @Bean
  public ClientHttpRequestFactory clientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
        new HttpComponentsClientHttpRequestFactory();
    clientHttpRequestFactory.setConnectTimeout(timeout);
    clientHttpRequestFactory.setConnectionRequestTimeout(timeout);
    clientHttpRequestFactory.setReadTimeout(timeout);
    return clientHttpRequestFactory;
  }

  @Bean
  public RestTemplate getRestTemplate() {
    RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
    restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    return restTemplate;
  }
}
