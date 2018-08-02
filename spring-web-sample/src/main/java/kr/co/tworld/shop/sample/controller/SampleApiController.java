package kr.co.tworld.shop.sample.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tworld.shop.sample.service.SampleService;
import lombok.RequiredArgsConstructor;

/**
 * Sample Controller for REST API
 * @author Sangjun, Park
 *
 */
@RestController
@RequestMapping("/sample")
@RequiredArgsConstructor
public class SampleApiController {
	
	private final SampleService sampleService;

}
