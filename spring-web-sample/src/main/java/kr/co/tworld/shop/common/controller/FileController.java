package kr.co.tworld.shop.common.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import kr.co.tworld.shop.common.service.FileService;
import lombok.RequiredArgsConstructor;

/**
 * FileController
 * @author Sangjun, Park
 *
 */
@Controller
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

	private final FileService fileService;
	
	@PostMapping("/upload")
	@ResponseStatus(HttpStatus.CREATED)
	public void upload(@RequestParam("files") final MultipartFile[] multipartFiles) {
		this.fileService.upload(multipartFiles);
	}
	
}
