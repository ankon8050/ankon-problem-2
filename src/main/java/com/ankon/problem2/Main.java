package com.ankon.problem2;

import com.ankon.problem2.lexer.Lexer;

public class Main {

    public static void main(String[] args) {

        Lexer lexer = new Lexer("Input.java");

        System.out.println("Lexical Analysis");
        System.out.println("-----------------");
        while (!lexer.isExhausted()) {
            System.out.printf("%-18s :  %s \n",lexer.currentLexema() , lexer.currentToken());
            lexer.moveAhead();
        }

        if (lexer.isSuccessful()) {
            System.out.println("Ok! :D");
        } else {
            System.out.println(lexer.errorMessage());
        }
    }

}
