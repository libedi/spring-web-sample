package kr.co.tworld.shop.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.tworld.shop.sample.model.Sample;

/**
 * SampleMapper interface
 * @author Sangjun, Park
 *
 */
@Mapper
public interface SampleMapper {

	List<Sample> selectCustomer(Integer customerId);

}
