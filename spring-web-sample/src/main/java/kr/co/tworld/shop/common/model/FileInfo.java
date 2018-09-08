package kr.co.tworld.shop.common.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * FileInfo
 * @author Sangjun, Park
 *
 */
@Getter @Setter
@ToString
@Builder
public class FileInfo {

	private String uploadPath;
	private String uploadFilename;
	private String originalFilename;
}
