package org.example;import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import org.example.functions.Function;
import org.example.task.Task;

import java.io.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Решение ОДУ");

        Label taskLabel = new Label("Решение ОДУ");
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


        Label functionLabel = new Label("Выберите ОДУ: ");
        ComboBox<String> functionComboBox = new ComboBox<>();
        functionComboBox.getItems().addAll(Function.getAllFunctions());
        functionComboBox.setValue(Function.FUNCTION_1.toString()); // Default selection

        Label basicLabel = new Label("Введите начальные условия: y0 = y(x0)  ");
        TextField xInputField = new TextField();
        xInputField.setPromptText("x0");
        TextField yInputField = new TextField();
        yInputField.setPromptText("y0");

        Label intervalLabel = new Label("Введите исследуемый интервал: ");
        TextField startInputField = new TextField();
        startInputField.setPromptText("от");
        TextField endInputField = new TextField();
        endInputField.setPromptText("до");

        Label hLabel = new Label("Введите шаг h: ");
        TextField hInputField = new TextField();
        hInputField.setPromptText("h");

        Label epsLabel = new Label("Введите точность ε: ");
        TextField epsInputField = new TextField();
        epsInputField.setPromptText("ε");

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


        solveButton.setOnAction(e -> {
            try {
                Function function = Function.getFunctionByStr(functionComboBox.getValue());
                Double start = Double.parseDouble(startInputField.getText().trim().replace(",", "."));
                Double end = Double.parseDouble(endInputField.getText().trim().replace(",", "."));
                Double h = Double.parseDouble(hInputField.getText().trim().replace(",", ""));
                Double eps = Double.parseDouble(epsInputField.getText().trim().replace(",", ""));
                Double x = Double.parseDouble(xInputField.getText().trim().replace(",", ""));
                Double y = Double.parseDouble(yInputField.getText().trim().replace(",", ""));

                if (start >= end) throw new Exception("Начало отрезка должно быть меньше его конца");

                Task task = new Task(function, start, end, h, eps, x, y);
                String ansText = task.makeAnswer();

                Label plotLabel = new Label("Графики");
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

        GridPane.setHgrow(resultArea, Priority.ALWAYS);

        taskGrid.add(functionLabel, 0, 0);
        taskGrid.add(functionComboBox, 1, 0);

        taskGrid.add(basicLabel, 0, 1);
        GridPane basicGrid = new GridPane();
        basicGrid.add(xInputField, 0, 0);
        basicGrid.add(yInputField, 1, 0);
        taskGrid.add(basicGrid, 1, 1);

        taskGrid.add(intervalLabel, 0, 2);
        GridPane intervalGrid = new GridPane();
        intervalGrid.add(startInputField, 0, 0);
        intervalGrid.add(endInputField, 1, 0);
        taskGrid.add(intervalGrid, 1, 2);

        taskGrid.add(hLabel, 0, 3);
        taskGrid.add(hInputField, 1, 3);

        taskGrid.add(epsLabel, 0, 4);
        taskGrid.add(epsInputField, 1, 4);

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

}

