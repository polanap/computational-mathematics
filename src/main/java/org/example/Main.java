package org.example;

import javafx.scene.chart.ScatterChart;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.example.task.Task;

import java.io.*;
import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class Main extends Application {

    private double [] x = new double [10];
    private double [] y = new double [10];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Аппроксимация функции");

        Label taskLabel = new Label("Аппроксимация функции");
        taskLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(20);
        grid.setVgap(20);
        Scene scene = new Scene(grid, 700, 1000);
        scene.setFill(Color.LIGHTGRAY);

        GridPane task1Grid = createTaskGrid(primaryStage);

        grid.add(taskLabel, 0, 0);
        grid.add(task1Grid, 0, 2);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    private GridPane createTaskGrid(Stage primaryStage) {
//        GridPane taskGrid = new GridPane();
//        taskGrid.setPadding(new Insets(10));
//        taskGrid.setVgap(10);
//        taskGrid.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");
//
//        TextField xInputField = new TextField();
//        Label xLabel = new Label("Введите значения x (через запятую): ");
//        xInputField.setPromptText("значения x");
//        TextField yInputField = new TextField();
//        Label yLabel = new Label("Введите значения у (через запятую): ");
//        yInputField.setPromptText("значения y");
//
//        Button loadButton = new Button("Загрузить из файла");
//        loadButton.setOnAction(e -> loadDataFromFile(primaryStage));
//
//        Button solveButton = new Button("Решить");
//        solveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;");
//        solveButton.setOnMouseEntered(e -> solveButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));
//        solveButton.setOnMouseExited(e -> solveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));
//
//        TextArea resultArea = new TextArea();
//        resultArea.setEditable(false);
//        resultArea.setWrapText(true);
//
//        resultArea.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
//
//        LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
//        lineChart.setTitle("График функции");
//        lineChart.setStyle("-fx-background-color: #f0f0f0;");
//        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(new NumberAxis(), new NumberAxis());
//        scatterChart.setTitle("График функции");
//        scatterChart.setStyle("-fx-background-color: #f0f0f0;");
//
//        solveButton.setOnAction(e -> {
//            try {
//                x = Arrays.stream(xInputField.getText().split(","))
//                        .mapToDouble(Double::parseDouble)
//                        .toArray();
//                y = Arrays.stream(yInputField.getText().split(","))
//                        .mapToDouble(Double::parseDouble)
//                        .toArray();
//                Task task = new Task(x, y, lineChart);
//                String ansText = task.makeAnswer();
//                if (x.length!=y.length) throw new Exception("Количество значений x и y должно совпадать");
//                else if (x.length<8 || x.length>12) throw new Exception("Количество координат должно быть от 8 до 12");
////                String ansText = "мяу";
//
//                resultArea.setText(ansText);
//
//            } catch (Exception ex) {
//                resultArea.setText("Ошибка: " + ex.getMessage());
//            }
//        });
//
//        Button saveButton = new Button("Сохранить результат");
//        saveButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;");
//        saveButton.setOnMouseEntered(e -> saveButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));
//        saveButton.setOnMouseExited(e -> saveButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));
//
//        saveButton.setOnAction(e -> {
//            String resultText = resultArea.getText();
//            if (resultText.isEmpty()) {
//                showAlert("Ошибка", "Нет результата для сохранения.");
//                return;
//            }
//
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Сохранить результат");
//            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt"));
//            File file = fileChooser.showSaveDialog(primaryStage);
//
//            if (file != null) {
//                try (FileWriter writer = new FileWriter(file)) {
//                    writer.write(resultText);
//                    showAlert("Успех", "Результат успешно сохранен в файл: " + file.getAbsolutePath());
//                } catch (IOException ex) {
//                    showAlert("Ошибка", "Не удалось сохранить файл: " + ex.getMessage());
//                }
//            }
//        });
//
//        taskGrid.add(xLabel, 0,0);
//        taskGrid.add(xInputField, 1, 0);
//        taskGrid.add(yLabel, 0,1);
//        taskGrid.add(yInputField, 1, 1);
//        taskGrid.add(solveButton, 0, 5);
//        taskGrid.add(saveButton, 1, 5);
//        taskGrid.add(resultArea, 0, 6, 2, 1);
//        taskGrid.add(lineChart, 0, 7, 2, 1);
//
//
//        return taskGrid;
//    }

    private GridPane createTaskGrid(Stage primaryStage) {
        GridPane taskGrid = new GridPane();
        taskGrid.setPadding(new Insets(10));
        taskGrid.setVgap(10);
        taskGrid.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        TextField xInputField = new TextField();
        Label xLabel = new Label("Введите значения x (через запятую): ");
        xInputField.setPromptText("значения x");
        TextField yInputField = new TextField();
        Label yLabel = new Label("Введите значения у (через запятую): ");
        yInputField.setPromptText("значения y");

        Button loadButton = new Button("Загрузить из файла");
        loadButton.setOnAction(e -> loadDataFromFile(primaryStage));

        Button solveButton = new Button("Решить");
        solveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;");
        solveButton.setOnMouseEntered(e -> solveButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));
        solveButton.setOnMouseExited(e -> solveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        resultArea.setMinHeight(100); // Установка минимальной высоты
        resultArea.setPrefWidth(600); // Установка предпочтительной ширины

        LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart.setTitle("График функции");
        lineChart.setStyle("-fx-background-color: #f0f0f0;");
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(new NumberAxis(), new NumberAxis());
        scatterChart.setTitle("График функции");
        scatterChart.setStyle("-fx-background-color: #f0f0f0;");

        solveButton.setOnAction(e -> {
            try {
                x = Arrays.stream(xInputField.getText().split(","))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                y = Arrays.stream(yInputField.getText().split(","))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                Task task = new Task(x, y, lineChart);
                String ansText = task.makeAnswer();
                if (x.length != y.length) throw new Exception("Количество значений x и y должно совпадать");
                else if (x.length < 8 || x.length > 12) throw new Exception("Количество координат должно быть от 8 до 12");

                resultArea.setText(ansText);

            } catch (Exception ex) {
                resultArea.setText("Ошибка: " + ex.getMessage());
            }
        });

        Button saveButton = new Button("Сохранить результат");
        saveButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;");
        saveButton.setOnMouseEntered(e -> saveButton.setStyle("-fx-background-color: #1976D2; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));
        saveButton.setOnMouseExited(e -> saveButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));

        saveButton.setOnAction(e -> {
            String resultText = resultArea.getText();
            if (resultText.isEmpty()) {
                showAlert("Ошибка", "Нет результата для сохранения.");
                return;
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Сохранить результат");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt"));
            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(resultText);
                    showAlert("Успех", "Результат успешно сохранен в файл: " + file.getAbsolutePath());
                } catch (IOException ex) {
                    showAlert("Ошибка", "Не удалось сохранить файл: " + ex.getMessage());
                }
            }
        });

        // Установка HGrow для элементов, чтобы они занимали доступное пространство
        GridPane.setHgrow(xInputField, Priority.ALWAYS);
        GridPane.setHgrow(yInputField, Priority.ALWAYS);
        GridPane.setHgrow(resultArea, Priority.ALWAYS);
        GridPane.setHgrow(lineChart, Priority.ALWAYS);
        GridPane.setHgrow(scatterChart, Priority.ALWAYS);

        // Добавление элементов в taskGrid
        taskGrid.add(xLabel, 0, 0);
        taskGrid.add(xInputField, 1, 0);
        taskGrid.add(yLabel, 0, 1);
        taskGrid.add(yInputField, 1, 1);
        taskGrid.add(solveButton, 0, 5);
        taskGrid.add(saveButton, 1, 5);
        taskGrid.add(resultArea, 0, 6, 2, 1);
        taskGrid.add(lineChart, 0, 7, 2, 1);

        return taskGrid;
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadDataFromFile(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл с данными");
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int index = 0;
                while ((line = br.readLine()) != null && index < x.length) {
                    String[] values = line.split(",");
                    if (values.length >= 2) {
                        x[index] = Double.parseDouble(values[0].trim());
                        y[index] = Double.parseDouble(values[1].trim());
                        index++;
                    }
                }
                showAlert("Успех", "Данные успешно загружены из файла.");
            } catch (IOException | NumberFormatException ex) {
                showAlert("Ошибка", "Не удалось загрузить данные: " + ex.getMessage());
            }
        }
    }
}