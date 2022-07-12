package com.codebandit.authsimplified.abstractions.services;

import com.codebandit.authsimplified.exceptions.SecureResourceNotFoundException;
import com.codebandit.authsimplified.abstractions.dto.SecureResource;

public interface SecureResourceService {
    SecureResource findByIdAndType(Long resourceId, String resourceType) throws SecureResourceNotFoundException;
}
