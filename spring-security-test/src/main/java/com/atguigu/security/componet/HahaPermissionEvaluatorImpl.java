package com.atguigu.security.componet;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
public class HahaPermissionEvaluatorImpl implements PermissionEvaluator {

    /**
     * @PreAuthorize("hasPermission(#id, '2')")
     * public String leve1dsakjldjakl2Page(Integer id)
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        // TODO Auto-generated method stub
        if (targetDomainObject == null) return false;
        return targetDomainObject.toString().equals(permission.toString());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                                 Object permission) {
        // TODO Auto-generated method stub
        return false;
    }

}
