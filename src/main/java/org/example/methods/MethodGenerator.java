package org.example.methods;

import org.example.equation.Equation;

public class MethodGenerator {
    public static Method createMethod(Equation equantion, double accuracy, MethodType method) {
        switch (method) {
            case RECTANGLE_LEFT -> {
                RectangleMethod m = new RectangleMethod(equantion, accuracy);
                m.setModification(RectangleMethod.RectangleModification.LEFT);
                return m;
            }
            case RECTANGLE_RIGHT -> {
                RectangleMethod m = new RectangleMethod(equantion, accuracy);
                m.setModification(RectangleMethod.RectangleModification.RIGHT);
                return m;
            }
            case RECTANGLE_MIDDLE -> {
                RectangleMethod m = new RectangleMethod(equantion, accuracy);
                m.setModification(RectangleMethod.RectangleModification.MIDDLE);
                return m;
            }
            case SIMPSON -> {
                return new SympsonMethod(equantion, accuracy);
            }
            case TRAPEZIUM -> {
                return new TrapeziumMethod(equantion, accuracy);
            }
        }
        return null;
    }
}
