package org.acme.authorizationserver.fixture;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class PublicKeyUtils {

    private static final KeyPairGenerator KEY_PAIR_GENERATOR;

    /**
     * The security key algorithm used by this application
     *
     * @see KeyFactory#getAlgorithm()
     * @see java.security.KeyPairGenerator#getAlgorithm()
     */
    public static final String KEY_ALGORITHM = "RSA";

    static {
        try {
            KEY_PAIR_GENERATOR = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public static PublicKey generatePublicKey() {
        return getKeyPair().getPublic();
    }

    public static String generatePublicKeyEncoded() {
        return Base64.getEncoder()
                     .encodeToString(generatePublicKey().getEncoded());
    }

    public static KeyPair getKeyPair() {
        return KEY_PAIR_GENERATOR.generateKeyPair();
    }
}
