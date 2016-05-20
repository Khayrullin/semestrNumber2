package ru.kpfu.itis.group11506.semestr2;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Haffman {

    private String finalCode = "", stringReprOfTree = "";
    private BinaryList takenTreeHead;
    private Map<Integer, String> map = new HashMap<>();


    public String zipHim(String path) throws FileNotFoundException {
        String binaryCode = "";
        int[] ints1;
//Считываем весь текст в массив целых чисел:
        Io io = new Io();
        ints1 = io.readToZip(path);
// Создаем Аррей лист, и записываем туда РАЗНЫЕ буквы из текста + их частота появления в тексте:
        List<BinaryList> arrayList = getBinaryLists(ints1);
//Создаем Приоритетную очередь и заполняем ее элементами из Аррей Листа.
// Приоритет - Частота появления в тексте:
        PriorityQueue<BinaryList> priorityQueue = getBinaryLists(arrayList);
//Делаем дерево Хаффмана:
        createTree(priorityQueue);
//Выявляем Центральный узел Дерева:
        BinaryList treeHead = priorityQueue.poll();
//Кодируем дерево  в строку.
        createStringRepresentationOfTree(treeHead);
// Строим Таблицу Хаффмана:
        createMap("", treeHead);
// Создаем Строку с бинарным кодом для последующих преобразований:
        for (int bytes2 : ints1) {
            binaryCode = binaryCode + map.get(bytes2);
        }
//Финальные преобразования бинарного кода в Символы - запись в finalCode:
        transform(binaryCode);
//Запись конечной сжатой строки в файл:
        finalCode = stringReprOfTree + "x" + ints1.length + "y" + finalCode;
        return finalCode;
    }


    public String unpackHim(String path) throws IOException {
        int[] ints;
        Io io = new Io();
//Считываем весь текст в строку,(далее в массив интов)отделяем Код Дерева от Закодированного кода:
        ints = io.readToUnzip(path);
        stringReprOfTree = io.getStringReprOfTree();
//Создаем дерево из кода :
        createUnpackedTree();
//Для каждой буквы делаем двоичное представление добавляя если нехватает Лидирующие нули чтобы достичь
// длины 8 для однозначного декодирования.
        String binaryCode2 = getString(ints);
//Проходимся по дереву считывая нули и еденицы, если дошли до узла имеющего символ, конкатенируем его в
// строку 'unpackedText', после этого начинаем опять с центрального узла считывать другие символы.
        return getString(binaryCode2, io.getCurrentLength());
    }


    private void transform(String binaryCode) {
        int lastIndex = 0;
        while (lastIndex + 7 < binaryCode.length()) {
            String s = binaryCode.substring(lastIndex, lastIndex += 8);
            finalCode = finalCode + Character.toString((char) Integer.parseInt(s, 2));
        }
        if (binaryCode.length() % 8 != 0) {
            String s = Integer.toString((int) (Integer.parseInt(binaryCode.substring(lastIndex)) * Math.pow(10d, (8 - (binaryCode.length() % 8)))));
            finalCode = finalCode + Character.toString((char) Integer.parseInt(s, 2));
        }
    }


    private void createTree(PriorityQueue<BinaryList> priorityQueue) {
        while (priorityQueue.size() != 1) {
            BinaryList binaryList1 = priorityQueue.poll();
            BinaryList binaryList2 = priorityQueue.poll();
            BinaryList binaryList = new BinaryList();
            binaryList.setLeft(binaryList1);
            binaryList.setRight(binaryList2);
            binaryList.setFrequency(binaryList1.getFrequency() + binaryList2.getFrequency());
            priorityQueue.add(binaryList);
        }
    }


    private PriorityQueue<BinaryList> getBinaryLists(List<BinaryList> arrayList) {
        PriorityQueue<BinaryList> priorityQueue = new PriorityQueue<>();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            priorityQueue.add((BinaryList) it.next());
        }
        return priorityQueue;
    }


    private List<BinaryList> getBinaryLists(int[] ints1) {
        List<BinaryList> arrayList = new ArrayList<>();
        boolean b = false;
        for (int i = 0; i < ints1.length; i++) {
            int freq = 0;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                if (((BinaryList) it.next()).getValue() == ints1[i]) {
                    b = true;
                    break;
                }
            }
            if (!b) {
                for (int m = i; m < ints1.length; m++) {
                    if (ints1[i] == ints1[m]) {
                        freq += 1;
                    }
                }
                BinaryList binaryList = new BinaryList();
                binaryList.setFrequency(freq);
                binaryList.setValue(ints1[i]);
                arrayList.add(binaryList);
            }
            b = false;
        }
        return arrayList;
    }


    private void createMap(String way, BinaryList list) {
        if (list.getRight() == null && list.getLeft() == null) {
            map.put(list.getValue(), way);
        } else {
            if (list.getLeft() != null) {
                createMap(way + "0", list.getLeft());
            }
            if (list.getRight() != null) {
                createMap(way + "1", list.getRight());
            }
        }
    }

    private void createStringRepresentationOfTree(BinaryList list) {
        if (list.getRight() == null && list.getLeft() == null) {
            stringReprOfTree = stringReprOfTree + "1" + Character.toString((char) list.getValue());
        } else {
            createStringRepresentationOfTree(list.getLeft());
            createStringRepresentationOfTree(list.getRight());
            stringReprOfTree = stringReprOfTree + "0";

        }
    }


    private void createUnpackedTree() {
        Stack<BinaryList> stack = new Stack<>();
        for (int i = 0; i < stringReprOfTree.length(); i++) {
            if (stringReprOfTree.charAt(i) == '1') {
                BinaryList binaryList = new BinaryList();
                binaryList.setValue((int) (stringReprOfTree.charAt(i + 1)));
                stack.push(binaryList);
                i += 1;
            } else {
                BinaryList binaryList = new BinaryList();
                binaryList.setRight(stack.pop());
                binaryList.setLeft(stack.pop());
                stack.push(binaryList);
            }
        }
        takenTreeHead = stack.pop();
    }

    private String getString(String binaryCode2, int currentLength) {
        int length = 0;
        BinaryList word = takenTreeHead;
        String unpackedText = "";
        char[] array = binaryCode2.toCharArray();
        for (int i = 0; i < array.length && length < currentLength; i++) {
            if (array[i] == '0') {
                if (word.getLeft().getValue() != 0) {
                    unpackedText = unpackedText + Character.toString((char) word.getLeft().getValue());
                    word = takenTreeHead;
                    length += 1;
                } else {
                    word = word.getLeft();
                }
            } else {
                if (word.getRight().getValue() != 0) {
                    unpackedText = unpackedText + Character.toString((char) word.getRight().getValue());
                    word = takenTreeHead;
                    length += 1;
                } else {
                    word = word.getRight();
                }
            }
        }
        return unpackedText;
    }

    private String getString(int[] ints) {
        String binaryCode2 = "";
        for (int intes : ints) {
            if (Integer.toBinaryString(intes).length() < 8) {
                String concatZero = Integer.toBinaryString(intes);
                while (concatZero.length() != 8) {
                    concatZero = "0" + concatZero;
                }
                binaryCode2 = binaryCode2 + concatZero;
            } else {
                binaryCode2 = binaryCode2 + Integer.toBinaryString(intes);
            }
        }
        return binaryCode2;
    }


}
