package ${mock.rootPath}.biz.service.impl;


/**
 * @author: lisc
 * @description: ${mock.className}Facade
 * @date: ${date?datetime}
 */
public class $ {
    mock.className
}FacadeImpl implements ${mock.className}Facade{

@SofaReference
private ${mock.className}Service ${mock.className?uncap_first}Service;

/**
 * save${mock.className}
 *
 * @param request
 * @return
 */
@Override
public Save${mock.className}Response save${mock.className}(Save${mock.className}Request request){
        return ${mock.className?uncap_first}Service.save${mock.className}(request);
        }

/**
 * query${mock.className}
 * @param request
 * @return
 */
@Override
public Query${mock.className}Response query${mock.className}(Query${mock.className}Request request){
        return ${mock.className?uncap_first}Service.query${mock.className}(request);
        }

/**
 * update{ClassName}
 * @param request
 * @return
 */
@Override
public Upate${mock.className}Response update{ClassName}(Upate${mock.className}Request request){
        return ${mock.className?uncap_first}Service.query${mock.className}(request);
        }

/**
 * delete${mock.className}
 * @param request
 * @return
 */
@Override
public Delete${mock.className}Response delete${mock.className}(Delete${mock.className}Request request){
        return ${mock.className?uncap_first}Service.query${mock.className}(request);
        }
        }
