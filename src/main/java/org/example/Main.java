package org.example;

import org.example.equation.Equation;
import org.example.equation.EquationSystem;
import org.example.graph.FunctionGraph;
import org.example.task.Task;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Equation equantion = null;
        EquationSystem equationSystem = null;
        boolean isSystem = true;
        double a;
        double b;
        double accuracy;

        if (args.length == 0) {
            Scanner in = new Scanner(System.in);
            boolean notExit = true;
            String command;
            while (notExit) {
                while (true) {
                    try {
                        System.out.println("Решить уравнение (1) или систему уравнений (2)?");
                        command = in.next();
                        command = command.replaceAll("\s", "");
                        if (command.equals("1")) {
                            isSystem = false;
                            break;
                        } else if (command.equals("2")){
                            isSystem = true;
                            break;
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("Не удалось распознать ответ. Введите 1, если хотите найти решение уравнения, или 2, чтобы решить систему");
                    }
                }
                if (isSystem) {
                    System.out.println("Выберете систему уравнений, решение которой нужно найти: ");
                    System.out.println(EquationSystem.getAllEquantions());
                    while (true) {
                        try {
                            command = in.next();
                            equationSystem = EquationSystem.getEquationById(command);
                            break;
                        } catch (Exception e) {
                            System.out.println("Недопустимое значение. Попробуйте снова");
                        }
                    }
                } else {
                    System.out.println("Выберете уравнение, корни которого нужно найти: ");
                    System.out.println(Equation.getAllEquantions());
                    while (true) {
                        try {
                            command = in.next();
                            equantion = Equation.getEquationById(command);
                            break;
                        } catch (Exception e) {
                            System.out.println("Недопустимое значение. Попробуйте снова");
                        }
                    }
                }

                System.out.println("Введите точность, с которой необходимо получить решение: ");
                while (true) {
                    try {
                        command = in.next();
                        command = command.replace(',', '.');
                        accuracy = Double.parseDouble(command);
                        break;
                    } catch (Exception e) {
                        System.out.println("Недопустимое значение. Попробуйте снова");
                    }
                }

                if (!isSystem) {
                    System.out.println("Введите начальную точку интервала: ");
                    while (true) {
                        try {
                            command = in.next();
                            command = command.replace(',', '.');
                            a = Double.parseDouble(command);
                            break;
                        } catch (Exception e) {
                            System.out.println("Недопустимое значение. Попробуйте снова");
                        }
                    }

                    System.out.println("Введите конечную точку интервала: ");
                    while (true) {
                        try {
                            command = in.next();
                            command = command.replace(',', '.');
                            b = Double.parseDouble(command);
                            break;
                        } catch (Exception e) {
                            System.out.println("Недопустимое значение. Попробуйте снова");
                        }
                    }
                    Equation finalEquantion = equantion;
                    double finalA = a;
                    double finalB = b;
                    SwingUtilities.invokeLater(() -> {
                        FunctionGraph example = new FunctionGraph(finalEquantion, finalA, finalB);
                        example.setSize(800, 600);
                        example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        example.setLocationRelativeTo(null);
                        example.setVisible(true);
                    });
                } else {
                    EquationSystem finalEquantion = equationSystem;
                    SwingUtilities.invokeLater(() -> {
                        FunctionGraph example = new FunctionGraph(finalEquantion);
                        example.setSize(800, 600);
                        example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        example.setLocationRelativeTo(null);
                        example.setVisible(true);
                    });
                    System.out.println("Введите начальное приближение х: ");
                    while (true) {
                        try {
                            command = in.next();
                            command = command.replace(',', '.');
                            a = Double.parseDouble(command);
                            break;
                        } catch (Exception e) {
                            System.out.println("Недопустимое значение. Попробуйте снова");
                        }
                    }

                    System.out.println("Введите начальное приближение у: ");
                    while (true) {
                        try {
                            command = in.next();
                            command = command.replace(',', '.');
                            b = Double.parseDouble(command);
                            break;
                        } catch (Exception e) {
                            System.out.println("Недопустимое значение. Попробуйте снова");
                        }
                    }

                }
                var task = new Task(a, b, accuracy, equantion, equationSystem);
                task.makeAnswer();


                while (true) {
                    try {
                        System.out.println("Хотите продолжить? (+/-)");
                        command = in.next();
                        command = command.replaceAll("\s", "");
                        if (command.equals("+")) {
                            break;
                        } else if (command.equals("-")){
                            notExit = false;
                            break;
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("Не удалось распознать ответ. Введите +/-");
                    }
                }
            }
            in.close();
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
                String [] line;
                String command = br.readLine().replaceAll("\s", "");
                if (command.equals("1")) {
                    isSystem = false;
                } else if (command.equals("2")) {
                    isSystem = true;
                }
                if (!isSystem){
                    equantion = Equation.getEquationById(command);
                } else {
                    equationSystem = EquationSystem.getEquationById(command);
                }
                accuracy = Double.parseDouble(br.readLine());
                a = Double.parseDouble(br.readLine());
                b = Double.parseDouble(br.readLine());

            }
            catch(IOException ex){
                System.out.println(String.format("При попытке чтения из файла произошла ошибка: %s", ex.getMessage()));
                return;
            }
            catch(NumberFormatException ex){
                System.out.println(String.format("На вход переданы некорректные данные: %s", ex.getMessage()));
                return;
            }
            var task = new Task(a, b, accuracy, equantion, equationSystem);
            task.makeAnswer();
        }

    }
}