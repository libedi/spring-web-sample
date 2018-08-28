package kr.co.tworld.shop.framework.view;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.view.AbstractView;

/**
 * AbstractXlsxCustomView
 * @author Sangjun, Park
 *
 */
public abstract class AbstractXlsxCustomView extends AbstractView {
	
	private final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	
	public AbstractXlsxCustomView() {
		this.setContentType(CONTENT_TYPE);
	}
	
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	@Override
	protected void renderMergedOutputModel(final Map<String, Object> model, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		
		// Create a fresh workbook instance for this render step.
		Workbook workbook = null;
		if(model.containsKey("workbook")) {
			// 미리 생성된 workbook
			workbook = (Workbook) model.get("workbook");
		} else {
			// 엑셀 데이터로 workbook 생성
			workbook = createWorkbook();
			// Delegate to application-provided document code.
			buildExcelDocument(model, workbook, request, response);
		}
		
		// Set response headers.
		setResponseHeaders(response, makeFilename(request, (String) model.get("fileName")));

		// Flush byte array to servlet output stream.
		renderWorkbook(workbook, response);
	}
	
	protected Workbook createWorkbook() {
		return new SXSSFWorkbook();
	}
	
	protected void renderWorkbook(Workbook workbook, HttpServletResponse response) throws IOException {
		workbook.write(response.getOutputStream());
		// java.io.Closeable only implemented as of POI 3.10
		if(workbook instanceof Closeable) {
			((Closeable) workbook).close();
		}
		// Dispose of temporary files in case of streaming variant...
		((SXSSFWorkbook) workbook).dispose();
	}
	
	private void setResponseHeaders(final HttpServletResponse response, final String filename) {
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				new StringBuilder("attachment; filename=\"").append(filename).append(".xlsx\"").toString());
		response.setHeader(HttpHeaders.CACHE_CONTROL, "max-age=0, private, must-revalidate");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setContentType(getContentType());
	}
	
	private String makeFilename(final HttpServletRequest request, final String filename)
			throws UnsupportedEncodingException {
		
		final String userAgent = request.getHeader(HttpHeaders.USER_AGENT).toLowerCase();
		if (StringUtils.contains(userAgent, "msie") || StringUtils.contains(userAgent, "trident")
				|| StringUtils.contains(userAgent, "edge/")) {
			// MS IE, Edge
			return URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "\\ ");
		} else {
			// FF, Opera, Safari, Chrome
			return new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
		}
	}
	
	/**
	 * Application-provided subclasses must implement this method to populate
	 * the Excel workbook document, given the model.
	 * @param model the model Map
	 * @param workbook the Excel workbook to populate
	 * @param request in case we need locale etc. Shouldn't look at attributes.
	 * @param response in case we need to set cookies. Shouldn't write to it.
	 */
	protected abstract void buildExcelDocument(
			Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

}
