package com.company.functions.meta;

import com.company.functions.Function;

public class Composition implements Function {

    Function func1, func2;

    public Composition(Function x, Function y) {
        func1 = x;
        func2 = y;
    }

    @Override
    public double getLeftDomainBorder() {
        return (func1.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return (func1.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return (func2.getFunctionValue(func1.getFunctionValue(x)));
    }

    @Override
    public double calculateIntegral(double a, double b, double step) {
        if (a>this.getRightDomainBorder() || b<this.getLeftDomainBorder() || a<b) throw new IllegalArgumentException();
        double rezult = 0;
        while(a>b) {
            rezult += (this.getFunctionValue(b) + this.getFunctionValue(b+step))*step/2;
            b+=step;
        }
        return rezult;
    }

}
