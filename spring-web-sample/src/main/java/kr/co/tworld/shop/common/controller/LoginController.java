package kr.co.tworld.shop.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * LoginController class
 * @author Sang jun, Park
 *
 */
@Controller
public class LoginController {
	
	@GetMapping(path = {"", "/"})
	public String index(Model model) {
		return "forward:/view/login";
	}

	@GetMapping("/view/login")
	public void loginPage(Model model) {}
}
