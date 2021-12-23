package com.lsc.mapper;

import com.lsc.bean.SysUserToken;
import com.lsc.bean.SysUserTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysUserTokenMapper {
    long countByExample(SysUserTokenExample example);

    int deleteByExample(SysUserTokenExample example);

    int deleteByPrimaryKey(Long userId);

    int insert(SysUserToken record);

    int insertSelective(SysUserToken record);

    List<SysUserToken> selectByExample(SysUserTokenExample example);

    SysUserToken selectByPrimaryKey(Long userId);

    int updateByExampleSelective(@Param("record") SysUserToken record, @Param("example") SysUserTokenExample example);

    int updateByExample(@Param("record") SysUserToken record, @Param("example") SysUserTokenExample example);

    int updateByPrimaryKeySelective(SysUserToken record);

    int updateByPrimaryKey(SysUserToken record);
}