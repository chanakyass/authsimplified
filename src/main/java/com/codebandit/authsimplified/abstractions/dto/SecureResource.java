package com.codebandit.authsimplified.abstractions.dto;

public interface SecureResource {
    Long getResourceId();
    String getResourceType();
    SimpleIdentity getResourceOwner();
}
