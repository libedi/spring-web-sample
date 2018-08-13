package kr.co.tworld.shop.sample.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.tworld.shop.sample.model.Sample;

/**
 * SampleController REST API test case
 * @author Sang jun, Park
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SampleControllerRestTest {

	@Autowired
	private TestRestTemplate restTemplate;
	
	private String jwt;
	
	@Before
	public void init() throws Exception {
		final ResponseEntity<Void> resp = this.restTemplate.postForEntity("/api/login",
				"{\"username\":\"admin\", \"password\":\"password\"}", Void.class);
		if (resp.getStatusCode() == HttpStatus.OK) {
			jwt = resp.getHeaders().get(HttpHeaders.AUTHORIZATION).stream().findFirst().get();
		}
	}
	
	@Test
	public void test01_getCustomer() throws Exception {
		final HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, jwt);
		final HttpEntity<Void> httpEntity = new HttpEntity<Void>(headers);
		final List<Sample> list = this.restTemplate
				.exchange("/api/samples", HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Sample>>() {})
				.getBody();
		assertThat(list).allMatch(s -> s instanceof Sample);
	}
	
	@Test
	public void test02_putCustomer() throws Exception {
		final int customerId = 1;
		final Sample expected = Sample.builder().customerId(customerId).customerName("updateUser1").company("updateCompany1").build();
		
		final HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.AUTHORIZATION, jwt);
		final HttpEntity<Sample> httpEntity = new HttpEntity<Sample>(expected, headers);
		
		this.restTemplate.exchange("/api/samples/" + customerId, HttpMethod.PUT, httpEntity, Void.class);
		Sample actual = this.restTemplate.exchange("/api/samples/" + customerId, HttpMethod.GET, httpEntity, Sample.class).getBody();
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}
}
