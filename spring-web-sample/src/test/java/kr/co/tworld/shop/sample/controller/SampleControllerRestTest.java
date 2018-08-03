package kr.co.tworld.shop.sample.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
	
	@Autowired
	public void tes01_getCustomer() throws Exception {
		List<Sample> list = this.restTemplate
				.exchange("/api/samples", HttpMethod.GET, null, new ParameterizedTypeReference<List<Sample>>() {})
				.getBody();
		assertThat(list).allMatch(s -> s instanceof Sample);
	}
	
	@Test
	public void test02_putCustomer() throws Exception {
		Sample expected = Sample.builder().customerId(1).customerName("updateUser1").company("updateCompany1").build();
		this.restTemplate.put("/api/samples", expected);
		Sample actual = this.restTemplate.getForObject("/api/samples/" + expected.getCustomerId(), Sample.class);
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}
}
