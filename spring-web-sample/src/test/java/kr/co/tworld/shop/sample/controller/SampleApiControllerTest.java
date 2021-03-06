package kr.co.tworld.shop.sample.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.tworld.shop.common.exception.ResourceConflictException;
import kr.co.tworld.shop.framework.model.SessionScopeModel;
import kr.co.tworld.shop.framework.security.model.User;
import kr.co.tworld.shop.sample.model.Sample;
import kr.co.tworld.shop.sample.service.SampleService;

/**
 * SampleApiController test case
 * @author Sangjun, Park
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SampleApiControllerTest {
	
	private SampleApiController controller;
	private SampleService service;
	private SessionScopeModel sessionScopeModel;
	
	@Before
	public void init() throws Exception {
		this.service = mock(SampleService.class);
		this.sessionScopeModel = mock(SessionScopeModel.class);
		this.controller = new SampleApiController(this.service, new ObjectMapper(), sessionScopeModel);
	}
	
	@Test
	public void test01_getCustomerList() throws Exception {
		when(this.service.getCustomerList()).thenReturn(Collections.emptyList());
		User mockUser = User.builder().username("admin").password("password").build();
		List<Sample> actual = this.controller.getCustomerList(mockUser);
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
		when(this.service.getCustomer(customerId)).thenReturn(expected);
		Sample actual = this.controller.getCustomer(customerId);
		assertNotNull(actual);
		assertThat(actual).isEqualToComparingFieldByField(expected);
	}
	
	@Test
	public void test03_createCustomer() throws Exception {
		int customerId = 1;
		Sample sample = Sample.builder()
				.customerId(customerId)
				.customerName("user1")
				.company("company1")
				.build();
		this.controller.createCustomer(sample);
	}
	
	@Test(expected = ResourceConflictException.class)
	public void test04_createCustomerThrow() throws Exception {
		int customerId = 1;
		Sample sample = Sample.builder()
				.customerId(customerId)
				.customerName("user1")
				.company("company1")
				.build();
		doThrow(ResourceConflictException.class)
			.when(this.service).createCustomer(sample);
		this.controller.createCustomer(sample);
	}
	
	@Test
	public void test05_updateCustomer() throws Exception {
		int customerId = 1;
		Sample sample = Sample.builder()
				.customerId(customerId)
				.customerName("user1")
				.company("company1")
				.build();
		this.controller.updateCustomer(customerId, sample);
	}
	
	@Test
	public void test06_deleteCustomer() throws Exception {
		int customerId = 1;
		this.controller.deleteCustomer(customerId);
	}
	
}
