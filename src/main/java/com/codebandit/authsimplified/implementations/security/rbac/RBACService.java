package com.codebandit.authsimplified.implementations.security.rbac;

import com.codebandit.authsimplified.enums.RBACAuthorityType;
import com.codebandit.authsimplified.exceptions.SecureResourceNotFoundException;
import com.codebandit.authsimplified.abstractions.services.SecureResourceService;
import com.codebandit.authsimplified.implementations.dto.RBACAuthority;
import com.codebandit.authsimplified.abstractions.dto.SecureResource;
import com.codebandit.authsimplified.abstractions.dto.SimpleIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RBACService {
    private final SecureResourceService secureResourceService;

    @Autowired
    public RBACService(SecureResourceService secureResourceService) {
        this.secureResourceService = secureResourceService;
    }

    public final boolean canUserReadResource(SecureResource secureResource, SimpleIdentity simpleIdentity) {
        return simpleIdentity.getRBACAuthoritiesList() != null || simpleIdentity.getRBACAuthoritiesList().isEmpty();
    }
    public final boolean canUserEditToResource(SecureResource secureResource, SimpleIdentity simpleIdentity) {
        if (secureResource.getResourceOwner().getUsername().equals(simpleIdentity.getUsername())) {
            return canUserReadResource(secureResource, simpleIdentity) && simpleIdentity.getRBACAuthoritiesList().stream()
                    .map(RBACAuthority::getRBACAuthorityType)
                    .anyMatch(rbacAuthorityType -> rbacAuthorityType == RBACAuthorityType.CONTRIBUTOR);
        }
        return false;
    }
    public boolean canUserDeleteResource(SecureResource secureResource, SimpleIdentity simpleIdentity) {
        if (secureResource.getResourceOwner().getUsername().equals(simpleIdentity.getUsername())) {
            return canUserReadResource(secureResource, simpleIdentity) && simpleIdentity.getRBACAuthoritiesList().stream()
                    .map(RBACAuthority::getRBACAuthorityType)
                    .anyMatch(rbacAuthorityType -> rbacAuthorityType == RBACAuthorityType.CONTRIBUTOR
                            || rbacAuthorityType == RBACAuthorityType.OWNER);
        }
        return false;
    }

    public SecureResource getSecureResource(Long id, String resourceType) throws SecureResourceNotFoundException {
        return secureResourceService.findByIdAndType(id, resourceType);
    }
}
