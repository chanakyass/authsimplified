package com.codebandit.authsimplified.implementables.dto;

public interface SecureResource {
    Long getResourceId();
    String getResourceType();
    SimpleIdentity getResourceOwner();
}
