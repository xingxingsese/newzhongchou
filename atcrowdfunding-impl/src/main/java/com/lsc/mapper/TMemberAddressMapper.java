package com.lsc.mapper;

import com.lsc.bean.TMemberAddress;
import com.lsc.bean.TMemberAddressExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TMemberAddressMapper {
    long countByExample(TMemberAddressExample example);

    int deleteByExample(TMemberAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMemberAddress record);

    int insertSelective(TMemberAddress record);

    List<TMemberAddress> selectByExample(TMemberAddressExample example);

    TMemberAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMemberAddress record, @Param("example") TMemberAddressExample example);

    int updateByExample(@Param("record") TMemberAddress record, @Param("example") TMemberAddressExample example);

    int updateByPrimaryKeySelective(TMemberAddress record);

    int updateByPrimaryKey(TMemberAddress record);
}