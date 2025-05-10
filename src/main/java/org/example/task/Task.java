package org.example.task;

import org.example.equation.Equation;
import org.example.methods.ChordMethod;
import org.example.methods.SecantMethod;
import org.example.methods.SimpleIterationMethod;

public class Task {
    Equation equantion = null;
    boolean isSystem = true;
    double a;
    double b;
    double accuracy;
    ChordMethod chordMethod;
    SecantMethod secantMethod;
    SimpleIterationMethod simpleIterationMethod;


    public Task(double a, double b, double accuracy, Equation equantion) {
        this.a = a;
        this.b = b;
        this.accuracy = accuracy;
        this.equantion = equantion;
        this.chordMethod = new ChordMethod(equantion, accuracy);
        this.secantMethod = new SecantMethod(equantion, accuracy);
        this.simpleIterationMethod = new SimpleIterationMethod(equantion, accuracy);
    }

    public void makeAnswer(){
        System.out.println("Проверка интервала на наличие корней...");
        try {
            switch (countRoots()){
                case 0:
                    System.out.println("На заданном интервале нет корней");
                    throw new Exception();
                case 1:
                    System.out.println("На заданном интервале найден 1 корень");
                    break;
                default:
                    System.out.println("На заданном интервале найдено более 1 корня");
                    throw new Exception();
            }
            if (isSystem){
                System.out.println("Вычисление корня методом хорд:");
                System.out.println(chordMethod.calculate(a, b));
                System.out.println("Вычисление корня методом секущих:");
                System.out.println(secantMethod.calculate(a, b));
                System.out.println("Вычисление корня методом простой итерации:");
                System.out.println("Проверка достаточного условия сходимости на интервале...");
                if (simpleIterationMethod.isConverged(a, b)) {
                    System.out.println("Алгоритм сходится");
                    System.out.println(simpleIterationMethod.calculate(a, b));
                } else {
                    System.out.println("Алгоритм не сходится -- ответ получить не удастся");
                }
            } else {
                System.out.println("Вычисление решения системы...");
            }
        } catch (Exception e){
            System.out.println("Не удалось посчитать корень");
            System.out.println(e);
        }

    }

    public int countRoots() {
            int roots = 0;
            double previous = a;
            double current = a + accuracy;
            if (previous == 0) roots += 1;
            while (current <= b) {
                if (equantion.calculate(previous) * equantion.calculate(current) < 0 || equantion.calculate(current) == 0)
                    roots += 1;
                previous = current;
                current += accuracy;
            }
            return roots;
    }

}
