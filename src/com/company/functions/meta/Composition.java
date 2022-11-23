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

}
