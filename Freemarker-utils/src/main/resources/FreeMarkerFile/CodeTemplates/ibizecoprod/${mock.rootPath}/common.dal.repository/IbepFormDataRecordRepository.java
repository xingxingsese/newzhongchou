package $

{mock.rootPath}.common.dal.repository;

import ${mock.rootPath}.dal.ibizecoprod.dataobject.${mock.tableName}DO;

import java.util.List;

/**
 * @author lisc
 * @Description: ${mock.rootPath}.common.dal.repository
 * @date: ${date?datetime}
 */
public interface $ {
    mock.tableName
}Repository{
        /**
         *  Query DB table <tt>${mock.tableName}</tt> for records.
         *
         *    @param ipayUserId ipayUserId
         *    @param serviceCode serviceCode
         *    @return List<IbepFormDataRecordDO>
         */
        List<${mock.tableName}DO>selectByUserIdAndServiceCode(String ipayUserId,String serviceCode);

        /**
         *  Update DB table <tt>${mock.tableName}</tt>.
         *
         *    @param ibepFormDataRecord ibepFormDataRecord
         *    @return int
         */
        int updateById(${mock.tableName}RecordDO ${mock.tableName?uncap_first});

        /**
         *  Insert one <tt>${mock.tableName}DO</tt> object to DB table <tt>${mock.tableName}</tt>, return primary key
         *
         *    @param ibepFormDataRecord ibepFormDataRecord
         *    @return String
         */
        String insert(${mock.tableName}DO ${mock.tableName?uncap_first});
        }
