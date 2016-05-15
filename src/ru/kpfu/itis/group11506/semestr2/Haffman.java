package ru.kpfu.itis.group11506.semestr2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

// логика работы с i/o должна быть инкапсулирована
public class Haffman {


    private String s = "", binaryCode = "", finalCode = "", ss = "";
    private int[] ints1;
    private BinaryList treeHead;
    private HashMap<Integer, String> map = new HashMap<>();


    public String zipHim(String j) throws FileNotFoundException {
//Считываем весь текст в строку:
        readFromFile(j);
// Создаем Аррей лист, и записываем туда РАЗНЫЕ буквы из текста + их частота появления в тексте:
        List<BinaryList> arrayList = getBinaryLists();
//Создаем Приоритетную очередь и заполняем ее элементами из Аррей Листа.
// Приоритет - Частота появления в тексте:
        PriorityQueue<BinaryList> priorityQueue = getBinaryLists(arrayList);
//Делаем дерево Хаффмана:
        createTree(priorityQueue);
//Выявляем Центральный узел Дерева:
        treeHead = priorityQueue.poll();
// Строим Таблицу Хаффмана:
        createMap("", treeHead);
// Создаем Строку с бинарным кодом для последующих преобразований:
        for (int bytes2 : ints1) {
            binaryCode = binaryCode + map.get(bytes2);
        }
//Финальные преобразования бинарного кода в Символы - запись в finalCode:
        transform();
//Запись конечной сжатой строки в файл:
        return finalCode;

    }

    private void transform() {
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

    private List<BinaryList> getBinaryLists() {
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

    private void readFromFile(String j) throws FileNotFoundException {
        Scanner in = new Scanner(new File(j));
        while (in.hasNext()) {
            s += in.nextLine() + "\n";
        }
        in.close();
        ints1 = new int[s.length() - 2];
        int index = 0;
        char[] chars = s.toCharArray();
        for (int i = 1; i < s.length() - 1; i++) {
            ints1[index] = (int) chars[i];
            index += 1;
        }
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

    public String unpackHim(String j) throws IOException {
//Считываем весь текст в строку:

        int[] ints = getInts(j);

//Для каждой буквы делаем двоичное представление добавляя если нехватает Лидирующие нули чтобы достичь
// длины 8 для однозначного декодирования.

        String binaryCode2 = getString(ints);

//Проходимся по дереву считывая нули и еденицы, если дошли до узла имеющего символ, конкатенируем его в
// строку 'unpackedText', после этого начинаем опять с центрального узла считывать другие символы.

        String unpackedText = getString(binaryCode2);
        return unpackedText;


    }

    private String getString(String binaryCode2) {
        int length = 0;
        BinaryList word = treeHead;
        String unpackedText = "";
        char[] array = binaryCode2.toCharArray();
        for (int i = 0; i < array.length && length < ints1.length; i++) {
            if (array[i] == '0') {
                if (word.getLeft().getValue() != 0) {
                    unpackedText = unpackedText + Character.toString((char) word.getLeft().getValue());
                    word = treeHead;
                    length += 1;
                } else {
                    word = word.getLeft();
                }
            } else {
                if (word.getRight().getValue() != 0) {
                    unpackedText = unpackedText + Character.toString((char) word.getRight().getValue());
                    word = treeHead;
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

    private int[] getInts(String j) throws IOException {
        Scanner in = new Scanner(new File(j));
        while (in.hasNext()) {
            ss += in.nextLine() + "\r";
        }
        in.close();
        int[] ints = new int[ss.length()];

        try (FileReader reader = new FileReader(j)) {
            int c, i = 0;
            while ((c = reader.read()) != -1) {
                ints[i] = (char) c;
                i += 1;
            }
        } catch (IOException ex) {
            throw ex;
        }
        return ints;
    }


}
