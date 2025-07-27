package org.example;

import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import org.example.task.Task;

import java.io.*;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Application;
import javafx.scene.Scene;
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
    private double point;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Интерполяция функции");

        Label taskLabel = new Label("Интерполяция функции");
        taskLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(20);
        grid.setVgap(20);
        Scene scene = new Scene(grid, 1400, 1000);
        scene.setFill(Color.LIGHTGRAY);

        GridPane task1Grid = createTaskGrid(primaryStage, grid);

        grid.add(taskLabel, 0, 0);
        grid.add(task1Grid, 0, 1);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createTaskGrid(Stage primaryStage, GridPane gridPane) {
        GridPane taskGrid = new GridPane();
        taskGrid.setPadding(new Insets(10));
        taskGrid.setVgap(10);
        taskGrid.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        TextField xInputField = new TextField();
        Label xLabel = new Label("Введите значения X (через запятую): ");
        xInputField.setPromptText("значения X");
        TextField yInputField = new TextField();
        Label yLabel = new Label("Введите значения Y (через запятую): ");
        yInputField.setPromptText("значения Y");
        TextField xValueField = new TextField();
        Label xValueLabel = new Label("Введите значение точки x");
        xValueField.setPromptText("точка x");

        Button loadButton = new Button("Загрузить из файла");
        loadButton.setOnAction(e -> loadDataFromFile(primaryStage));

        Button solveButton = new Button("Решить");
        solveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;");
        solveButton.setOnMouseEntered(e -> solveButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));
        solveButton.setOnMouseExited(e -> solveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));

        taskGrid.setMinWidth(800);
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        resultArea.setFont(Font.font("Monospaced", 12));
        resultArea.setMinHeight(700); // Установка минимальной высоты
        resultArea.setPrefWidth(800); // Установка предпочтительной ширины

        solveButton.setOnAction(e -> {
            try {
                x = Arrays.stream(xInputField.getText().split(","))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                y = Arrays.stream(yInputField.getText().split(","))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                point = Double.parseDouble(xValueField.getText().trim().replace(",", "."));

                if (x.length != y.length) throw new Exception("Количество значений x и y должно совпадать");
                else if (x.length < 2) throw new Exception("Количество координат должно быть не меньше 2");

                Task task = new Task(x, y, point);
                String ansText = task.makeAnswer();

                Label plotLabel = new Label("Графики интерполяционных функций");
                plotLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
                gridPane.add(plotLabel, 1, 0);
                gridPane.add(task.createPlotGrid(), 1, 1);
                System.out.println(ansText);
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
//        GridPane.setHgrow(lineChart, Priority.ALWAYS);

        // Добавление элементов в taskGrid
        taskGrid.add(xLabel, 0, 0);
        taskGrid.add(xInputField, 1, 0);
        taskGrid.add(yLabel, 0, 1);
        taskGrid.add(yInputField, 1, 1);
        taskGrid.add(xValueLabel, 0, 2);
        taskGrid.add(xValueField, 1, 2);
        taskGrid.add(solveButton, 0, 5);
        taskGrid.add(saveButton, 1, 5);
        taskGrid.add(resultArea, 0, 6, 2, 1);

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
