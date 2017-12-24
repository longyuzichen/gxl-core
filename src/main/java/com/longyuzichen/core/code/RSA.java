/**
 * Copyright [2017] guoxinlei(longyuzichen@126.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package com.longyuzichen.core.code;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * com.longyuzichen.core.code
 *
 * @author longyuzichen@126.com
 * @version V1.0
 * @desc RSA签名工具类
 * @date 2017-03-24 23:49
 */
public class RSA {

    private static final Logger log = LoggerFactory.getLogger(RSA.class);

    /**
     * RSA签名
     *
     * @param rsaPrivateKey
     * @param contents
     * @return
     */
    public static String rsaSign(RSAPrivateKey rsaPrivateKey, String contents) {
        String result = null;
        //执行签名
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            signature.update(contents.getBytes());
            byte[] sign = signature.sign();
            result = Hex.encodeHexString(sign);
        } catch (NoSuchAlgorithmException e) {
            log.error("RSA签名异常！", e);
        } catch (InvalidKeySpecException e) {
            log.error("RSA签名初始化异常！", e);
        } catch (SignatureException e) {
            log.error("RSA签名异常！", e);
        } catch (InvalidKeyException e) {
            log.error("RSA签名异常！", e);
        }
        return result;
    }

    /**
     * 校验RSA密钥
     *
     * @param rsaPublicKey
     * @param contents
     * @param sign
     * @return
     */
    public static boolean verifyRSASign(RSAPublicKey rsaPublicKey, String contents, byte[] sign) {
        boolean result = true;
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Signature signature = Signature.getInstance("MD5WithRSA");
            signature.initVerify(publicKey);
            signature.update(contents.getBytes());
            result = signature.verify(sign);
        } catch (NoSuchAlgorithmException e) {
            log.error("RSA校验异常！", e);
        } catch (InvalidKeySpecException e) {
            log.error("RSA校验初始化异常！", e);
        } catch (InvalidKeyException e) {
            log.error("RSA校验异常！", e);
        } catch (SignatureException e) {
            log.error("RSA校验异常！", e);
        }
        return result;
    }

    /**
     * 获取私钥
     *
     * @return
     */
    public static RSAPrivateKey getPrivateKey() {
        KeyPair keyPair = initKeyPair();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        return rsaPrivateKey;
    }

    /**
     * 获取公钥
     *
     * @return
     */
    public static RSAPublicKey getPublicKey() {
        KeyPair keyPair = initKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        return rsaPublicKey;
    }

    /**
     * 初始化密钥
     *
     * @return
     */
    private static KeyPair initKeyPair() {
        KeyPair keyPair = null;
        try {
            //初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            keyPair = keyPairGenerator.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            log.error("RSA初始化密钥异常！", e);
            return null;
        }
        return keyPair;
    }

    public static void main(String[] args) {
        String contents = "woshishuei";
        RSAPublicKey publicKey = getPublicKey();
        RSAPrivateKey privateKey = getPrivateKey();
        String sign = rsaSign(privateKey, contents);
        boolean bool = verifyRSASign(publicKey, contents, sign.getBytes());
        System.out.print("publicKey=" + publicKey + "\nprivateKey=" + privateKey + "\nsign=" + sign + "\nbool" + bool);
    }
}
