package ru.kpfu.itis.group11506.semestr2;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.IOException;


public class Mrx {
    public static void main(String[] args) {

        // Запуск с аргументами командной строки
        // java -x (extract) -s (source) out.dat -o (out) in.dat
        // java -z (zip) -s ... -o ...
        // apache.commons.cli

        Options options = new Options();
        options.addOption("x", false, "Опция для разархивации");
//        Option option = new Option("x", );

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
