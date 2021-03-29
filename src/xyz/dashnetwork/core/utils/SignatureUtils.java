package xyz.dashnetwork.core.utils;

import java.io.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class SignatureUtils {

    private static ClassLoader loader = SignatureUtils.class.getClassLoader();

    public static boolean verify(byte[] data, byte[] signed) {
        try {
            InputStream resource = loader.getResourceAsStream("keys/public.key");
            ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(resource));
            BigInteger modulus = (BigInteger) stream.readObject();
            BigInteger exponent = (BigInteger) stream.readObject();

            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey key = factory.generatePublic(new RSAPublicKeySpec(modulus, exponent));

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(key);
            signature.update(data);

            return signature.verify(signed);
        } catch (Exception exception) {
            return false;
        }
    }

    public static byte[] sign(byte[] data) {
        try {
            InputStream resource = loader.getResourceAsStream("keys/private.key");
            ObjectInputStream stream = new ObjectInputStream(new BufferedInputStream(resource));
            BigInteger modulus = (BigInteger) stream.readObject();
            BigInteger exponent = (BigInteger) stream.readObject();

            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey key = factory.generatePrivate(new RSAPrivateKeySpec(modulus, exponent));

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(key);
            signature.update(data);

            return signature.sign();
        } catch (Exception exception) {
            exception.printStackTrace();

            return new byte[256];
        }
    }

}
