package $

{projectPath}.api;

import com.lsc.bean.TAdmin;
import com.lsc.bean.TMenu;

import java.util.List;

/**
 * @Description:
 * @Author: lisc
 * @date: ${date?datetime}
 */
public interface $ {
    ClassName
}Service{
        ${MapClassName}selectLogInfo(String userName,String passWord);

        List<${MapClassName}>selectListMenus();

        List<${MapClassName}>listAllAdminByCondition(String condition);

        ${MapClassName}selectById(Integer id);

        void updateAdmin(${MapClassName}${MapClassName?uncap_first});

        void savaAdmin(${MapClassName}${MapClassName?uncap_first});

        boolean checkLogAccount(${MapClassName}${MapClassName?uncap_first});

        boolean checkemail(${MapClassName}${MapClassName?uncap_first});

        void deleteAdmin(Integer id);
        }
