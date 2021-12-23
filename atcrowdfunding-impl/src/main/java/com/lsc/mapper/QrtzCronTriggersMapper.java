package com.lsc.mapper;

import com.lsc.bean.QrtzCronTriggers;
import com.lsc.bean.QrtzCronTriggersExample;
import com.lsc.bean.QrtzCronTriggersKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QrtzCronTriggersMapper {
    long countByExample(QrtzCronTriggersExample example);

    int deleteByExample(QrtzCronTriggersExample example);

    int deleteByPrimaryKey(QrtzCronTriggersKey key);

    int insert(QrtzCronTriggers record);

    int insertSelective(QrtzCronTriggers record);

    List<QrtzCronTriggers> selectByExample(QrtzCronTriggersExample example);

    QrtzCronTriggers selectByPrimaryKey(QrtzCronTriggersKey key);

    int updateByExampleSelective(@Param("record") QrtzCronTriggers record, @Param("example") QrtzCronTriggersExample example);

    int updateByExample(@Param("record") QrtzCronTriggers record, @Param("example") QrtzCronTriggersExample example);

    int updateByPrimaryKeySelective(QrtzCronTriggers record);

    int updateByPrimaryKey(QrtzCronTriggers record);
}