/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.danilore.piniaterializzety.clases;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ASUS
 */
public class HashGenerator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String plainPassword = "danilore123";
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        System.out.println("Hashed Password: " + hashedPassword);
    }
    
}
