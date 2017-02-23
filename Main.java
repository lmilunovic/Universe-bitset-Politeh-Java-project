package com.ladislav;

public class Main {

    public static void main(String[] args) {
        Universe u = new Universe(20);
        for (int i = 0; i < 20 ; i++) {
            u.setElement(i);
        }
        System.out.println(u.toString());
    }
}
