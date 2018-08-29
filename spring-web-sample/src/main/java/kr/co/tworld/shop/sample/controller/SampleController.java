package kr.co.tworld.shop.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;

import kr.co.tworld.shop.common.view.XlsxDownloadView;
import kr.co.tworld.shop.sample.service.SampleService;
import lombok.RequiredArgsConstructor;

/**
 * Sample Controller for view forwarding
 * @author Sangjun, Park
 *
 */
@Controller
@RequestMapping("/view/sample")
@RequiredArgsConstructor
public class SampleController {
	
	private final SampleService sampleService;
	
	private final XlsxDownloadView xlsxDownloadView;

	@GetMapping("/customer")
	public void viewCustomer(final Model model) {}
	
	@GetMapping("/excel")
	public View testExcel(final Model model) {
		model.addAttribute("excelData", this.sampleService.getCustomerListExcel());
		model.addAttribute("fileName", "testExcelFile");
		return this.xlsxDownloadView;
	}

}
