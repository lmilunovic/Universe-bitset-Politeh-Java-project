package com.ladislav;

import static com.ladislav.Universe.printUniverse;

public class Main {

    public static void main(String[] args) {

        // Testing all methods
        // TODO Test those methods in Testing function using JUnit

        System.out.println("Creating universe of size 15.");
        Universe universe = new Universe(15);

        System.out.println("Testing size() " + universe.size());

        System.out.println("Setting elements 12, 5, 0, 14 - True expected 4 times: ");
        System.out.println(universe.setElement(0));
        System.out.println(universe.setElement(5));
        System.out.println(universe.setElement(12));
        System.out.println(universe.setElement(14));

        System.out.println("Setting elements 12, 5 - False expected 2 times: ");
        System.out.println(universe.setElement(5));
        System.out.println(universe.setElement(12));

        System.out.println("Testing elementExist() - True expected 4 times: ");
        System.out.println(universe.elementExists(0));
        System.out.println(universe.elementExists(5));
        System.out.println(universe.elementExists(12));
        System.out.println(universe.elementExists(14));

        System.out.println("Testing elementExist() - False expected 4 times: ");
        System.out.println(universe.elementExists(1));
        System.out.println(universe.elementExists(3));
        System.out.println(universe.elementExists(13));
        System.out.println(universe.elementExists(2));

        System.out.println("Testing union()");

            Universe univ = new Universe(8);
            Universe otherUniv = new Universe(8);

            univ.setElement(0);
            univ.setElement(1);
            otherUniv.setElement(1);
            otherUniv.setElement(2);
            otherUniv.setElement(3);

            Universe union = univ.union(otherUniv);
            System.out.print("Universe 1: ");
            printUniverse(univ);
            System.out.print("Universe 2: ");
            printUniverse(otherUniv);
            System.out.print("Union:    ");
            printUniverse(union);

        System.out.println("Testiong intersection()");

            univ = new Universe(8);
            otherUniv = new Universe(8);

            univ.setElement(0);
            univ.setElement(1);
            univ.setElement(2);
            univ.setElement(4);
            otherUniv.setElement(0);
            otherUniv.setElement(1);
            otherUniv.setElement(3);
            otherUniv.setElement(5);

            Universe intersection = univ.intersection(otherUniv);
            System.out.print("Universe 1:   ");
            printUniverse(univ);
            System.out.print("Universe 2:   ");
            printUniverse(otherUniv);
            System.out.print("Intersection: ");
            printUniverse(intersection);

        System.out.println("Testiong complement() ");

            univ= new Universe(8);
            otherUniv = new Universe(8);

            univ.setElement(0);
            univ.setElement(1);
            univ.setElement(2);
            univ.setElement(4);
            otherUniv.setElement(0);
            otherUniv.setElement(1);
            otherUniv.setElement(3);
            otherUniv.setElement(5);

            Universe complement = univ.complement(otherUniv);

            System.out.print("Universe 1: ");
            printUniverse(univ);
            System.out.print("Universe 2: ");
            printUniverse(otherUniv);
            System.out.print("Complement: ");
            printUniverse(union);
    }
}
