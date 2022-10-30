package ${rootPath}.common.dal.repository.impl;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.ipay.ibizecoprod.common.dal.repository.IbepFormDataRecordRepository;
import com.ipay.ibizecoprod.dal.ibizecoprod.daointerface.IbepFormDataRecordDAO;
import com.ipay.ibizecoprod.dal.ibizecoprod.dataobject.IbepFormDataRecordDO;

import java.util.List;

/**
 * @author lisc
 * @Description: ${rootPath}.common.dal.repository.impl
 * @date ${date?datetime}
 */
public class ${mock.tableName}RepositoryImpl implements ${mock.tableName}Repository {

    @SofaReference
    private ${mock.tableName}RecordDAO ${mock.tableName?uncap_first}RecordDAO;

    @Override
    public List<${mock.tableName}RecordDO> selectByUserIdAndServiceCode(String ipayUserId, String serviceCode) {
        return ${mock.tableName?uncap_first}RecordDAO.selectByUserIdAndServiceCode(ipayUserId,serviceCode);
    }

    @Override
    public int updateById(IbepFormDataRecordDO ibepFormDataRecord) {
        return ${mock.tableName?uncap_first}RecordDAO.updateById(ibepFormDataRecord);
    }

    @Override
    public String insert(IbepFormDataRecordDO ibepFormDataRecord) {
        return ${mock.tableName?uncap_first}RecordDAO.insert(ibepFormDataRecord);
    }
}
