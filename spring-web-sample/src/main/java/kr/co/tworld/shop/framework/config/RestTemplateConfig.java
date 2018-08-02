package kr.co.tworld.shop.framework.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
public class RestTemplateConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder()
				.requestFactory(HttpComponentsClientHttpRequestFactory.class)	// for using PATCH method
				.setReadTimeout(1000 * 10)		// 10 seconds
				.setConnectTimeout(1000 * 10)	// 10 seconds
				.build();
	}
}
