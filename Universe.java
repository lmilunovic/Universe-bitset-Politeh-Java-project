package com.ladislav;

/**
 * Created by student on 16.02.2017.
 */
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

    // Universe
    public boolean union(Universe other){

        if (this.getPower() == other.getPower()) {                          // if their powers are equal
            for(int i = 0; i < this.getPower(); i++) {

                this.elements[i] = this.elementExists(i) || other.elementExists(i);

            }
            return true;
        }

        return false;
    }

    public boolean intersection(Universe other) {

        if (this.getPower() == other.getPower()) {

            for(int i = 0; i < this.getPower(); i++) {
                this.elements[i] = this.elementExists(i) && other.elementExists(i);
            }

        }

        return false;
    }

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
        return element < power ? elements[element]:false;
    }

    public int getPower() {
        return power;
    }
}



