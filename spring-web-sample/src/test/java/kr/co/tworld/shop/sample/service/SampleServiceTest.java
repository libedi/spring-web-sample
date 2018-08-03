package kr.co.tworld.shop.sample.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import kr.co.tworld.shop.sample.mapper.SampleMapper;
import kr.co.tworld.shop.sample.model.Sample;

/**
 * SampleService test case
 * @author Sangjun, Park
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SampleServiceTest {

	private SampleService sampleService;
	private SampleMapper sampleMapper;
	
	@Before
	public void init() throws Exception {
		this.sampleMapper = mock(SampleMapper.class);
		this.sampleService = new SampleService(this.sampleMapper);
	}
	
	@Test
	public void test01_getCustomerList() throws Exception {
		when(this.sampleMapper.selectCustomer(null)).thenReturn(Collections.emptyList());
		List<Sample> actual = this.sampleService.getCustomerList();
		assertNotNull(actual);
		assertTrue(actual instanceof List);
	}
	
	@Test
	public void test02_getCustomer() throws Exception {
		int customerId = 1;
		Sample expected = Sample.builder()
				.customerId(customerId)
				.customerName("user1")
				.company("company1")
				.build();
		when(this.sampleMapper.selectCustomer(customerId)).thenReturn(Arrays.asList(expected));
		Sample actual = this.sampleService.getCustomer(customerId);
		assertNotNull(actual);
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}
	
	@Test(expected = ResourceNotFoundException.class)
	public void test03_getCustomerThrow() throws Exception {
		int customerId = 10;
		when(this.sampleMapper.selectCustomer(customerId)).thenReturn(Collections.emptyList());
		this.sampleService.getCustomer(customerId);
	}
	
}
