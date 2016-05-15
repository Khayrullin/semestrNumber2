package ru.kpfu.itis.group11506.semestr2;

import java.io.IOException;

public class Mrx {
    public static void main(String[] args) {

        // Запуск с аргументами командной строки
        // java -x -s out.dat -o in.dat
        // java -z -s ... -o ...
        // apache.commons.cli

        // В out файле хранить дерево для разархивации
        Haffman haffman = new Haffman();
        Io io = new Io();

        try {
            io.writeUnicode("out.dat", haffman.zipHim("in.dat"));
            io.writeUnicode("unpackedZip.dat", haffman.unpackHim("out.dat"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
