package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileCSVParser {

    /**
     * Метод, возвращающий строку с содержимым файла,
     * либо пустую строку в случае неверного формата либо пустого файла.
     */
    public static String parse(File file) {
        String result = "";
        try {
            Scanner scn = new Scanner(file);
            if (file.exists() && scn.hasNext()) {
                StringBuilder sb = new StringBuilder();
                    while (scn.hasNext()) {
                        String text = scn.nextLine();
                        sb.append(text).append("\r\n");
                    }
                    result = sb.toString();
                } else return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод проверяет файл на совместимость с выбранной таблицей, возвращает true в случае
     * успешной проверки
     * false - несовпадение названий колонок в файле и таблице
     */
    public static boolean checkFileAndTable(String tableName, String data) {
        String[] headers = DataBaseUtils.getHeaders(data);
        boolean result = false;

        for (int i = 0; i < headers.length; i++) {
            String[] columns = DataBaseUtils.getColumns(tableName);
            if ((columns.length - 1) == headers.length) {
                if (columns[i + 1].equals(headers[i]))
                    result = true;
                else
                    result = false;

            } else return result;
        }
        return result;
    }
}
