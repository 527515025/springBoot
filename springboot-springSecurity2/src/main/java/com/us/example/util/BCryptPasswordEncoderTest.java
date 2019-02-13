package com.us.example.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by yangyibo on 17/3/2.
 */
public class BCryptPasswordEncoderTest {
    public static void main(String[] args) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("encoder:  " + encoder.encode("abel"));
        System.out.println("encoder:  " + encoder.encode("admin"));

        if (encoder.matches("abel", "$2a$10$IAz6WzJ314LH1NXq7Rf.dOYPP2uvzk08g.eAl9l4DRG4YsxavEV4W")) {
            System.out.println("encoder: true");
        }


        System.out.println("------------华丽的分割线-----------------------");
        String Md5Password = MD5Util.encode("abel");
        System.out.println("Md5Password:  " + Md5Password);
        System.out.println("encoder:  " + encoder.encode(Md5Password));
        if (encoder.matches(Md5Password, "$2a$10$37MXEfzlbtC6QSsRTlRhIOmykMRJtO5mU8Y.yiJBjy1x4WYWFR5gG")) {
            System.out.println("Md5Password: true");
        }

    }
}
