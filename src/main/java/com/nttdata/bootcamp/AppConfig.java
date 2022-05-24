package com.nttdata.bootcamp;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

	/*@Bean("clientRest")
	public RestTemplate registrarRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(2000);
		requestFactory.setReadTimeout(2000);

		restTemplate.setRequestFactory(requestFactory);
		return restTemplate;
	}*/
	

	@LoadBalanced
	@Bean
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}
	
}
