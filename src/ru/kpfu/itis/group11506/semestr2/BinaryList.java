package ru.kpfu.itis.group11506.semestr2;

class BinaryList implements Comparable<BinaryList> {

    private int value;
    private int frequency;
    private BinaryList Left;
    private BinaryList Right;

     int getFrequency() {
        return frequency;
    }

     void setFrequency(int frequency) {
        this.frequency = frequency;
    }

     int getValue() {
        return value;
    }

     void setValue(int value) {
        this.value = value;
    }

     BinaryList getLeft() {
        return Left;
    }

     void setLeft(BinaryList left) {
        Left = left;
    }

    BinaryList getRight() {
        return Right;
    }

     void setRight(BinaryList right) {
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

