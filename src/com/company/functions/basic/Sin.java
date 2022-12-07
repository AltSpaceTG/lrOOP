package com.company.functions.basic;

import com.company.functions.TrigonometricFunction;

public class Sin implements TrigonometricFunction {

    double leftDomainBorder = Double.NEGATIVE_INFINITY;
    double rightDomainBorder = Double.POSITIVE_INFINITY;
    double functionValTopDomainBorder = 1.0;
    double functionValBotDomainBorder = -1.0;

    @Override
    public double getLeftDomainBorder() {
        return leftDomainBorder;
    }

    @Override
    public double getRightDomainBorder() {
        return rightDomainBorder;
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.sin(x);
    }

    @Override
    public double getFunctionValTopDomainBorder() {
        return functionValTopDomainBorder;
    }

    @Override
    public double getFunctionValBotDomainBorder() {
        return functionValBotDomainBorder;
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
