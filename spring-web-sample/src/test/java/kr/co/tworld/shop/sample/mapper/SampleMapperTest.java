package kr.co.tworld.shop.sample.mapper;

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
	public void test01_selectCustomerList() throws Exception {
		List<Sample> list = this.sampleMapper.selectCustomer(null);
		assertTrue(list instanceof List);
		list.stream().forEach(System.out::println);
	}
	
	@Test
	public void test02_selectCustomer() throws Exception {
		List<Sample> list = this.sampleMapper.selectCustomer(1);
		assertTrue(list instanceof List);
		assertTrue(list.size() == 1);
		Sample sample = list.get(0);
		Optional.ofNullable(sample).ifPresent(System.out::println);
	}
	
}
