package ru.kpfu.itis.group11506.semestr2;

import java.io.FileWriter;
import java.io.IOException;

// Вынести чтение и запись файла сюда
public class Io {

    public void writeUnicode(String way, String text) {
        try (FileWriter writer = new FileWriter(way, false)) {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
