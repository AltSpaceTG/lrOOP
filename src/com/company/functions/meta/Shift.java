package com.company.functions.meta;

import com.company.functions.Function;

public class Shift implements Function {

    Function func;
    double xShift, yShift;

    public Shift(Function temp, double x, double y) {
        this.func = temp;
        this.xShift = x;
        this.yShift = y;
    }

    @Override
    public double getLeftDomainBorder() {
        return (func.getLeftDomainBorder()+xShift);
    }

    @Override
    public double getRightDomainBorder() {
        return (func.getRightDomainBorder()-yShift);
    }

    @Override
    public double getFunctionValue(double x) {
        return (func.getFunctionValue(x+xShift) + yShift);
    }
}
