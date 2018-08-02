package kr.co.tworld.shop.sample.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import kr.co.tworld.shop.sample.model.Sample;

/**
 * SampleMapper test case
 * @author Sang jun, Park
 *
 */
@RunWith(SpringRunner.class)
@MybatisTest
@ActiveProfiles("local")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SampleMapperTest {

	@Autowired
	private SampleMapper sampleMapper;
	
	@Test
	public void test01_init() throws Exception {
		assertNotNull(this.sampleMapper);
	}
	
	@Test
	public void test02_selectCustomerList() throws Exception {
		List<Sample> list = this.sampleMapper.selectCustomer(null);
		assertNotNull(list);
		assertTrue(list instanceof List);
		list.stream().forEach(System.out::println);
	}
	
	@Test
	public void test03_selectCustomer() throws Exception {
		List<Sample> list = this.sampleMapper.selectCustomer(1);
		assertNotNull(list);
		assertTrue(list instanceof List);
		assertTrue(list.size() == 1);
		Sample sample = list.get(0);
		Optional.ofNullable(sample).ifPresent(System.out::println);
	}
	
	@Test
	public void test04_insertCustomer() throws Exception {
		Sample newSample = Sample.builder()
				.customerId(10)
				.customerName("user10")
				.company("company10")
				.build();
		this.sampleMapper.insertCustomer(newSample);
	}
	
	@Test
	public void test05_updateCustomer() throws Exception {
		Sample target = this.sampleMapper.selectCustomer(1).get(0);
		target.setCustomerName("updateUser1");
		target.setCompany("updateCompany1");
		Sample expected = Sample.builder()
				.customerId(target.getCustomerId())
				.customerName(target.getCustomerName())
				.company(target.getCompany())
				.build();
		this.sampleMapper.updateCustomer(target);
		Sample actual = this.sampleMapper.selectCustomer(1).get(0);
		assertNotNull(actual);
		assertEquals(expected.getCustomerId(), actual.getCustomerId());
		assertEquals(expected.getCustomerName(), actual.getCustomerName());
		assertEquals(expected.getCompany(), actual.getCompany());
	}
	
	@Test
	public void test06_deleteCustomer() throws Exception {
		int customerId = 1;
		this.sampleMapper.deleteCustomer(customerId);
		List<Sample> list = this.sampleMapper.selectCustomer(customerId);
		assertTrue(list.isEmpty());
	}
	
}
