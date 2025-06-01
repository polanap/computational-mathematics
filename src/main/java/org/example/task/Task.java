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


    public Task(double a, double b, double accuracy, Equation equantion, EquationSystem equationSystem, boolean isSystem) {
        this.a = a;
        this.b = b;
        this.accuracy = accuracy;
        this.equantion = equantion;
        this.equationSystem = equationSystem;
        this.chordMethod = new ChordMethod(equantion, accuracy);
        this.secantMethod = new SecantMethod(equantion, accuracy);
        this.simpleIterationMethod = new SimpleIterationMethod(equantion, accuracy);
        this.newtonMethod = new NewtonMethod(equationSystem, accuracy);
        this.isSystem = isSystem;
    }

    public String makeAnswer(){
        String ans = "";
        if (!isSystem){
            try {
                ans+="Проверка интервала на наличие корней...\n";
                switch (countRoots()){
                    case 0:
                        ans+="На заданном интервале нет корней\n";
                        throw new Exception("Невозможно найти ответ, так как на заданном интервале нет корней");
                    case 1:
                        ans+="На заданном интервале найден 1 корень\n";
                        break;
                    default:
                        ans+="На заданном интервале найдено более 1 корня\n";
                        throw new Exception("Невозможно найти ответ, так как на заданном интервале найдено более 1 корня");
                }
                ans+="Вычисление корня методом хорд:\n";
                try {
                    chordMethod.calculate(a, b);
                    ans+=chordMethod.printResult();
                }catch (Exception e){
                    ans+=e.getMessage()+'\n';
                }
                try {
                    ans += "Вычисление корня методом секущих:\n";
                    secantMethod.calculate(a, b);
                    ans+=secantMethod.printResult();
                }catch (Exception e){
                    ans+=e.getMessage()+'\n';
                }
                ans+="Вычисление корня методом простой итерации:\n";
                ans+="Проверка достаточного условия сходимости на интервале...\n";
                if (simpleIterationMethod.isConverged(a, b)) {
                    ans+="Алгоритм сходится\n";
                    simpleIterationMethod.calculate(a, b);
                    ans+=simpleIterationMethod.printResult();
                } else {
                    ans+="Алгоритм не сходится -- ответ получить не удастся\n";
                }
            } catch (Exception e){
                ans+="Не удалось посчитать корень\n";
                ans+=e.getMessage()+'\n';
            }
        } else {
            ans+="Вычисление решения системы...\n";
            try {
                newtonMethod.calculate(a, b);
                ans+=newtonMethod.printResult();
            } catch (Exception e){
                ans+="Не удалось посчитать корень\n";
                ans+=e.getMessage()+'\n';
            }
        }
        return ans;
    }

    public int countRoots() {
        int roots = 0;
        double previous = a;
        double current = a + accuracy;
        if (equantion.calculate(previous) == 0) roots += 1;
        while (current <= b) {
            if (equantion.calculate(previous) * equantion.calculate(current) < 0 || equantion.calculate(current) == 0)
                roots += 1;
            previous = current;
            current += accuracy;
        }
        return roots;
    }

}
