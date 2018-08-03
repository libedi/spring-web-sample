package kr.co.tworld.shop.sample.service;

import java.util.List;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

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
	 * find all
	 * @return
	 */
	public List<Sample> getCustomerList() {
		return this.sampleMapper.selectCustomer(null);
	}

	/**
	 * find by customerId
	 * @param customerId
	 * @return
	 */
	public Sample getCustomer(int customerId) {
		return this.sampleMapper.selectCustomer(customerId).stream()
				.findFirst().orElseThrow(ResourceNotFoundException::new);
	}

}
