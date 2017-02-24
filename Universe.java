package com.ladislav;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Implements set on fixed universe. Power of the universe is set in constructor.
 * Element of the set is positive integer.
 * Implements operations: intersection, union, difference, complement, add/remove element or
 * array of elements and existence of element.
 *
 * @author Ladislav
 * @version 2.2
 * @since 12/24/2017
 */

public class Universe implements Iterable<Integer>{


    private static final int BYTE_SIZE = 8;

    private final byte[] bitSet;
    private final int power;
    private int setSize = 0;

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
     * @throws ArrayIndexOutOfBoundsException if element power is not in bounds of Universe power
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
     * @throws IndexOutOfBoundsException (elementExsists()) when number is greater than Universe power
     */
    public boolean setElement(int element) {

        if (!elementExists(element)) {
            int bitSetPos = element / BYTE_SIZE;
            byte bitSetByte = bitSet[bitSetPos];

            bitSetByte = (byte) (bitSetByte | (1 << element - bitSetPos*BYTE_SIZE));
            bitSet[bitSetPos] = bitSetByte;
            setSize++;
            return true;
        }
        return false;
    }

    /**
     * Adds array of elements to set.
     * @param elements array of elements to add to set
     * @return integer, number of addeds elements, -1 if array length is larger than Universe power
     * @throws IndexOutOfBoundsException (elementExsists()) when number is greater than Universe power
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
     * @throws IndexOutOfBoundsException (elementExsists()) when number is greater than Universe power
     */

    public boolean unsetElement(int element) {
        if (elementExists(element)) {
            int bitSetPos = element / BYTE_SIZE;
            byte bitSetByte = bitSet[bitSetPos];

            bitSetByte = (byte) (bitSetByte & ~(1 << element - bitSetPos*BYTE_SIZE));
            bitSet[bitSetPos] = bitSetByte;
            setSize--;
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
            for (int i = 0; i < elements.length; i++) {
                int elementToUnset = elements[i];
                if (elementToUnset < this.power() && this.elementExists(elementToUnset)) {
                    unsetElement(elementToUnset);
                    counter++;
                }
            }
            return counter;
        }
        return -1;
    }

    public void unsetAll() {
        for(int i = 0; i < bitSet.length; i++) {
            bitSet[i] = 0;
            setSize = 0;
        }
    }

    /**
     * *Implements union set arithmetic
     * @param other accepts Universe object
     * @return Returns union of to Universes
     * @throws NullPointerException if parameter is null
     */

    public Universe union(Universe other) {

        if (other != null) {

            Universe newUniverse;
            if (this.bitSet.length >= other.bitSet.length) {

                newUniverse = new Universe(this.power());
                for (int i = 0; i < this.bitSet.length; i++) {

                    if (i <= other.bitSet.length) {
                        newUniverse.bitSet[i] = (byte) (this.bitSet[i] | other.bitSet[i]);
                    } else {
                        newUniverse.bitSet[i] = this.bitSet[i];
                    }
                }
            } else {

                newUniverse = new Universe(other.power());
                for (int i = 0; i < other.bitSet.length; i++) {

                    if (i <= this.bitSet.length) {
                        newUniverse.bitSet[i] = (byte) (this.bitSet[i] | other.bitSet[i]);
                    } else {
                        newUniverse.bitSet[i] = other.bitSet[i];
                        }
                    }
                }
                return newUniverse;

            } else {
            throw new NullPointerException();
        }
    }

    /**
     * *Implements union set arithmetic
     * @param other accepts Universe object that has to have same power as one uniting with
     * @return void
     */

    public boolean unite(Universe other){

        if (other != null) {

            if (this.power() == other.power()) {

                for (int i = 0; i < this.bitSet.length; i++) {
                    this.bitSet[i] = (byte) (this.bitSet[i] | other.bitSet[i]);
                }
                return true;
            }
            return false;

        } else {
            throw new NullPointerException();
        }
    }

    /**
     * *Implements intersection set arithmetic
     * @param other accepts Universe object that has to have same power as one intersecting with
     * @return Returns intersection of to Universes or null if their power differs
     * @throws NullPointerException for null param
     */

    public Universe intersection(Universe other) {

        if (other != null) {
            Universe newUniverse;
            if (this.bitSet.length >= other.bitSet.length) {
                newUniverse = new Universe(other.power());
                for (int i = 0; i < other.bitSet.length; i++) {
                    newUniverse.bitSet[i] = (byte) (this.bitSet[i] & other.bitSet[i]);
                }
            } else {
                newUniverse = new Universe(this.power());
                for (int i = 0; i < this.bitSet.length; i++) {
                    newUniverse.bitSet[i] = (byte) (this.bitSet[i] & other.bitSet[i]);
                }
            }
            return newUniverse;
        } else {
            throw new NullPointerException();
        }
    }

    public void intersect(Universe other) {

        if (other != null) {
            if (this.power() == other.power()) {
                for (int i = 0; i < this.bitSet.length; i++) {
                    this.bitSet[i] = (byte) (this.bitSet[i] & other.bitSet[i]);
                }
            }
        } else {
            throw new NullPointerException();
        }

    }

    /**
     * *Implements difference set arithmetic
     * @param other accepts Universe object that has to have same power as one intersectingwith
     * @return Returns intersection of to Universes or null if their power differs
     * @throws NullPointerException when null passed
     */

    public Universe difference(Universe other) {

        if (other != null) {

            Universe newUniverse = new Universe(this.power());
            if (this.power() >= other.power()) {

                for (int i = 0; i < other.bitSet.length; i++) {
                    newUniverse.bitSet[i] = (byte) (this.bitSet[i] ^ other.bitSet[i]);
                }
            } else {
                for (int i = 0; i < this.bitSet.length; i++) {
                    newUniverse.bitSet[i] = (byte) (this.bitSet[i] ^ other.bitSet[i]);
                }
            }
            return newUniverse;
        } else {
            throw new NullPointerException();
        }
    }

    public void complement() {

        for (int i = 0; i < this.bitSet.length; i++) {
            this.bitSet[i] = (byte) ~this.bitSet[i];
        }

    }

    /**
     * @return integer, number of possible elements in Set
     */

    public int power() {
        return power;
    }

    /**
     * @return integer, number of elements in Set
     */

    public int size() {
        return setSize;
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
            for(int i = 0; i < this.bitSet.length; i++) {
                if(this.bitSet[i] == other.bitSet[i]){
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

    /**
     * Override of toString() method. Returns set of elements inside square brackets.
     * If last element of universe is set, there will be no comma (,) after it, if not
     * There will be comma printed after last element in set.
     * @return string
     */
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

    /**
     * Private inner class. Returns iterator over set.
     * Has functions hasNext() and next() to check if there is next element and one that returns it
     * @return Iterator
     */
    @Override
    public Iterator<Integer> iterator() {
        return new UniverseIterator();
    }

    private class UniverseIterator implements Iterator<Integer>{

        private int cursor;

        private UniverseIterator() {
            cursor = 0;
        }

        @Override
        public boolean hasNext() {

            for(int i = cursor; i < Universe.this.power(); i++) {
                if (Universe.this.elementExists(i)) {
                    return true;
                }
                cursor++;
            }
            return false;
        }

        @Override
        public Integer next() {
            if(!this.hasNext()) {
                throw new NoSuchElementException();
            }
            int current = cursor;
            cursor++;
            return current;
        }
    }
}
