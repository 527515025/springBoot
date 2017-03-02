package com.us.example.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by yangyibo on 17/3/2.
 */
public class BCryptPasswordEncoderTest {
    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("abel"));
        System.out.println(encoder.encode("admin"));
    }
}
