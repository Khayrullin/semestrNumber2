package ru.kpfu.itis.group11506.semestr2;

import java.io.FileNotFoundException;

public class Mrx {
    public static void main(String[] args) {

        Haffman haffman = new Haffman();

        try {
            haffman.zipHim("in.dat");
            haffman.unpackHim("out.dat");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
