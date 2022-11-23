package com.company.functions.basic;

import com.company.functions.TrigonometricFunction;

public class Tan implements TrigonometricFunction {

    double leftDomainBorder = Double.NEGATIVE_INFINITY;
    double rightDomainBorder = Double.POSITIVE_INFINITY;
    double functionValTopDomainBorder = Double.POSITIVE_INFINITY;
    double functionValBotDomainBorder = Double.NEGATIVE_INFINITY;

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
        return Math.tan(x);
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
