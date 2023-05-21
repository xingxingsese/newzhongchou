package $

{mock.rootPath}.core.service.impl;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.ipay.ialicore.common.event.EventContextUtil;
import com.ipay.ialicore.common.log.BaseDigestLog;
import com.ipay.ialicore.common.util.assertion.AssertUtil;
import com.ipay.ialicore.common.util.log.LogUtil;
import com.ipay.ibizecoprod.common.util.JSONUtil;
import com.ipay.ibizecoprod.common.util.constants.CommonConstant;
import com.ipay.ibizecoprod.common.util.constants.LoggerConstants;
import com.ipay.ibizecoprod.common.util.error.BizeBizprodErrorCode;
import org.springframework.util.CollectionUtils;


/**
 * @author lisc
 * @Description: ${mock.className}ServiceImpl
 * @date ${date?datetime}
 */
public class $ {
    mock.className
}ServiceImpl implements ${mock.className}Service{

/**
 * ibep_form_data_records 开店申请单数据暂存
 */
@SofaReference
private ${mock.tableName}Repository ${mock.tableName?uncap_first}Repository;


/**
 * 保存
 *
 * @param request
 * @return
 */
@Override
public Save${mock.className}Response save${mock.className}(Save${mock.className}Request request){
        Save${mock.className}Response response=new Save${mock.className}Response();
        ServiceTemplate.execute(request,response,new BizServiceCallback(){

@Override
public void beforeService(){
        AssertUtil.notNull(request,BizeBizprodErrorCode.REQUEST_NULL_EXCEPTION);
        }

@Override
public void executeService()throws Exception{


        }

@Override
public BaseDigestLog afterService(){
        LogUtil.info(LoggerConstants.IBIZECOPROD_BIZ_CORE_SERVICE,String.format("MpOpenMarketPlaceServiceImpl:save${mock.className}:end,  [request]:%s [response]:%s",
        JSONUtil.objectToString(request),JSONUtil.objectToString(response)));
        return null;
        }
        });
        return response;
        }

/**
 * 查询
 *
 * @param request
 * @return
 */
@Override
public Query${mock.className}Response query${mock.className}(Query${mock.className}Request request){
        Query${mock.className}Response response=new Query${mock.className}Response();
        ServiceTemplate.execute(request,response,new BizServiceCallback(){
@Override
public void beforeService(){
        AssertUtil.notNull(request,BizeBizprodErrorCode.REQUEST_NULL_EXCEPTION);
        }

@Override
public void executeService()throws Exception{


        }

@Override
public BaseDigestLog afterService(){
        LogUtil.info(LoggerConstants.IBIZECOPROD_BIZ_CORE_SERVICE,
        String.format("${mock.className}ServiceImpl:query${mock.className}:end, [request]:%s [response]:%s",
        JSONUtil.objectToString(request),JSONUtil.objectToString(response)));
        return null;
        }
        });
        return response;
        }

/**
 * 修改
 *
 * @param request
 * @return
 */
@Override
public Upate${mock.className}Response upate${mock.className}(Upate${mock.className}Request request){
        Upate${mock.className}Response response=new Upate${mock.className}Response();
        ServiceTemplate.execute(request,response,new BizServiceCallback(){
@Override
public void beforeService(){
        AssertUtil.notNull(request,BizeBizprodErrorCode.REQUEST_NULL_EXCEPTION);
        }

@Override
public void executeService()throws Exception{


        }

@Override
public BaseDigestLog afterService(){
        LogUtil.info(LoggerConstants.IBIZECOPROD_BIZ_CORE_SERVICE,
        String.format("${mock.className}ServiceImpl:upate${mock.className}:end, [request]:%s [response]:%s",
        JSONUtil.objectToString(request),JSONUtil.objectToString(response)));
        return null;
        }
        });
        return response;
        }


/**
 * 查询
 *
 * @param request
 * @return
 */
@Override
public Delete${mock.className}Response delete${mock.className}(Delete${mock.className}Request request){
        Delete${mock.className}Response response=new Delete${mock.className}Response();
        ServiceTemplate.execute(request,response,new BizServiceCallback(){
@Override
public void beforeService(){
        AssertUtil.notNull(request,BizeBizprodErrorCode.REQUEST_NULL_EXCEPTION);
        }

@Override
public void executeService()throws Exception{


        }

@Override
public BaseDigestLog afterService(){
        LogUtil.info(LoggerConstants.IBIZECOPROD_BIZ_CORE_SERVICE,
        String.format("${mock.className}ServiceImpl:delete${mock.className}:end, [request]:%s [response]:%s",
        JSONUtil.objectToString(request),JSONUtil.objectToString(response)));
        return null;
        }
        });
        return response;
        }


        }
