package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileCSVParser {

    /**
     * Метод, преобразующий содержимое файла в строку
     */
    public static String parse(File file) {
        String result = "";
        try {
            if (file != null) {
                Scanner scn = new Scanner(file);
                StringBuilder sb = new StringBuilder();
                while (scn.hasNext()) {
                    String text = scn.nextLine();
                    sb.append(text);
                }
                result = sb.toString();
            } else {
                return "No data";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод проверяет файл на совместимость с выбранной таблицей, возвращает "1" в случае
     * успешной проверки
     * "-1" - несовпадение названий колонок в файле и таблице
     */
    public static int checkFile(String tableName, String data) {
        String[] headers = DBUtils.getHeaders(data);
        int result = 0;

        for (int i = 0; i < headers.length; i++) {
            String[] columns = DBUtils.getColumns(tableName);
            if ((columns.length - 1) == headers.length) {
                if (columns[i + 1].equals(headers[i]))
                    result = 1;
                else
                    result = -1;

            } else return result;
        }
        return result;
    }
}
