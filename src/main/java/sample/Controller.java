package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.*;

public class Controller {

    private File file;
    private String tableName;
    private static boolean checked = false;

    @FXML
    Button browse;

    @FXML
    Button refresh;

    @FXML
    Button save;

    @FXML
    Button newTable;

    @FXML
    Button changeHeaders;

    @FXML
    TextField url;

    @FXML
    TextField tblName;

    @FXML
    TextArea tables;

    @FXML
    TextArea data;

    @FXML
    TextArea cols;

    @FXML
    private void initialize() {
        DataBaseUtils.initDB();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("");
        alert.setContentText("Please browse .txt/.xls/.csv file and select the table");
        tables.setText(DataBaseUtils.getAllTables());
        alert.showAndWait();
    }

    @FXML
    public void refresh() {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Information");
        success.setHeaderText("");
        success.setContentText("Refreshed \r\nPlease browse the file and select the table");
        tables.setText(DataBaseUtils.getAllTables());
        url.setText("");
        tblName.setText("");
        cols.setText("");
        data.setText("");
        success.showAndWait();
    }

    @FXML
    public void browseFile() throws IOException, URISyntaxException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("");
        alert.setContentText("File was selected. Now insert the table name for data saving");

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(stage);
        if (file != null && !FileCSVParser.parse(file).equals("")) {
            url.setText(file.getAbsolutePath());
            data.setText(FileCSVParser.parse(file));
            alert.showAndWait();
        }
        else {
            data.setText("No data");
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("File is empty or it has unsupported format");
            alert.showAndWait();
        }
    }

    public void gotTableName() {
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        Alert alert = new Alert(Alert.AlertType.WARNING);
        success.setTitle("Success");
        alert.setTitle("Warning");

        tableName = tblName.getText();
        if (!tableName.equals("") && !tableName.isEmpty()) {
            if (DataBaseUtils.getAllTables().contains(tableName)) {

                boolean result = FileCSVParser.checkFileAndTable(tableName, FileCSVParser.parse(file));
                if (result) {
                    StringBuilder sb = new StringBuilder();
                    for (String s : DataBaseUtils.getHeadersAndColumns(tableName, FileCSVParser.parse(file))) {
                        sb.append(s).append("\r\n");
                    }
                    cols.setText(sb.toString());
                    checked = true;
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Headers:").append("\r\n");
                    for (String s : DataBaseUtils.getHeaders(FileCSVParser.parse(file))) {
                        sb.append(s).append("\r\n");
                    }
                    sb.append("Columns:").append("\r\n");
                    for (int i = 1; i < DataBaseUtils.getColumns(tableName).length; i++) {
                        sb.append(DataBaseUtils.getColumns(tableName)[i]).append("\r\n");
                    }
                    cols.setText("File and table do not match \r\n" + sb.toString());
                }
            }
        } else {
            alert.setHeaderText("Not valid table name");
            alert.setContentText("Please enter new table name");
            alert.showAndWait();
        }
    }

    public void saveData() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        success.setTitle("Success!");
        if (checked) {
            String data = FileCSVParser.parse(file);
            if (DataBaseUtils.insert(tableName, data)) {
                success.setHeaderText("File was inserted successfully");
                success.setContentText("File [" + file.getAbsolutePath() + "] was inserted to table [" + tableName + "]");
                success.showAndWait();
                checked = false;
            } else {
                alert.setHeaderText("Error during file inserting");
                alert.setContentText("Please check table and file parameters");
                alert.showAndWait();
            }
        } else {
            alert.setHeaderText("No Table/File Selected");
            alert.setContentText("Please select a file and insert the table name, then press [ENTER]");
            alert.showAndWait();
        }
    }

    public void createTable() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning");
        success.setTitle("Success!");
        StringBuilder sb = new StringBuilder();
        String data = FileCSVParser.parse(file);
        if (!data.equals("No data")) {
            if (tableName != null) {
                if (!DataBaseUtils.getAllTables().contains(tableName)) {
                    DataBaseUtils.createTable(tableName, data);
                    success.setHeaderText("Table was created successfully");
                    success.setContentText("Table [" + tableName + "] was created");
                    success.showAndWait();
                    tables.setText(DataBaseUtils.getAllTables());
                    this.data.setText(FileCSVParser.parse(file).replace(",", "\r\n").replace(";", "\r\n"));
                    for (String s : DataBaseUtils.getHeadersAndColumns(tableName, FileCSVParser.parse(file))) {
                        sb.append(s).append("\r\n");
                    }
                    cols.setText(sb.toString());
                } else {
                    alert.setHeaderText("Warning");
                    alert.setContentText("Table [" + tableName + "] already exists \r\n" +
                            "Please, insert the new table name and press [ENTER]");
                    alert.showAndWait();
                }
            } else {
                alert.setHeaderText("Warning");
                alert.setContentText("Please, insert unique table name and press [ENTER]");
                alert.showAndWait();
            }
        } else {
            alert.setHeaderText("Warning");
            alert.setContentText("Please, select the file first");
            alert.showAndWait();
        }
    }

    public void changeHeaders() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Information");
        alert.setTitle("Warning");

        String data = FileCSVParser.parse(file);
        StringBuilder sb = new StringBuilder();
        String[] newHeaders = cols.getText().split("\r\n");

        if (!data.equals("No data")) {
            if (newHeaders.length <= 5 || newHeaders.length >= 2) {
                if (newHeaders.length == DataBaseUtils.getHeaders(data).length) {
                    DataBaseUtils.changeHeaders(tableName, newHeaders);
                    for (String s : DataBaseUtils.getHeadersAndColumns(tableName, FileCSVParser.parse(file))) {
                        sb.append(s).append("\r\n");
                    }
                    cols.setText(sb.toString());
                    success.setContentText("Headers in table [" + tableName + "] were changed");
                    success.showAndWait();
                } else {
                    cols.setText("Enter new column headers here");
                    alert.setHeaderText("Wrong headers count or format!");
                    alert.setContentText("Please check selected table headers");
                    alert.showAndWait();
                }
            } else {
                success.setHeaderText("");
                success.setContentText("Enter new column headers in [Headers & Columns] in one column, then press [Change]");
                success.showAndWait();
            }
        } else {
            alert.setHeaderText("System can not find file by path [" + (file != null ? file.getAbsolutePath() : null) + "]");
            alert.setContentText("Please check selected table headers");
            alert.showAndWait();
        }
    }
}


