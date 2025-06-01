package org.example.task;

import org.example.equation.Equation;
import org.example.methods.*;

public class Task {
    Equation equantion;
    double a;
    double b;
    double accuracy;
    MethodType methodType;
    Method method;


    public Task(double a, double b, double accuracy, Equation equantion, MethodType methodType) {
        this.a = a;
        this.b = b;
        this.accuracy = accuracy;
        this.equantion = equantion;
        this.methodType = methodType;
        method = MethodGenerator.createMethod(equantion, accuracy, methodType);
    }

    public String makeAnswer(){
        String ans = "";
        ans+="Вычисление интеграла...\n";
        try {
            method.calculate(a, b);
            ans+= method.printResult();
        } catch (Exception e){
            ans+="Не удалось посчитать интеграл\n";
            ans+=e.getMessage()+'\n';
        }
        return ans;
    }
}
