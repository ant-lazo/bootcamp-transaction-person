package com.nttdata.bootcamp;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.bootcamp.utils.Constants;

@Configuration
public class AppConfig {
	
    @LoadBalanced
    @Bean
	public WebClient.Builder WebClient() {
      return WebClient.builder();
    }
	
}
