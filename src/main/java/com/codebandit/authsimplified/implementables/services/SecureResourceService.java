package com.codebandit.authsimplified.implementables.services;

import com.codebandit.authsimplified.exceptions.SecureResourceNotFoundException;
import com.codebandit.authsimplified.implementables.dto.SecureResource;

public interface SecureResourceService {
    SecureResource findByIdAndType(Long resourceId, String resourceType) throws SecureResourceNotFoundException;
}
