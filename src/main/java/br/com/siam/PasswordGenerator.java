package br.com.siam;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordGenerator {
    public static void main(String[] args) {
        String hashedPassword = BCrypt.hashpw("teste123", BCrypt.gensalt());

        System.out.println(hashedPassword);
    }
}
