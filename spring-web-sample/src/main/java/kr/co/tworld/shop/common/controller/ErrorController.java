package kr.co.tworld.shop.common.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.tworld.shop.common.util.MessageSourceUtil;
import lombok.RequiredArgsConstructor;

/**
 * Error Handler Controller
 * @author Sang jun, Park
 *
 */
@Controller
@RequiredArgsConstructor
public class ErrorController {
	
	private final MessageSourceUtil messageSource;
	
	/**
	 * error page
	 * @param model
	 * @param code
	 * @return
	 */
	@GetMapping("/error/{code}")
	public String error(final Model model, @PathVariable(required = false) final String code) {
		model.addAttribute("title", this.messageSource.getMessage("error.msg.title"));
		model.addAttribute("description",
				this.messageSource.getMessage("error.msg.desc." + Optional.ofNullable(code).orElseGet(() -> "500")));
		return "/view/error/error";
	}
}
