package org.example.task;

import org.example.equation.Equation;
import org.example.equation.EquationSystem;
import org.example.methods.ChordMethod;
import org.example.methods.Method;
import org.example.methods.SecantMethod;
import org.example.methods.SimpleIterationMethod;
import org.example.methods.system.NewtonMethod;

public class Task {
    Equation equantion = null;
    EquationSystem equationSystem = null;
    boolean isSystem = false;
    double a;
    double b;
    double accuracy;
    ChordMethod chordMethod;
    SecantMethod secantMethod;
    SimpleIterationMethod simpleIterationMethod;
    NewtonMethod newtonMethod;


    public Task(double a, double b, double accuracy, Equation equantion, EquationSystem equationSystem) {
        this.a = a;
        this.b = b;
        this.accuracy = accuracy;
        this.equantion = equantion;
        this.equationSystem = equationSystem;
        this.chordMethod = new ChordMethod(equantion, accuracy);
        this.secantMethod = new SecantMethod(equantion, accuracy);
        this.simpleIterationMethod = new SimpleIterationMethod(equantion, accuracy);
        this.newtonMethod = new NewtonMethod(equationSystem, accuracy);
        if (equantion == null ) {
            isSystem = true;
        }
    }

    public void makeAnswer(){
        if (!isSystem){
            try {
                System.out.println("Проверка интервала на наличие корней...");
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
                System.out.println("Вычисление корня методом хорд:");
                chordMethod.calculate(a, b);
                chordMethod.printResult();
                System.out.println("Вычисление корня методом секущих:");
                secantMethod.calculate(a, b);
                secantMethod.printResult();
                System.out.println("Вычисление корня методом простой итерации:");
                System.out.println("Проверка достаточного условия сходимости на интервале...");
                if (simpleIterationMethod.isConverged(a, b)) {
                    System.out.println("Алгоритм сходится");
                    simpleIterationMethod.calculate(a, b);
                    simpleIterationMethod.printResult();
                } else {
                    System.out.println("Алгоритм не сходится -- ответ получить не удастся");
                }
            } catch (Exception e){
                System.out.println("Не удалось посчитать корень");
                System.out.println(e);
            }
        } else {
            System.out.println("Вычисление решения системы...");
            try {
                newtonMethod.calculate(a, b);
                newtonMethod.printResult();
            } catch (Exception e){
                System.out.println("Не удалось посчитать корень");
                System.out.println(e.getMessage());
            }
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
