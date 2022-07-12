package com.codebandit.authsimplified.implementations.security;

import com.codebandit.authsimplified.enums.Permission;
import com.codebandit.authsimplified.abstractions.dto.SecureResource;
import com.codebandit.authsimplified.implementations.security.rbac.RBACService;
import com.codebandit.authsimplified.implementations.dto.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("permissionEvaluator")
public class PermissionEvaluatorImpl implements PermissionEvaluator {

    private final RBACService rbacService;

    @Autowired
    public PermissionEvaluatorImpl(RBACService rbacService) {
        this.rbacService = rbacService;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        if (authentication != null) {
            UserDetailsImpl loggedUserDetails = (UserDetailsImpl) authentication.getPrincipal();
            SecureResource secureResource = (SecureResource) targetDomainObject;

            //Gathering user details and user id of the logged in user
            //access denied if user not logged in

            Permission permission1 = (Permission) permission;
            boolean returnValue = false;

            switch(permission1) {
                case READ: returnValue = rbacService.canUserReadResource(secureResource, loggedUserDetails.getSimpleIdentity());
                break;
                case WRITE: returnValue = rbacService.canUserEditToResource(secureResource, loggedUserDetails.getSimpleIdentity());
                break;
                case DELETE: returnValue = rbacService.canUserDeleteResource(secureResource, loggedUserDetails.getSimpleIdentity());
            }

            return returnValue;
        }
        return false;

    }


    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        SecureResource secureResource = rbacService.getSecureResource((Long) targetId, targetType);
        return hasPermission(authentication, secureResource, permission);
    }

}

