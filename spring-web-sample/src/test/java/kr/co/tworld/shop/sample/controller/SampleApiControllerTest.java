package kr.co.tworld.shop.sample.controller;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

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
	
	@Before
	public void init() throws Exception {
		this.service = mock(SampleService.class);
		this.controller = new SampleApiController(this.service);
	}
	

}
