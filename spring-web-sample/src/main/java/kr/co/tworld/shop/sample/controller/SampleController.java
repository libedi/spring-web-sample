package kr.co.tworld.shop.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Sample Controller for view forwarding
 * @author Sangjun, Park
 *
 */
@Controller
@RequestMapping("/view/sample")
public class SampleController {

	@GetMapping("/customer")
	public void viewCustomer(Model model) {}

}
