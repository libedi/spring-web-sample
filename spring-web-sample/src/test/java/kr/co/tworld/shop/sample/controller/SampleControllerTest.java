package kr.co.tworld.shop.sample.controller;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

/**
 * SampleController test case
 * @author Sang jun, Park
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SampleControllerTest {
	
	private SampleController controller;
	
	@Before
	public void init() throws Exception {
		this.controller = new SampleController(null, null);
	}
	
	@Test
	public void test01_viewCustomer() throws Exception {
		Model model = new ConcurrentModel();
		this.controller.viewCustomer(model);
	}

}
