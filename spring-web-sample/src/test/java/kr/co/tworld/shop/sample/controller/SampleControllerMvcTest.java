package kr.co.tworld.shop.sample.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * SampleController MVC Test case
 * @author Sangjun, Park
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SampleController.class, secure = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SampleControllerMvcTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void test01_viewCustomer() throws Exception {
		this.mockMvc.perform(get("/view/sample/customer").accept(MediaType.TEXT_HTML).characterEncoding("UTF-8"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("view/sample/customer"))
		;
	}
}
