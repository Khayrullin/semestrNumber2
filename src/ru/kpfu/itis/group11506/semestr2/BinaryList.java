package ru.kpfu.itis.group11506.semestr2;

public class BinaryList implements Comparable<BinaryList> {

    private int value;
    private int frequency;
    private BinaryList left;
    private BinaryList right;

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public BinaryList getLeft() {
        return left;
    }

    public void setLeft(BinaryList left) {
        this.left = left;
    }

    public BinaryList getRight() {
        return right;
    }

    public void setRight(BinaryList right) {
        this.right = right;
    }


    @Override
    public int compareTo(BinaryList o) {
        if (frequency > o.frequency) {
            return 1;
        } else if (frequency < o.frequency) {
            return -1;
        } else return 0;
    }
}

