package com.lsc.mapper;

import com.lsc.bean.TCodeTemplate;
import com.lsc.bean.TCodeTemplateExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TCodeTemplateMapper {
    long countByExample(TCodeTemplateExample example);

    int deleteByExample(TCodeTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TCodeTemplate record);

    int insertSelective(TCodeTemplate record);

    List<TCodeTemplate> selectByExample(TCodeTemplateExample example);

    TCodeTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TCodeTemplate record, @Param("example") TCodeTemplateExample example);

    int updateByExample(@Param("record") TCodeTemplate record, @Param("example") TCodeTemplateExample example);

    int updateByPrimaryKeySelective(TCodeTemplate record);

    int updateByPrimaryKey(TCodeTemplate record);
}