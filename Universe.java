package com.ladislav;

/**
 * Created on 16.02.2017.
 * This class implements bitset functionality.
 * @author Ladislav
 */

// TODO (1) Implement Universe with bits instead of boolean array

public class Universe {

    private final int power;
    private final boolean[] elements;

    public Universe(int power) {

        if (power < 0) {
            System.out.println("Argument passed less than 0, power set to 10");
            this.power = 10;
        } else {
            this.power = power;
        }

        elements = new boolean[power];
    }

    // TODO (2) Implement method union() that doesn't change this object
    public boolean union(Universe other){

        if (this.getPower() == other.getPower()) {                          // if their powers are equal
            for(int i = 0; i < this.getPower(); i++) {

                this.elements[i] = this.elementExists(i) || other.elementExists(i);

            }
            return true;
        }

        return false;
    }
    // TODO (3) Implement method intersection() that doesn't change this object
    public boolean intersection(Universe other) {

        if (this.getPower() == other.getPower()) {

            for(int i = 0; i < this.getPower(); i++) {
                this.elements[i] = this.elementExists(i) && other.elementExists(i);
            }

        }

        return false;
    }
    // TODO (4) Implement methods  complement()


    public boolean addElement(int element) {

        if (element < power && !elements[element]) {
            elements[element] = true;
            return true;
        }

        return false;
    }

    public boolean removeElement(int element) {

        if (element < power && elements[element]) {
            elements[element] = false;
            return true;
        }

        return false;
    }

    public boolean elementExists(int element) {
        return element < power && elements[element];
    }

    public int getPower() {
        return power;
    }
}



