package $

{projectPath}.controller;

import com.alibaba.druid.support.json.JSONUtils;
import ${projectPath}.api.${ClassName}Service;
import ${projectPath}.bean.${MapClassName};
import ${projectPath}.constant.Constant;
import ${projectPath}.common.ExceptionUtils.LscException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @Description: ${ClassName}
 * @Author: lisc
 * @date: ${date?datetime}
 */
@Slf4j
public class $ {
    ClassName
}CrudController{


@Autowired
    ${ClassName}Service ${ClassName?uncap_first}Service;

@GetMapping("/user/batch/delete")
public String deleteBacthAdmin(@RequestParam("ids")String ids,HttpSession session,Model model){

        ${ClassName?uncap_first}Service.deleteAdmin(id);

        return"redirect:/admin/index.html";
        }

@PostMapping("user/add")
public String addAdmin(${MapClassName}${MapClassName?uncap_first},Model model){
        log.info("将要添加的用户为:{}",${MapClassName?uncap_first});
        try{
        ${ClassName?uncap_first}Service.savaAdmin(${MapClassName?uncap_first});
        }catch(LscException e){
        model.addAttribute(Constant.PAGE_MSG,e.getMsg());
        log.error("{}添加出错",${MapClassName?uncap_first},e);
        return"redirect:/permission/user-add";
        }

        return"redirect:/admin/index.html?pn="+Integer.MAX_VALUE;
        }


@GetMapping("user/add.html")
public String addPage(${MapClassName}admin){
        // 来到用户添加页
        return"permission/user-add";
        }

@GetMapping("/user/edit.html")
public String toEdiPage(@RequestParam("id") Integer id,Model model){
        ${MapClassName}admin=${ClassName?uncap_first}Service.selectById(id);

        return"permission/user-edit";
        }

/**
 * 用户修改请求
 *
 * @return
 */
@PostMapping("/user/update")
public String adminUpdate(${MapClassName}admin,HttpSession session,Model model){
        log.info("接收到:{},修改用户信息",admin);
        ${ClassName?uncap_first}Service.updateAdmin(admin);

        return"redirect:/admin/index.html";
        }
        }
