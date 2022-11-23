package com.company.functions.basic;

import com.company.functions.TrigonometricFunction;

public class Cos implements TrigonometricFunction {

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
        return Math.cos(x);
    }

    @Override
    public double getFunctionValTopDomainBorder() {
        return functionValTopDomainBorder;
    }

    @Override
    public double getFunctionValBotDomainBorder() {
        return functionValBotDomainBorder;
    }
}
