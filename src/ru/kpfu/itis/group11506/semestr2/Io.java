package ru.kpfu.itis.group11506.semestr2;

import java.io.*;
import java.util.Scanner;


public class Io {

    private int currentLength;
    private String stringReprOfTree;

    public String getStringReprOfTree() {
        return stringReprOfTree;
    }


    public int getCurrentLength() {
        return currentLength;
    }

    public void writeUnicode(String path, String text) {
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] readToZip(String path) throws FileNotFoundException {
        int[] ints1;
        String s = "";
        try (Scanner in = new Scanner(new File(path))) {
            while (in.hasNext()) {
                s += in.nextLine() + "\n";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ints1 = new int[s.length() - 1];
        int index = 0;
        char[] chars = s.toCharArray();
        for (int i = 0; i < s.length() - 1; i++) {
            ints1[index] = (int) chars[i];
            index += 1;
        }
        return ints1;
    }


    public int[] readToUnzip(String path) throws IOException {
        int[] ints;
        int symbol;
        try (FileReader reader = new FileReader(path)) {
            String ss = "";
            while ((symbol = reader.read()) != -1) {
                ss = ss + (char) symbol;
            }
            stringReprOfTree = ss.substring(0, ss.indexOf("x"));
            currentLength = Integer.parseInt(ss.substring(ss.indexOf("x") + 1, ss.indexOf("y")));
            ints = new int[ss.length() - ss.indexOf("y")];
            int index = 0;
            for (int i = ss.indexOf("y") + 1; i < ss.length(); i++) {
                ints[index] = ss.charAt(i);
                index += 1;
            }
        }
        return ints;
    }


}
