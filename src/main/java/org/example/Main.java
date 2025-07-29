package org.example;import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import org.example.functions.Function;
import org.example.task.Task;

import java.io.*;
import java.util.Arrays;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class Main extends Application {

    private double[] x = new double[10];
    private double[] y = new double[10];
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

        ComboBox<String> inputMethodComboBox = new ComboBox<>();
        inputMethodComboBox.getItems().addAll("Ввод с клавиатуры", "Ввод из файла", "На основе функции");
        inputMethodComboBox.setValue("Ввод с клавиатуры"); // Default selection

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


        Label functionLabel = new Label("Выберите функцию: ");
        ComboBox<String> functionComboBox = new ComboBox<>();
        functionComboBox.getItems().addAll(Function.getAllFunctions());
        functionComboBox.setValue(Function.FUNCTION_1.toString()); // Default selection
        Label intervalLabel = new Label("Введите исследуемый интервал: ");
        TextField startInputField = new TextField();
        startInputField.setPromptText("от");
        TextField endInputField = new TextField();
        endInputField.setPromptText("до");
        Label countLabel = new Label("Введите количество точек: ");
        TextField countInputField = new TextField();
        countInputField.setPromptText("количество точек");

        Button solveButton = new Button("Решить");
        solveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;");
        solveButton.setOnMouseEntered(e -> solveButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));
        solveButton.setOnMouseExited(e -> solveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");
        resultArea.setFont(Font.font("Monospaced", 12));
        resultArea.setMinHeight(700); // Установка минимальной высоты
        resultArea.setPrefWidth(800); // Установка предпочтительной ширины

        // Show/hide input fields based on selected method
        inputMethodComboBox.setOnAction(e -> {
            if (inputMethodComboBox.getValue().equals("Ввод из файла")) {
                xLabel.setVisible(false);
                yLabel.setVisible(false);
                xValueLabel.setVisible(false);
                xInputField.setVisible(false);
                yInputField.setVisible(false);
                xValueField.setVisible(false);

                loadButton.setVisible(true);

                functionLabel.setVisible(false);
                functionComboBox.setVisible(false);
                intervalLabel.setVisible(false);
                startInputField.setVisible(false);
                endInputField.setVisible(false);
                countLabel.setVisible(false);
                countInputField.setVisible(false);
            } else if (inputMethodComboBox.getValue().equals("Ввод с клавиатуры")){
                xLabel.setVisible(true);
                yLabel.setVisible(true);
                xValueLabel.setVisible(true);
                xInputField.setVisible(true);
                yInputField.setVisible(true);
                xValueField.setVisible(true);

                loadButton.setVisible(false);

                functionLabel.setVisible(false);
                functionComboBox.setVisible(false);
                intervalLabel.setVisible(false);
                startInputField.setVisible(false);
                endInputField.setVisible(false);
                countLabel.setVisible(false);
                countInputField.setVisible(false);

            } else {
                xLabel.setVisible(false);
                yLabel.setVisible(false);
                xValueLabel.setVisible(true);
                xInputField.setVisible(false);
                yInputField.setVisible(false);
                xValueField.setVisible(true);

                loadButton.setVisible(false);

                functionLabel.setVisible(true);
                functionComboBox.setVisible(true);
                intervalLabel.setVisible(true);
                startInputField.setVisible(true);
                endInputField.setVisible(true);
                countLabel.setVisible(true);
                countInputField.setVisible(true);
            }
        });

        solveButton.setOnAction(e -> {
            try {
                if (inputMethodComboBox.getValue().equals("Ввод с клавиатуры")) {
                    x = Arrays.stream(xInputField.getText().split(","))
                            .mapToDouble(Double::parseDouble)
                            .toArray();
                    y = Arrays.stream(yInputField.getText().split(","))
                            .mapToDouble(Double::parseDouble)
                            .toArray();
                    point = Double.parseDouble(xValueField.getText().trim().replace(",", "."));

                    if (x.length != y.length) throw new Exception("Количество значений x и y должно совпадать");
                    else if (x.length < 2) throw new Exception("Количество координат должно быть не меньше 2");

                } else if (inputMethodComboBox.getValue().equals("На основе функции")) {
                    Function function = Function.getFunctionByStr(functionComboBox.getValue());
                    Double start = Double.parseDouble(startInputField.getText().trim().replace(",", "."));
                    Double end = Double.parseDouble(endInputField.getText().trim().replace(",", "."));
                    int count = Integer.parseInt(countInputField.getText().trim().replace(",", ""));
                    point = Double.parseDouble(xValueField.getText().trim().replace(",", "."));

                    if (start >= end) throw new Exception("Начало отрезка должно быть меньше его конца");
                    else if (count < 2 || count > 20)
                        throw new Exception("Количество точек должно быть не меньше 2 и не больше 10");

                    x = new double[count];
                    y = new double[count];

                    for (int i = 0; i < count; i++) {
                        x[i] = start + (end - start) * i / (count - 1);
                        y[i] = function.calculate(x[i]);
                    }
                }

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

        // Добавление элементов в taskGrid
        taskGrid.add(new Label("Способ ввода:"), 0, 0);
        taskGrid.add(inputMethodComboBox, 1, 0);
        taskGrid.add(xLabel, 0, 1);
        taskGrid.add(xInputField, 1, 1);
        taskGrid.add(yLabel, 0, 2);
        taskGrid.add(yInputField, 1, 2);
        taskGrid.add(xValueLabel, 0, 4);
        taskGrid.add(xValueField, 1, 4);

        taskGrid.add(loadButton, 0, 1);

        taskGrid.add(functionLabel, 0, 1);
        taskGrid.add(functionComboBox, 1, 1);
        taskGrid.add(intervalLabel, 0, 2);
        GridPane intervalGrid = new GridPane();
        intervalGrid.add(startInputField, 0, 0);
        intervalGrid.add(endInputField, 1, 0);
        taskGrid.add(intervalGrid, 1, 2);
        taskGrid.add(countLabel, 0, 3);
        taskGrid.add(countInputField, 1, 3);

        taskGrid.add(solveButton, 0, 5);
        taskGrid.add(saveButton, 1, 5);
        taskGrid.add(resultArea, 0, 6, 2, 1);

        // Initially hide input fields for file input
        xLabel.setVisible(true);
        yLabel.setVisible(true);
        xValueLabel.setVisible(true);
        xInputField.setVisible(true);
        yInputField.setVisible(true);
        xValueField.setVisible(true);

        loadButton.setVisible(false);

        functionLabel.setVisible(false);
        functionComboBox.setVisible(false);
        intervalLabel.setVisible(false);
        startInputField.setVisible(false);
        endInputField.setVisible(false);
        countLabel.setVisible(false);
        countInputField.setVisible(false);

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
                x = Arrays.stream(br.readLine().split(","))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                y = Arrays.stream(br.readLine().split(","))
                        .mapToDouble(Double::parseDouble)
                        .toArray();
                point = Double.parseDouble(br.readLine().trim().replace(",", "."));
                showAlert("Успех", "Данные успешно загружены из файла.");
            } catch (IOException | NumberFormatException ex) {
                showAlert("Ошибка", "Не удалось загрузить данные: " + ex.getMessage());
            }
        }
    }
}

