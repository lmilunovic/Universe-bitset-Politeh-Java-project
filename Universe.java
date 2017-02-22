package com.ladislav;

/**
 * Created by Ladislav on 2/22/2017.
 */
public class Universe {

    private static final int BYTE_SIZE = 8;

    private final byte[] bitSet;
    private final int numberOfBits;

    Universe(int numberOfBits) {
        assert numberOfBits >= 0;

        this.numberOfBits = numberOfBits;
        if (numberOfBits % BYTE_SIZE == 0) {
            bitSet = new byte[((numberOfBits / BYTE_SIZE))];
        } else {
            bitSet = new byte[numberOfBits / BYTE_SIZE + 1];
        }
    }


    public boolean elementExists(int element) {

        if (element >= 0 && element <= numberOfBits) {
            // 12
            int bitSetPos = element / BYTE_SIZE; // 12 / 8 = 1
            byte bitSetValue = bitSet[bitSetPos]; // vrijednost 80

            return ((bitSetValue >> (element - bitSetPos*BYTE_SIZE) & 1) == 1);

        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean setElement(int element) {

        if (!elementExists(element)) {
            int bitSetPos = element / BYTE_SIZE;
            byte bitSetByte = bitSet[bitSetPos];

            bitSetByte = (byte) (bitSetByte | (1 << element - bitSetPos*BYTE_SIZE));
            bitSet[bitSetPos] = bitSetByte;
            return true;
        }
        return false;
    }

    public boolean unsetElement(int element) {
        if (elementExists(element)) {
            int bitSetPos = element / BYTE_SIZE;
            byte bitSetByte = bitSet[bitSetPos];

            bitSetByte = (byte) (bitSetByte & ~(1 << element - bitSetPos*BYTE_SIZE));
            bitSet[bitSetPos] = bitSetByte;
            return true;
        }
        return false;
    }

    public Universe union(Universe other){
        if (this.size() == other.size()) {
            Universe newUniverse = new Universe(this.size());
            for(int i = 0; i < this.size(); i++) {
                if (this.elementExists(i) || other.elementExists(i)) {
                    newUniverse.setElement(i);
                }
            }
            return newUniverse;
        }
        return null;
    }

    public Universe intersection(Universe other) {
        if (this.size() == other.size()) {
            Universe newUniverse = new Universe(this.size());
            for(int i = 0; i < this.size(); i++) {
                if (this.elementExists(i) && other.elementExists(i)) {
                    newUniverse.setElement(i);
                }
            }
            return newUniverse;
        }
        return null;
    }

    public Universe complement(Universe other) {

        if (this.size() == other.size()) {
            Universe newUniverse = new Universe(this.size());
            for(int i = 0; i < this.size(); i++) {
                if (this.elementExists(i) && !other.elementExists(i)) {
                    newUniverse.setElement(i);
                }
            }
            return newUniverse;
        }
        return null;
    }

    public int size() {
        return numberOfBits;
    }


    // Quick testing of Complement, Union and Intersection
    // TODO test it with JUnit in Testing class
    public static void main(String[] args) {
        Universe univ1 = new Universe(50);
        Universe univ2 = new Universe (50);

        for(int i = 0; i < univ1.size(); i++) {
            if (i > 25) {
                univ1.setElement(i);
                univ2.setElement(i);
            } else {
                univ2.setElement(i);
            }
        }
        System.out.println("Universe 1");
        printUniverse(univ1);
        System.out.println("Universe 2");
        printUniverse(univ2);
        System.out.println("Universe complement");
        printUniverse(univ2.complement(univ1));
        System.out.println("Universe intersection");
        printUniverse(univ2.intersection(univ1));

        univ1 = new Universe(50);
        univ2 = new Universe (50);

        for(int i = 0; i < univ1.size(); i++) {
            if (i % 2 == 0) {
                univ1.setElement(i);
            } else {
                univ2.setElement(i);
            }
        }
        System.out.println("Universe 1");
        printUniverse(univ1);
        System.out.println("Universe 2");
        printUniverse(univ2);
        System.out.println("Universe union");
        printUniverse(univ2.union(univ1));
    }

    public static void printUniverse(Universe universe) {

        for(int i = 0; i < universe.size(); i++) {
            if (universe.elementExists(i)) {
                System.out.print(i + " ");
            } else {
                System.out.print("- ");
            }
        }
        System.out.println();

    }
}
