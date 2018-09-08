package kr.co.tworld.shop.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.fileupload.util.mime.MimeUtility;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import kr.co.tworld.shop.common.model.FileInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * FileUtil
 * @author Sangjun, Park
 *
 */
@Slf4j
@Component
public class FileUtil {
	
	private final String rootUploadPath;
	
	public FileUtil(@Value("${path.upload.root}") String rootUploadPath) {
		this.rootUploadPath = rootUploadPath;
	}

	public Optional<FileInfo> upload(final MultipartFile multipartFile, final String uploadSubPath, final String newFilename) {
		return Optional.ofNullable(multipartFile)
				.filter(mf -> StringUtils.isNotEmpty(mf.getOriginalFilename()))
				.map(mf -> {
					// 1. Create the upload directory
					Path uploadPath = Paths.get(FilenameUtils.normalizeNoEndSeparator(rootUploadPath), uploadSubPath);
					try {
						Files.createDirectories(uploadPath);
						if(Files.isDirectory(uploadPath, LinkOption.NOFOLLOW_LINKS) == false) {
							throw new FileNotFoundException("Unable to create directory: " + uploadPath.toString());
						}
						// 2. Upload file
						final String sourceFilename = MimeUtility.decodeText(mf.getOriginalFilename());
						final String sourceFileExtension = FilenameUtils.getExtension(sourceFilename).toLowerCase();
						final String fullNewFilename = new StringBuilder(newFilename)
								.append(FilenameUtils.EXTENSION_SEPARATOR).append(sourceFileExtension).toString();
						final Path destinationPath = Paths.get(uploadPath.toString(), fullNewFilename);
						
						mf.transferTo(destinationPath.toAbsolutePath().toFile());
						// 3. Return file info
						return FileInfo.builder().uploadPath(uploadPath.toString()).uploadFilename(fullNewFilename)
								.originalFilename(sourceFilename).build();
					} catch (IOException e) {
						log.error(e.getMessage());
						return null;
					}
				});
	}
}
