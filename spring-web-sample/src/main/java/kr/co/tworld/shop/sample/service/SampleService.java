package kr.co.tworld.shop.sample.service;

import java.util.List;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import kr.co.tworld.shop.common.exception.ResourceConflictException;
import kr.co.tworld.shop.sample.mapper.SampleMapper;
import kr.co.tworld.shop.sample.model.Sample;
import lombok.RequiredArgsConstructor;

/**
 * SampleService class
 * @author Sangjun, Park
 *
 */
@Service
@RequiredArgsConstructor
public class SampleService {
	
	private final SampleMapper sampleMapper;

	/**
	 * get customer all list
	 * @return
	 */
	public List<Sample> getCustomerList() {
		return this.sampleMapper.selectCustomer(null);
	}

	/**
	 * get customer info by customerId
	 * @param customerId
	 * @return
	 */
	public Sample getCustomer(int customerId) {
		return this.sampleMapper.selectCustomer(customerId).stream()
				.findFirst().orElseThrow(ResourceNotFoundException::new);
	}

	/**
	 * create customer
	 * @param sample
	 */
	public void createCustomer(Sample sample) {
		this.sampleMapper.selectCustomer(sample.getCustomerId()).stream().findFirst().ifPresent(s -> {
			throw new ResourceConflictException("Customer already exists.");
		});
		this.sampleMapper.insertCustomer(sample);
	}

	/**
	 * update customer
	 * @param sample
	 */
	public void updateCustomer(Sample sample) {
		this.sampleMapper.updateCustomer(sample);
	}

	/**
	 * delete customer by customerId
	 * @param customerId
	 */
	public void deleleCustomer(int customerId) {
		this.sampleMapper.deleteCustomer(customerId);
	}
	
	

}
