package com.codebandit.authsimplified.implementations.security.jwt;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class KeyGenerator {
    public RSAPrivateKey getPrivateKeyFromKeyAtPath(String privateKeyFileName) throws GeneralSecurityException, IOException {
        File file = ResourceUtils.getFile(privateKeyFileName);
        byte[] encoded = Base64.decodeBase64(Files.readAllBytes(file.toPath()));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) kf.generatePrivate(keySpec);
    }

    public RSAPublicKey getPublicKeyFromKeyAtPath(String publicKeyFileName) throws GeneralSecurityException, IOException {
        File file = ResourceUtils.getFile(publicKeyFileName);
        byte[] encoded = Base64.decodeBase64(Files.readAllBytes(file.toPath()));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
    }
}
