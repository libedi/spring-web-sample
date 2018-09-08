package kr.co.tworld.shop.common.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.tworld.shop.common.util.FileUtil;
import lombok.RequiredArgsConstructor;

/**
 * 
 * @author Sangjun, Park
 *
 */
@Service
@RequiredArgsConstructor
public class FileService {
	
	private final FileUtil fileUtil;

	/**
	 * upload
	 * @param multipartFiles
	 */
	public void upload(final MultipartFile[] multipartFiles) {
		int i=0;
		for(MultipartFile multipartFile : multipartFiles) {
			this.fileUtil.upload(multipartFile, "/test", "testNewFilename" + (++i));
		}
	}

}
