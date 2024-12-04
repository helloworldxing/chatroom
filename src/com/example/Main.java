package com.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws Exception {
        MessageDigest instance= MessageDigest.getInstance("md5");
        byte[] bytes = instance.digest("1234".getBytes());
        String pwd= new String(bytes);
        System.out.println(pwd);
    }
}
