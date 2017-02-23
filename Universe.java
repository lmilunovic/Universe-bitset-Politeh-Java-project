package com.ladislav;

import java.util.Objects;

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

            int bitSetPos = element / BYTE_SIZE;
            byte bitSetValue = bitSet[bitSetPos];

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

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o instanceof Universe) {
            Universe other = (Universe)o;
            if (this.size() != other.size()) {
                return false;
            }
            for(int i = 0; i < this.size(); i++) {
                if (this.elementExists(i) && other.elementExists(i) ||
                        !this.elementExists(i) && !other.elementExists(i)) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfBits, bitSet, BYTE_SIZE);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for(int i = 0; i < this.size(); i++) {
            if (this.elementExists(i)) {
                if (i == this.size() - 1) {
                    sb.append(i + "");
                } else {
                    sb.append(i + ", ");
                }
            }
        }
        return sb.append("]").toString();
    }
}
