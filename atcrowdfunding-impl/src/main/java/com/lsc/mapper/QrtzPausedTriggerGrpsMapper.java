package com.lsc.mapper;

import com.lsc.bean.QrtzPausedTriggerGrpsExample;
import com.lsc.bean.QrtzPausedTriggerGrpsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QrtzPausedTriggerGrpsMapper {
    long countByExample(QrtzPausedTriggerGrpsExample example);

    int deleteByExample(QrtzPausedTriggerGrpsExample example);

    int deleteByPrimaryKey(QrtzPausedTriggerGrpsKey key);

    int insert(QrtzPausedTriggerGrpsKey record);

    int insertSelective(QrtzPausedTriggerGrpsKey record);

    List<QrtzPausedTriggerGrpsKey> selectByExample(QrtzPausedTriggerGrpsExample example);

    int updateByExampleSelective(@Param("record") QrtzPausedTriggerGrpsKey record, @Param("example") QrtzPausedTriggerGrpsExample example);

    int updateByExample(@Param("record") QrtzPausedTriggerGrpsKey record, @Param("example") QrtzPausedTriggerGrpsExample example);
}