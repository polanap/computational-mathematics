package org.example.methods.system;

import org.example.equation.EquationSystem;

public class NewtonMethod extends SystemMethod {

    public NewtonMethod(EquationSystem equantion, double accuracy) {
        super(equantion, accuracy);
    }

    @Override
    public double[] calculate(double x, double y) {
        var system = new double[2];
        var derivative = equantion.derivative(x, y);
        
        return system;
    }
}
