package com.codebandit.authsimplified.implementables.dto;

import com.codebandit.authsimplified.implementations.dto.RBACAuthority;

import java.util.List;

public interface SimpleIdentity {

    String getUsername();
    List<RBACAuthority> getRBACAuthoritiesList();

}
