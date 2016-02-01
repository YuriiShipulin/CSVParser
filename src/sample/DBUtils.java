package sample;

import com.sun.deploy.util.StringUtils;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class DBUtils {
    /**
     * Метод получения объекта типа Connection для работы с базой данных;
     * необходимо указать валидные данные для подключения.
     */
    private static Connection getConnection() {
        final String URL = "jdbc:mysql://localhost:3306/parsecsv";
        final String NAME = "root";
        final String PASS = "GFDert567";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, NAME, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Метод, построчно добавляющий полученную строку в базу данных
     */
    protected static int insert(String tableName, String data) {
        Connection conn = getConnection();
        //разбиваем текст на строки
        List<String> arr = Arrays.asList(data.split("\r\n"));
        String[] headersList = getHeaders(data);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < headersList.length; i++) {
            if (i != headersList.length - 1)
                sb.append(headersList[i]).append(", ");
                //если последний элемент не ставим запятую
            else
                sb.append(headersList[i]);
        }
        String headers = sb.toString();

        String query;
        String resultQuery;
        PreparedStatement ps;
        switch (headersList.length) {
            case 2:
                query = "INSERT INTO $tableName ($headers) VALUES (?, ?)";
                resultQuery = query
                        .replace("$tableName", tableName)
                        .replace("$headers", headers);
                for (int i = 1; i < arr.size(); i++) {
                    try {
                        //для каждой строки, начиная с 1 вычитываем значения и кладем в базу данных
                        ps = conn.prepareStatement(resultQuery);
                        ps.setString(1, arr.get(i).split(",")[0]);
                        ps.setString(2, arr.get(i).split(",")[1]);
                        ps.executeUpdate();
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return -1;
                    }
                }
                break;
            case 3:
                query = "INSERT INTO $tableName ($headers) VALUES (?, ?, ?)";
                resultQuery = query
                        .replace("$tableName", tableName)
                        .replace("$headers", headers);
                for (int i = 1; i < arr.size(); i++) {
                    try {
                        //для каждой строки, начиная с 1 вычитываем значения и кладем в базу данных
                        ps = conn.prepareStatement(resultQuery);
                        ps.setString(1, arr.get(i).split(",")[0]);
                        ps.setString(2, arr.get(i).split(",")[1]);
                        ps.setString(3, arr.get(i).split(",")[2]);
                        ps.executeUpdate();
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return -1;
                    }
                }
                break;
            case 4:
                query = "INSERT INTO $tableName ($headers) VALUES (?, ?, ?, ?)";
                resultQuery = query
                        .replace("$tableName", tableName)
                        .replace("$headers", headers);
                for (int i = 1; i < arr.size(); i++) {
                    try {
                        //для каждой строки, начиная с 1 вычитываем значения и кладем в базу данных
                        ps = conn.prepareStatement(resultQuery);
                        ps.setString(1, arr.get(i).split(",")[0]);
                        ps.setString(2, arr.get(i).split(",")[1]);
                        ps.setString(3, arr.get(i).split(",")[2]);
                        ps.setString(4, arr.get(i).split(",")[3]);
                        ps.executeUpdate();
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return -1;
                    }
                }
                break;
            case 5:
                query = "INSERT INTO $tableName ($headers) VALUES (?, ?, ?, ?, ?)";
                resultQuery = query
                        .replace("$tableName", tableName)
                        .replace("$headers", headers);
                for (int i = 1; i < arr.size(); i++) {
                    try {
                        //для каждой строки, начиная с 1 вычитываем значения и кладем в базу данных
                        ps = conn.prepareStatement(resultQuery);
                        ps.setString(1, arr.get(i).split(",")[0]);
                        ps.setString(2, arr.get(i).split(",")[1]);
                        ps.setString(3, arr.get(i).split(",")[2]);
                        ps.setString(4, arr.get(i).split(",")[3]);
                        ps.setString(5, arr.get(i).split(",")[4]);
                        ps.executeUpdate();
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return -1;
                    }
                }
                break;
        }
        return 1;
    }

    /**
     * Метод инициализации таблиц таблицы для тестирования
     */
    protected static void initDB() {

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            st.execute("DROP TABLE IF EXISTS Table1");
            st.execute("CREATE TABLE Table1 (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, header_1 VARCHAR(20), header_2 VARCHAR(20))");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод, создающий новую таблицу в случае неоходимости
     */
    protected static void createTable(String tableName, String data) {
        String[] headers = getHeaders(data);

        try {
            Connection conn = getConnection();
            Statement st = conn.createStatement();
            switch (headers.length) {
                case 2:
                    String query = "CREATE TABLE $tableName (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, $header1 VARCHAR(20), $header2 VARCHAR(20))";
                    String resultQuery = query
                            .replace("$tableName", tableName)
                            .replace("$header1", headers[0])
                            .replace("$header2", headers[1]);
                    st.execute(resultQuery);
                    st.close();
                    conn.close();
                    break;
                case 3:
                    query = "CREATE TABLE $tableName (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, $header1 VARCHAR(20), " +
                            "$header2 VARCHAR(20), $header3 VARCHAR(20))";
                    resultQuery = query
                            .replace("$tableName", tableName)
                            .replace("$header1", headers[0])
                            .replace("$header2", headers[1])
                            .replace("$header3", headers[2]);
                    st.execute(resultQuery);
                    st.close();
                    conn.close();
                    break;
                case 4:
                    query = "CREATE TABLE $tableName (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, $header1 VARCHAR(20), " +
                            "$header2 VARCHAR(20), $header3 VARCHAR(20), $header4 VARCHAR(20))";
                    resultQuery = query
                            .replace("$tableName", tableName)
                            .replace("$header1", headers[0])
                            .replace("$header2", headers[1])
                            .replace("$header3", headers[2])
                            .replace("$header4", headers[3]);
                    st.execute(resultQuery);
                    st.close();
                    conn.close();
                    break;
                case 5:
                    query = "CREATE TABLE $tableName (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, $header1 VARCHAR(20), " +
                            "$header2 VARCHAR(20), $header3 VARCHAR(20), $header4 VARCHAR(20), $header5 VARCHAR(20))";
                    resultQuery = query
                            .replace("$tableName", tableName)
                            .replace("$header1", headers[0])
                            .replace("$header2", headers[1])
                            .replace("$header3", headers[2])
                            .replace("$header4", headers[3])
                            .replace("$header5", headers[4]);
                    st.execute(resultQuery);
                    st.close();
                    conn.close();
                    break;
                default:
                    st.close();
                    conn.close();
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод получения списка доступных таблиц
     */
    protected static String getAllTables() {
        Connection conn = getConnection();
        StringBuilder sb = new StringBuilder();
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "%", null);
            while (rs.next()) {
                sb.append(rs.getString(3)).append("\n");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Метод, возвращающий список колонок в файле и соответствующих полей в базе данных
     */
    protected static String[] getHeadersAndColumns(String tableName, String data) {
        String[] headers = getHeaders(data);
        String[] columns = getColumns(tableName);
        String[] resultList = new String[columns.length - 1];

        if ((columns.length - 1) == headers.length) {
            for (int i = 0; i < resultList.length; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append(headers[i]).append("  :  ").append(columns[i + 1]);
                resultList[i] = sb.toString();
            }
        } else {
            for (String header : headers) {
                System.out.println("Headers:");
                System.out.println(header);
            }
            for (String column : columns) {
                System.out.println("Columns:");
                System.out.println(column);
            }
        }
        return resultList;
    }

    /**
     * Метод, возвращающий список колонок в выбранной таблице
     */
    protected static String[] getColumns(String tableName) {
        StringBuilder sb = new StringBuilder();
        String[] columns = null;
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = ?");
            ps.setString(1, tableName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                sb.append(rs.getString(1)).append(";");
            }
            columns = sb.toString().split(";");
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }


    /**
     * Метод, возвращающий список колонок в файле
     */
    protected static String[] getHeaders(String data) {
        return data.split("\r\n")[0].split(",");
    }

    /**
     * Метод, заменяющий заголовки колонок в таблице
     */
    protected static void changeHeaders(String tableName, String[] newHeaders) {
        Connection conn = getConnection();
        String[] columns = DBUtils.getColumns(tableName);
        switch (newHeaders.length) {
            case 2:
                String query = "ALTER TABLE $tableName CHANGE $col1 $newHeaderName1 VARCHAR(20)";
                String resultQuery = query
                        .replace("$tableName", tableName)
                        .replace("$col1", columns[1])
                        .replace("$newHeaderName1", newHeaders[0]);

                String query1 = "ALTER TABLE $tableName CHANGE $col2 $newHeaderName2 VARCHAR(20)";
                String resultQuery1 = query1
                        .replace("$tableName", tableName)
                        .replace("$col2", columns[2])
                        .replace("$newHeaderName2", newHeaders[1]);
                try {
                    PreparedStatement ps = conn.prepareStatement(resultQuery);
                    PreparedStatement ps1 = conn.prepareStatement(resultQuery1);
                    ps.executeUpdate();
                    ps1.executeUpdate();
                    ps.close();
                    ps1.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                query = "ALTER TABLE $tableName CHANGE $col1 $newHeaderName1 VARCHAR(20)";
                resultQuery = query
                        .replace("$tableName", tableName)
                        .replace("$col1", columns[1])
                        .replace("$newHeaderName1", newHeaders[0]);

                query1 = "ALTER TABLE $tableName CHANGE $col2 $newHeaderName2 VARCHAR(20)";
                resultQuery1 = query1
                        .replace("$tableName", tableName)
                        .replace("$col2", columns[2])
                        .replace("$newHeaderName2", newHeaders[1]);
                String query2 = "ALTER TABLE $tableName CHANGE $col2 $newHeaderName2 VARCHAR(20)";
                String resultQuery2 = query2
                        .replace("$tableName", tableName)
                        .replace("$col2", columns[3])
                        .replace("$newHeaderName2", newHeaders[2]);
                try {
                    PreparedStatement ps = conn.prepareStatement(resultQuery);
                    PreparedStatement ps1 = conn.prepareStatement(resultQuery1);
                    PreparedStatement ps2 = conn.prepareStatement(resultQuery2);
                    ps.executeUpdate();
                    ps1.executeUpdate();
                    ps2.executeUpdate();
                    ps.close();
                    ps1.close();
                    ps2.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                query = "ALTER TABLE $tableName CHANGE $col1 $newHeaderName1 VARCHAR(20)";
                resultQuery = query
                        .replace("$tableName", tableName)
                        .replace("$col1", columns[1])
                        .replace("$newHeaderName1", newHeaders[0]);

                query1 = "ALTER TABLE $tableName CHANGE $col2 $newHeaderName2 VARCHAR(20)";
                resultQuery1 = query1
                        .replace("$tableName", tableName)
                        .replace("$col2", columns[2])
                        .replace("$newHeaderName2", newHeaders[1]);
                query2 = "ALTER TABLE $tableName CHANGE $col2 $newHeaderName2 VARCHAR(20)";
                resultQuery2 = query2
                        .replace("$tableName", tableName)
                        .replace("$col2", columns[3])
                        .replace("$newHeaderName2", newHeaders[2]);
                String query3 = "ALTER TABLE $tableName CHANGE $col2 $newHeaderName2 VARCHAR(20)";
                String resultQuery3 = query3
                        .replace("$tableName", tableName)
                        .replace("$col2", columns[4])
                        .replace("$newHeaderName2", newHeaders[3]);
                try {
                    PreparedStatement ps = conn.prepareStatement(resultQuery);
                    PreparedStatement ps1 = conn.prepareStatement(resultQuery1);
                    PreparedStatement ps2 = conn.prepareStatement(resultQuery2);
                    PreparedStatement ps3 = conn.prepareStatement(resultQuery3);
                    ps.executeUpdate();
                    ps1.executeUpdate();
                    ps2.executeUpdate();
                    ps3.executeUpdate();
                    ps.close();
                    ps1.close();
                    ps2.close();
                    ps3.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                query = "ALTER TABLE $tableName CHANGE $col1 $newHeaderName1 VARCHAR(20)";
                resultQuery = query
                        .replace("$tableName", tableName)
                        .replace("$col1", columns[1])
                        .replace("$newHeaderName1", newHeaders[0]);

                query1 = "ALTER TABLE $tableName CHANGE $col2 $newHeaderName2 VARCHAR(20)";
                resultQuery1 = query1
                        .replace("$tableName", tableName)
                        .replace("$col2", columns[2])
                        .replace("$newHeaderName2", newHeaders[1]);
                query2 = "ALTER TABLE $tableName CHANGE $col2 $newHeaderName2 VARCHAR(20)";
                resultQuery2 = query2
                        .replace("$tableName", tableName)
                        .replace("$col2", columns[3])
                        .replace("$newHeaderName2", newHeaders[2]);
                query3 = "ALTER TABLE $tableName CHANGE $col2 $newHeaderName2 VARCHAR(20)";
                resultQuery3 = query3
                        .replace("$tableName", tableName)
                        .replace("$col2", columns[4])
                        .replace("$newHeaderName2", newHeaders[3]);
                String query4 = "ALTER TABLE $tableName CHANGE $col2 $newHeaderName2 VARCHAR(20)";
                String resultQuery4 = query4
                        .replace("$tableName", tableName)
                        .replace("$col2", columns[5])
                        .replace("$newHeaderName2", newHeaders[4]);
                try {
                    PreparedStatement ps = conn.prepareStatement(resultQuery);
                    PreparedStatement ps1 = conn.prepareStatement(resultQuery1);
                    PreparedStatement ps2 = conn.prepareStatement(resultQuery2);
                    PreparedStatement ps3 = conn.prepareStatement(resultQuery3);
                    PreparedStatement ps4 = conn.prepareStatement(resultQuery4);
                    ps.executeUpdate();
                    ps1.executeUpdate();
                    ps2.executeUpdate();
                    ps3.executeUpdate();
                    ps4.executeUpdate();
                    ps.close();
                    ps1.close();
                    ps2.close();
                    ps3.close();
                    ps4.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}

