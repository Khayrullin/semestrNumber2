package ru.kpfu.itis.group11506.semestr2;

import org.apache.commons.cli.*;

import java.io.IOException;


public class Mrx {
    public static void main(String[] args) {

        Options options = new Options();
        boolean haveXOrZ = false;
        options.addOption("x", false, "Опция для разархивации");
        options.addOption("z", false, "Опция для Архивации");
        options.addOption("s", true, "Файл исходный");
        options.addOption("o", true, "Файл архива");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("s") && cmd.hasOption("o")) {
                if (cmd.getOptionValue("s") == null) {
                    System.err.print("Не введен путь файла для архивации");
                } else if (cmd.getOptionValue("o") == null) {
                    System.err.print("Не введен путь файла для разархивации");
                } else if (cmd.hasOption("z")) {
                    haveXOrZ = true;
                    Haffman haffman = new Haffman();
                    Io io = new Io();
                    try {
                        io.writeUnicode(cmd.getOptionValue("o"), haffman.zipHim(cmd.getOptionValue("s")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                if (cmd.hasOption("x")) {
                    haveXOrZ= true;
                    Haffman haffman = new Haffman();
                    Io io = new Io();
                    try {
                        io.writeUnicode(cmd.getOptionValue("s"), haffman.unpackHim(cmd.getOptionValue("o")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!haveXOrZ){
                    System.err.println("Введите -x если нужно разархивировать" +
                            "и/или -z чтобы архивировать");
                }


            } else {
                System.err.println("Вы не ввели аргументы Файла для чтения(-s) или/и Путь файла для записи(-o)");
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
