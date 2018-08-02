package kr.co.tworld.shop.sample.service;

import org.springframework.stereotype.Service;

import kr.co.tworld.shop.sample.mapper.SampleMapper;
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

}
