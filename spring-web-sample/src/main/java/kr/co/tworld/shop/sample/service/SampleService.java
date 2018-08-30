package kr.co.tworld.shop.sample.service;

import java.util.List;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.tworld.shop.common.exception.ResourceConflictException;
import kr.co.tworld.shop.common.model.ColumnType;
import kr.co.tworld.shop.common.model.ExcelData;
import kr.co.tworld.shop.sample.mapper.SampleMapper;
import kr.co.tworld.shop.sample.model.Sample;
import lombok.RequiredArgsConstructor;

/**
 * SampleService class
 * @author Sangjun, Park
 *
 */
@Service
@RequiredArgsConstructor
public class SampleService {
	
	private final SampleMapper sampleMapper;

	/**
	 * get customer all list
	 * @return
	 */
	public List<Sample> getCustomerList() {
		return this.sampleMapper.selectCustomer(null);
	}

	/**
	 * get customer info by customerId
	 * @param customerId
	 * @return
	 */
	public Sample getCustomer(final int customerId) {
		return this.sampleMapper.selectCustomer(customerId).stream()
				.findFirst().orElseThrow(ResourceNotFoundException::new);
	}

	/**
	 * create customer
	 * @param sample
	 */
	@Transactional
	public void createCustomer(final Sample sample) {
		this.sampleMapper.selectCustomer(sample.getCustomerId()).stream().findFirst().ifPresent(s -> {
			throw new ResourceConflictException("Customer already exists.");
		});
		this.sampleMapper.insertCustomer(sample);
	}

	/**
	 * update customer
	 * @param sample
	 */
	@Transactional
	public void updateCustomer(final Sample sample) {
		this.sampleMapper.selectCustomer(sample.getCustomerId()).stream().findFirst()
				.orElseThrow(ResourceNotFoundException::new);
		this.sampleMapper.updateCustomer(sample);
	}

	/**
	 * delete customer by customerId
	 * @param customerId
	 */
	@Transactional
	public void deleleCustomer(final int customerId) {
		this.sampleMapper.deleteCustomer(customerId);
	}

	/**
	 * create excel data
	 * @return
	 */
	public ExcelData getCustomerListExcel() {
		final List<Sample> list = this.getCustomerList();
		
		// create excel data & sheet name
		final ExcelData excelData = new ExcelData("customers");
		
		// create header names
		excelData.addRowHeaders("고객정보리스트", "", "");
		excelData.addRowHeaders("고객ID", "고객명", "업체명");
		excelData.addRowHeaders("", "고객명", "업체명");
		
		// set merge info : Standard Area Reference
		excelData.setMergeStrings("A1:C1", "A2:A3");
		
		// set merge info : CellRangeAddress
//		excelData.setMergeInfos(new int[] {0, 0, 0, 2}, new int[] {1, 2, 0, 0});
		
		// create data
		list.stream().forEach(c -> {
			excelData.addRowDatas(
					String.valueOf(c.getCustomerId()),
					c.getCustomerName(),
					c.getCompany());
		});
		
		// set column type
		excelData.setColumnTypes(
				ColumnType.INTEGER,
				ColumnType.STRING,
				ColumnType.STRING);
		
		return excelData;
	}

}
