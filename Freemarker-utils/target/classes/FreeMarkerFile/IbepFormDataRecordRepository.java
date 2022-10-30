package ${rootPath}.common.dal.repository;

import ${rootPath}.dal.ibizecoprod.dataobject.${mock.TableName}DO;

import java.util.List;

/**
 * @author lisc
 * @Description: ${rootPath}.common.dal.repository
 * @date 2022/10/17 11:30
 */
public interface ${mock.TableName}Repository {
    /**
     *  Query DB table <tt>${mock.TableName}</tt> for records.
     *
     *	@param ipayUserId ipayUserId
     *	@param serviceCode serviceCode
     *	@return List<IbepFormDataRecordDO>
     */
    List<${mock.TableName}DO> selectByUserIdAndServiceCode(String ipayUserId, String serviceCode);

    /**
     *  Update DB table <tt>ibep_form_data_records</tt>.
     *
     *	@param ibepFormDataRecord ibepFormDataRecord
     *	@return int
     */
    int updateById(${mock.TableName}RecordDO ${mock.TableName?uncap_first});

    /**
     *  Insert one <tt>${mock.TableName}DO</tt> object to DB table <tt>${mock.TableName}</tt>, return primary key
     *
     *	@param ibepFormDataRecord ibepFormDataRecord
     *	@return String
     */
    String insert(${mock.TableName}DO ${mock.TableName?uncap_first});
}
