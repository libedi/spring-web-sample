package kr.co.tworld.shop.sample.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;

import kr.co.tworld.shop.framework.model.SessionScopeModel;
import kr.co.tworld.shop.framework.model.ValidationMarkers.Create;
import kr.co.tworld.shop.framework.model.ValidationMarkers.Update;
import kr.co.tworld.shop.framework.security.model.User;
import kr.co.tworld.shop.sample.model.Sample;
import kr.co.tworld.shop.sample.service.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Sample Controller for REST API
 * @author Sangjun, Park
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/samples")
@RequiredArgsConstructor
public class SampleApiController {
	
	private final SampleService sampleService;
	
	private final ObjectMapper objectMapper;
	
	private final SessionScopeModel sessionScopeModel;

	/**
	 * get customer list
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Sample> getCustomerList(@AuthenticationPrincipal final User user) {
		log.info(user.toString());
		return this.sampleService.getCustomerList();
	}

	/**
	 * get customer by customerId
	 * @param customerId
	 * @return
	 */
	@GetMapping(path = "/{customerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Sample getCustomer(@PathVariable final int customerId) {
		return this.sampleService.getCustomer(customerId);
	}

	/**
	 * create customer
	 * @param sample
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void createCustomer(@RequestBody @Validated(Create.class) final Sample sample) {
		this.sampleService.createCustomer(sample);
	}

	/**
	 * update customer
	 * @param sample
	 */
	@RequestMapping(path = "/{customerId}", method = {RequestMethod.PUT, RequestMethod.PATCH},
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updateCustomer(@PathVariable final int customerId,
			@RequestBody @Validated(Update.class) final Sample sample) {
		sample.setCustomerId(customerId);
		this.sampleService.updateCustomer(sample);
	}

	/**
	 * delete customer by customerId
	 * @param customerId
	 */
	@DeleteMapping("/{customerId}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCustomer(@PathVariable final int customerId) {
		this.sampleService.deleleCustomer(customerId);
	}
	
	@GetMapping("/paging")
	public PageInfo<Sample> getCustomersByPaging() throws JsonProcessingException {
		PageInfo<Sample> page = this.sampleService.getCustomersByPaging(1, 3);
		log.debug("Page Info: {}", this.objectMapper.writeValueAsString(page));
		return page;
	}
	
	@GetMapping("/signin")
	public void testSessionScope() {
	}

}
