package br.com.siam;

import org.mindrot.jbcrypt.BCrypt;

public class PassGenerate {
    public static void main(String[] args) {
        String passwordHash = BCrypt.hashpw("userpass", BCrypt.gensalt());

        System.out.println(passwordHash);
    }
}