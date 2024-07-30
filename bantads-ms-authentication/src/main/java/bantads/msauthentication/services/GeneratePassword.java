package bantads.msauthentication.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import org.apache.commons.lang3.RandomStringUtils;

public class GeneratePassword {

    public String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(16);
    }

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public String hashPassword(String password, String salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String saltedPassword = password + salt;
        byte[] hash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

}