package com.ladislav;

import java.util.Objects;

/**
 * Implements set on fixed universe. Power of the universe is set in constructor.
 * Element of the set is positive integer.
 * Implements oprations: intersection, union, complement, add/remove element or array of elements and existance of element
 *
 * @author Ladislav
 * @version 2.1
 * @since 12/23/2017
 */

public class Universe {

    private static final int BYTE_SIZE = 8;

    private final byte[] bitSet;
    private final int power;

    Universe(int power) {
        assert power > 0;

        this.power = power;
        if (power % BYTE_SIZE == 0) {
            bitSet = new byte[((power / BYTE_SIZE))];
        } else {
            bitSet = new byte[power / BYTE_SIZE + 1];
        }
    }

    /**
     * *Checks if element exists in Universe or not
     * @param element, integer
     * @return true if exists, false if doesn't
     * @throws ArrayIndexOutOfBoundsException if element size is not in bounds of Universe power
     */

    public boolean elementExists(int element) {

        if (element >= 0 && element <= power) {

            int bitSetPos = element / BYTE_SIZE;
            byte bitSetValue = bitSet[bitSetPos];

            return ((bitSetValue >> (element - bitSetPos*BYTE_SIZE) & 1) == 1);

        } else {
            throw new IndexOutOfBoundsException();
        }
    }
    /**
     * Adds certain element to set.
     * @param element integer to add  to set
     * @return boolean, true if added, false if not
     * @throws IndexOutOfBoundsException (elementExsists()) when nubmer is greather than Unvierse power
     */
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

    /**
     * Adds array of elements to set.
     * @param elements array of elements to add to set
     * @return integer, number of addeds elements, -1 if array length is larger than Universe power
     * @throws IndexOutOfBoundsException (elementExsists()) when nubmer is greather than Unvierse power
     */

    public int setElement(int[] elements) {

        int counter = 0;

        if (elements.length <= this.power()) {
            for (int elementToSet : elements) {
                if (elementToSet < this.power() && !this.elementExists(elementToSet)) {
                    setElement(elementToSet);
                    counter++;
                }
            }
            return counter;
        }
        return -1;
    }

    /**
     * Removes certain element from set.
     * @param element integer to remove from set
     * @return boolean, true if removed, false if not
     * @throws IndexOutOfBoundsException (elementExsists()) when nubmer is greather than Unvierse power
     */

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

    /**
     * Removes array of elements from set.
     * @param elements array of elements to remove
     * @return number of elements removed, -1 if array length is greater than Universe power
     */

    public int unsetElement(int[] elements) {

        int counter = 0;
        if (elements.length <= this.power()) {
            for (int elementToUnset : elements) {
                if (elementToUnset < this.power() && this.elementExists(elementToUnset)) {
                    unsetElement(elementToUnset);
                    counter++;
                }
            }
            return counter;
        }
        return -1;
    }

    /**
     * *Implements union set arithmetic
     * @param other accepts Universe object that has to have same power as one uniting with
     * @return Returns union of to Universes or null if their power differs
     */
    public Universe union(Universe other){
        if (this.power() == other.power()) {
            Universe newUniverse = new Universe(this.power());
            for(int i = 0; i < this.power(); i++) {
                if (this.elementExists(i) || other.elementExists(i)) {
                    newUniverse.setElement(i);
                }
            }
            return newUniverse;
        }
        return null;
    }

    /**
     * *Implements intersection set arithmetic
     * @param other accepts Universe object that has to have same power as one intersectingwith
     * @return Returns intersection of to Universes or null if their power differs
     */
    public Universe intersection(Universe other) {
        if (this.power() == other.power()) {
            Universe newUniverse = new Universe(this.power());
            for(int i = 0; i < this.power(); i++) {
                if (this.elementExists(i) && other.elementExists(i)) {
                    newUniverse.setElement(i);
                }
            }
            return newUniverse;
        }
        return null;
    }

    /**
     * *Implements complement set arithmetic
     * @param other accepts Universe object that has to have same power as one intersectingwith
     * @return Returns intersection of to Universes or null if their power differs
     */
    public Universe complement(Universe other) {

        if (this.power() == other.power()) {
            Universe newUniverse = new Universe(this.power());
            for(int i = 0; i < this.power(); i++) {
                if (this.elementExists(i) && !other.elementExists(i)) {
                    newUniverse.setElement(i);
                }
            }
            return newUniverse;
        }
        return null;
    }

    /**
     * @return integer, number of possible elements in the Universe
     */
    public int power() {
        return power;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o instanceof Universe) {
            Universe other = (Universe)o;
            if (this.power() != other.power()) {
                return false;
            }
            for(int i = 0; i < this.power(); i++) {
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
        return Objects.hash(power, bitSet, BYTE_SIZE);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for(int i = 0; i < this.power(); i++) {
            if (this.elementExists(i)) {
                if (i == this.power() - 1) {
                    sb.append(i).append("");
                } else {
                    sb.append(i).append(", ");
                }
            }
        }
        return sb.append("]").toString();
    }
}
