package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static double [][] randomMatrix(int n) {
        double [][] matrix = new double[n][n+1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Math.random()*100-50;
            }
            matrix[i][i] = 2000;
            matrix[i][n] = Math.random()*100-50;
        }
        return matrix;
    }
    public static void main(String[] args) {
        int dimension;
        double accuracy;
        double [][] A;
        double [] B;
        double [][] M;
        if (args.length == 0) {
            Scanner in = new Scanner(System.in);
            boolean notExit = true;
            String command;
            while (notExit) {
                System.out.println("Введите размер матрицы: ");
                while (true) {
                    try {
                        command = in.next();
                        dimension = Integer.parseInt(command);
                        if (dimension <= 0 || dimension > 20) {
                            System.out.println("Недопустимый размер матрицы. (0 < n <= 20)");
                            return;
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Недопустимое значение. Попробуйте снова");
                    }
                }
                A = new double[dimension][dimension];
                B = new double[dimension];
                while (true) {
                    try {
                        System.out.println("Хотите вычислить ответ для случайной матрицы? (+/-)");
                        command = in.next();
                        command = command.replaceAll("\s", "");
                        if (command.equals("+")) {
                            M = randomMatrix(dimension);
                            System.out.println("Случайная матрица:");
                            for (int i = 0; i < dimension; i++) {
                                for (int j = 0; j < dimension; j++) {
                                    A[i][j] = M[i][j];
                                    System.out.print(String.format("%.5f ", A[i][j]));
                                }
                                B[i] = M[i][dimension];
                                System.out.print(String.format("%.5f \n", B[i]));
                            }
                            break;
                        } else if (command.equals("-")){
                            while (true) {
                                try {
                                    for (int i = 0; i < dimension; i++) {
                                        for (int j = 0; j < dimension; j++) {
                                            command = in.next();
                                            command = command.replace(',', '.');
                                            A[i][j] = Double.parseDouble(command);
                                        }
                                        B[i] = Double.parseDouble(command);
                                    }
                                    break;
                                } catch (Exception e) {
                                    System.out.println("Недопустимое значение. Попробуйте снова");
                                }
                            }
                            break;
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("Не удалось распознать ответ. Введите да/нет");
                    }
                }
                System.out.println("Введите расширенную матрицу СЛАУ: ");
                while (true) {
                    try {
                        System.out.println("Введите точность, с которой необходимо получить решение: ");
                        command = in.next();
                        command = command.replace(',', '.');
                        accuracy = Double.parseDouble(command);
                        break;
                    } catch (Exception e) {
                        System.out.println("Недопустимое значение. Попробуйте снова");
                    }
                }
                var task = new Task(A, B, accuracy);
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
                        System.out.println("Не удалось распознать ответ. Введите да/нет");
                    }
                }
            }
            in.close();
        } else {
            try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
                String [] line;
                dimension = Integer.parseInt(br.readLine());
                A = new double[dimension][dimension];
                B = new double[dimension];
                for (int i = 0; i < dimension; i++) {
                    line = br.readLine().split(" ");
                    for (int j = 0; j < dimension + 1; j++) {
                        if (j == dimension) {
                            B[i] = Double.parseDouble(line[j]);
                        } else {
                            A[i][j] = Double.parseDouble(line[j]);
                        }
                    }
                }
                accuracy = Double.parseDouble(br.readLine());

            }
            catch(IOException ex){
                System.out.println(String.format("При попытке чтения из файла произошла ошибка: %s", ex.getMessage()));
                return;
            }
            catch(NumberFormatException ex){
                System.out.println(String.format("На вход переданы некорректные данные: %s", ex.getMessage()));
                return;
            }
            var task = new Task(A, B, accuracy);
            task.makeAnswer();
        }

    }
}