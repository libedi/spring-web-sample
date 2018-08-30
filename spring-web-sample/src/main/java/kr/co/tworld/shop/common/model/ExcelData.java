package kr.co.tworld.shop.common.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * ExcelData
 * @author Sangjun, Park
 *
 */
@Getter @Setter
public class ExcelData {
	
	private String sheetName = "sheet1";
	private List<List<String>> headerList;
	private List<List<String>> dataList;
	private List<ColumnType> typeList;
	private List<String> mergeList;
	private List<CellRangeAddress> mergeInfoList;
	
	public ExcelData() {}
	
	public ExcelData(final String sheetName) {
		this.sheetName = sheetName;
	}
	
	/**
	 * 헤더 로우 추가
	 * @param headers
	 */
	public void addRowHeaders(final String... headers) {
		if(CollectionUtils.isEmpty(headerList)) {
			this.headerList = new ArrayList<>();
		}
		this.headerList.add(Arrays.asList(headers));
	}
	
	/**
	 * 데이터 로우 추가
	 * @param datas
	 */
	public void addRowDatas(final String... datas) {
		if(CollectionUtils.isEmpty(dataList)) {
			this.dataList = new ArrayList<>();
		}
		this.dataList.add(Arrays.asList(datas));
	}
	
	/**
	 * 컬럼 타입 추가
	 * @param columnType
	 */
	public void addColumnType(final ColumnType columnType) {
		if(CollectionUtils.isEmpty(typeList)) {
			this.typeList = new ArrayList<>();
		}
		this.typeList.add(columnType);
	}
	
	/**
	 * 컬럼 타입 리스트 설정
	 * @param columnTypes
	 */
	public void setColumnTypes(final ColumnType... columnTypes) {
		this.typeList = new ArrayList<>(Arrays.asList(columnTypes));
	}
	
	/**
	 * 병합정보 추가 : Standard Area Reference
	 * @param mergeString
	 */
	public void addMergeString(final String mergeString) {
		if(CollectionUtils.isEmpty(mergeList)) {
			this.mergeList = new ArrayList<>();
		}
		this.mergeList.add(mergeString);
	}
	
	/**
	 * 병합정보 설정 : Standard Area Reference
	 * @param mergeStrings
	 */
	public void setMergeStrings(final String... mergeStrings) {
		this.mergeList = new ArrayList<>(Arrays.asList(mergeStrings));
	}
	
	/**
	 * 병합정보 추가 : CellRangeAddress
	 * @param firstRow
	 * @param lastRow
	 * @param firstCol
	 * @param lastCol
	 */
	public void addMergeInfo(int firstRow, int lastRow, int firstCol, int lastCol) {
		if(CollectionUtils.isEmpty(mergeInfoList)) {
			this.mergeInfoList = new ArrayList<>();
		}
		this.mergeInfoList.add(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}
	
	/**
	 * 병합정보 설정 : CellRangeAddress<br>
	 * - 각각의 배열은 firstRow, lastRow, firstCol, lastCol 순이 되어야 함.
	 * @param mergeInfos
	 */
	public void setMergeInfos(final int[]... mergeInfos) {
		for(int[] mergeInfo : mergeInfos) {
			this.addMergeInfo(mergeInfo[0], mergeInfo[1], mergeInfo[2], mergeInfo[3]);
		}
	}

}
