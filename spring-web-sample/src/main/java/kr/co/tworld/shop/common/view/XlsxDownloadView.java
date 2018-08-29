package kr.co.tworld.shop.common.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import kr.co.tworld.shop.common.model.ExcelData;
import kr.co.tworld.shop.common.util.XlsxUtil;
import kr.co.tworld.shop.framework.view.AbstractXlsxCustomView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Excel Download View component class
 * @author Sangjun, Park
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class XlsxDownloadView extends AbstractXlsxCustomView {
	
	private final XlsxUtil xlsxUtil;
	
	@Override
	protected void buildExcelDocument(final Map<String, Object> model, final Workbook workbook,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		
		Assert.notNull(model, "Excel data must not be null.");
		
		log.info("Excel Download...");
		
		final ExcelData excelData = (ExcelData) model.get("excelData");
		
		// 시트정보
		final Sheet sheet = workbook.createSheet(excelData.getSheetName());
		this.xlsxUtil.defaultSheetStyle(sheet);
		
		// 헤더생성
		int rowIdx = this.xlsxUtil.createCell(workbook, sheet, excelData.getHeaderList(), null, 0);
		// 데이터 생성
		this.xlsxUtil.createCell(workbook, sheet, excelData.getDataList(), excelData.getTypeList(), rowIdx);
		// 셀 병합
		this.xlsxUtil.mergeCellByReference(sheet, excelData.getMergeList());
		this.xlsxUtil.mergeCellByAddress(sheet, excelData.getMergeInfoList());
		// 셀 width 조정
		this.xlsxUtil.autoSizeColumn(sheet, excelData.getTypeList().size());
	}

}
