package cn.abel.rest.shiro.credentials;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 密码
 *
 */
public class PasswordHelper {

    private static String algorithmName = "md5";
    private static int hashIterations = 2;

    public void setAlgorithmName(String algorithmName) {
        PasswordHelper.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        PasswordHelper.hashIterations = hashIterations;
    }

    public static String encryptPassword(String userName, String password) {
        return new SimpleHash(algorithmName, password, ByteSource.Util.bytes(userName), hashIterations).toHex();
    }

}
