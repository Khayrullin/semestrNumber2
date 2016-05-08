package ru.kpfu.itis.group11506.semestr2;

public class BinaryList implements Comparable<BinaryList> {

    private int value;
    private int frequency;
    private BinaryList Left;
    private BinaryList Right;

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
        return Left;
    }

    public void setLeft(BinaryList left) {
        Left = left;
    }

    public BinaryList getRight() {
        return Right;
    }

    public void setRight(BinaryList right) {
        Right = right;
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

