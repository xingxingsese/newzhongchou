package ${rootPath}.${packagePath}.facade;

import ${rootPath}.${packagePath}.request.voyage.Query${mock.className}Request;
import ${rootPath}.${packagePath}.request.voyage.Save${mock.className}Request;
import ${rootPath}.${packagePath}.response.voyage.Query${mock.className}Response;
import ${rootPath}.${packagePath}.response.voyage.Save${mock.className}Response;

/**
 * @author: lisc
 * @description: ${mock.className}Facade
 * @date: ${date?datetime}
 */
public interface ${mock.className}Facade {

  /**
   * save${mock.className}
   *
   * @param request
   * @return
   */
  Save${mock.className}Response save${mock.className}(Save${mock.className}Request request);

  /**
   * query${mock.className}
   *
   * @param request
   * @return
   */
  Query${mock.className}Response query${mock.className}(Query${mock.className}Request request);

  /**
   * update{ClassName}
   *
   * @param request
   * @return
   */
  Upate${mock.className}Response update{ClassName}(Upate${mock.className}Request request);

  /**
   * delete${mock.className}
   *
   * @param request
   * @return
   */
  Delete${mock.className}Response delete${mock.className}(Delete${mock.className}Request request);

}
