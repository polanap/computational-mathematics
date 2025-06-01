package org.example;

import javafx.scene.chart.ScatterChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import org.example.equation.Equation;
import org.example.equation.EquationSystem;
import org.example.task.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    private Equation equation = null;
    private EquationSystem equationSystem = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Решение уравнений");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(20);
        grid.setVgap(20);
        Scene scene = new Scene(grid, 1000, 1000);
        scene.setFill(Color.LIGHTGRAY);

        GridPane task1Grid = createTaskGrid("Решение уравнения", true, primaryStage);
        GridPane task2Grid = createTaskGrid("Решение системы уравнений", false, primaryStage);

        grid.add(task1Grid, 0, 0);
        grid.add(task2Grid, 1, 0);

        GridPane.setMargin(task2Grid, new Insets(0, 0, 0, 20));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GridPane createTaskGrid(String title, boolean isSingleEquation, Stage primaryStage) {
        GridPane taskGrid = new GridPane();
        taskGrid.setPadding(new Insets(10));
        taskGrid.setVgap(10);
        taskGrid.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label taskLabel = new Label(title);
        taskLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label labelEquation = new Label("Выберите уравнение:");
        ComboBox<String> equationComboBox = new ComboBox<>();
        equationComboBox.getItems().addAll(Arrays.stream(isSingleEquation ? Equation.values() : EquationSystem.values()).map(it -> it.toString()).toList());

        Label labelAccuracy = new Label("Введите точность:");
        TextField accuracyField = new TextField();

        Label labelA = new Label(isSingleEquation ? "Начальная точка (a):" : "Начальное приближение x:");
        TextField aField = new TextField();

        Label labelB = new Label(isSingleEquation ? "Конечная точка (b):" : "Начальное приближение y:");
        TextField bField = new TextField();

        Button solveButton = new Button("Решить");
        solveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;");
        solveButton.setOnMouseEntered(e -> solveButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));
        solveButton.setOnMouseExited(e -> solveButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10px; -fx-background-radius: 5;"));

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        LineChart<Number, Number> lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart.setTitle("График функции");
        lineChart.setStyle("-fx-background-color: #f0f0f0;");
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(new NumberAxis(), new NumberAxis());
        scatterChart.setTitle("График функции");
        scatterChart.setStyle("-fx-background-color: #f0f0f0;");

        solveButton.setOnAction(e -> {
            try {
                if (isSingleEquation) {
                    equation = Equation.getEquationByString(equationComboBox.getValue());
                    double accuracy = Double.parseDouble(accuracyField.getText().replace(',', '.'));
                    if(accuracy <= 0 || accuracy > 2) {throw new Exception("Недопустимое значение точности");}
                    double a = Double.parseDouble(aField.getText().replace(',', '.'));
                    double b = Double.parseDouble(bField.getText().replace(',', '.'));

                    Task task = new Task(a, b, accuracy, equation, equationSystem, false);
                    String ansText = task.makeAnswer();

                    resultArea.setText(ansText);
                    plotGraph(lineChart, equation, a, b);
                } else {
                    equationSystem = EquationSystem.getEquationByString(equationComboBox.getValue());
                    double accuracy = Double.parseDouble(accuracyField.getText().replace(',', '.'));
                    if(accuracy <= 0 || accuracy > 2) {throw new Exception("Недопустимое значение точности");}
                    double a = Double.parseDouble(aField.getText().replace(',', '.'));
                    double b = Double.parseDouble(bField.getText().replace(',', '.'));

                    Task task = new Task(a, b, accuracy, equation, equationSystem, true);
                    String ansText = task.makeAnswer();

                    resultArea.setText(ansText);
                    plotGraphSystem(scatterChart, equationSystem, -10, 10, -10, 10);
                }
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

        taskGrid.add(taskLabel, 0, 0, 2, 1);
        taskGrid.add(labelEquation, 0, 1);
        taskGrid.add(equationComboBox, 1, 1);
        taskGrid.add(labelAccuracy, 0, 2);
        taskGrid.add(accuracyField, 1, 2);
        taskGrid.add(labelA, 0, 3);
        taskGrid.add(aField, 1, 3);
        taskGrid.add(labelB, 0, 4);
        taskGrid.add(bField, 1, 4);
        taskGrid.add(solveButton, 0, 5);
        taskGrid.add(resultArea, 0, 6, 2, 1);
        taskGrid.add(saveButton, 1, 5);
        if (isSingleEquation) {
            taskGrid.add(lineChart, 0, 7, 2, 1);
        } else {
            taskGrid.add(scatterChart, 0, 7, 2, 1);
        }

        return taskGrid;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void plotGraph(LineChart<Number, Number> lineChart, Equation equation, double a, double b) {
        lineChart.getData().clear();

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("График уравнения");

        double step = (b-a)/100;
        for (double x = a; x <= b; x += step) {
            double y = equation.calculate(x);
            XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x, y);
            Circle point = new Circle(1);
            point.setFill(Color.ORANGE);
            dataPoint.setNode(point);
            dataPoint.setNode(point);
            series.getData().add(dataPoint);
        }

        lineChart.getData().add(series);
    }

    private void plotGraphSystem(ScatterChart<Number, Number> scatterChart, EquationSystem equationSystem, double xMin, double xMax, double yMin, double yMax) {
        scatterChart.getData().clear();

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("Уравнение 1");

        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("Уравнение 2");

        double stepX = 0.03;
        double stepY = 0.03;

        for (double x = xMin; x <= xMax; x += stepX) {
            for (double y = yMin; y <= yMax; y += stepY) {
                double[] results = equationSystem.calculate(x, y);
                if (Math.abs(results[0]) < 0.1) {
                    XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x, y);
                    Circle point = new Circle(1);
                    point.setFill(Color.BLUE);
                    dataPoint.setNode(point);
                    series1.getData().add(dataPoint);
                }

                if (Math.abs(results[1]) < 0.1) {
                    XYChart.Data<Number, Number> dataPoint = new XYChart.Data<>(x, y);
                    Circle point = new Circle(1);
                    point.setFill(Color.RED);
                    dataPoint.setNode(point);
                    series2.getData().add(dataPoint);
                }
            }
        }

        scatterChart.getData().addAll(series1, series2);
    }
}