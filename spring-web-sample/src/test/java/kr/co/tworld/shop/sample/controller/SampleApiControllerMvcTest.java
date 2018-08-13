package kr.co.tworld.shop.sample.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.tworld.shop.sample.model.Sample;
import kr.co.tworld.shop.sample.service.SampleService;

/**
 * SampleApiController MVC Test case
 * @author Sangjun, Park
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SampleApiController.class, secure = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SampleApiControllerMvcTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private SampleService sampleService;
	
	@Test
	public void test01_getCustomerList() throws Exception {
		when(this.sampleService.getCustomerList()).thenReturn(Arrays.asList(new Sample()));
		this.mockMvc.perform(get("/api/samples").accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$").isArray())
		;
	}
	
	@Test
	public void test02_getCustomer() throws Exception {
		int customerId = 1;
		String customerUser = "user1";
		String company = "company1";
		when(this.sampleService.getCustomer(customerId))
				.thenReturn(Sample.builder().customerId(customerId).customerName(customerUser).company(company).build());
		this.mockMvc.perform(get("/api/samples/" + customerId).accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.customerId").value(customerId))
				.andExpect(jsonPath("$.customerName").value(customerUser))
				.andExpect(jsonPath("$.company").value(company))
		;
	}
	
	@Test
	public void test03_createCustomer() throws Exception {
		Sample expected = Sample.builder()
				.customerId(1)
				.customerName("user1")
				.company("company1")
				.build();
		doNothing().when(this.sampleService).createCustomer(expected);
		this.mockMvc.perform(post("/api/samples")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(this.objectMapper.writeValueAsBytes(expected)))
				.andDo(print())
				.andExpect(status().isCreated())
		;
	}
	
	@Test
	public void test04_updateCustomer() throws Exception {
		int customerId = 1;
		Sample expected = Sample.builder()
				.customerId(customerId)
				.customerName("user1")
				.company("company1")
				.build();
		doNothing().when(this.sampleService).updateCustomer(expected);
		this.mockMvc.perform(put("/api/samples/" + customerId)
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.content(this.objectMapper.writeValueAsBytes(expected)))
				.andDo(print())
				.andExpect(status().isOk())
		;
	}
	
	@Test
	public void test05_deleteCustomer() throws Exception {
		int customerId = 1;
		doNothing().when(this.sampleService).deleleCustomer(customerId);
		this.mockMvc.perform(delete("/api/samples/" + customerId))
				.andDo(print())
				.andExpect(status().isOk())
		;
	}

}
