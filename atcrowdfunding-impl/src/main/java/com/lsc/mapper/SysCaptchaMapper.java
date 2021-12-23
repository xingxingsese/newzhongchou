package com.lsc.mapper;

import com.lsc.bean.SysCaptcha;
import com.lsc.bean.SysCaptchaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysCaptchaMapper {
    long countByExample(SysCaptchaExample example);

    int deleteByExample(SysCaptchaExample example);

    int deleteByPrimaryKey(String uuid);

    int insert(SysCaptcha record);

    int insertSelective(SysCaptcha record);

    List<SysCaptcha> selectByExample(SysCaptchaExample example);

    SysCaptcha selectByPrimaryKey(String uuid);

    int updateByExampleSelective(@Param("record") SysCaptcha record, @Param("example") SysCaptchaExample example);

    int updateByExample(@Param("record") SysCaptcha record, @Param("example") SysCaptchaExample example);

    int updateByPrimaryKeySelective(SysCaptcha record);

    int updateByPrimaryKey(SysCaptcha record);
}