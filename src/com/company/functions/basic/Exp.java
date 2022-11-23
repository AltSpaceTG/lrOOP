package com.company.functions.basic;

import com.company.functions.Function;

public class Exp implements Function {

    double leftDomainBorder = Double.NEGATIVE_INFINITY;
    double rightDomainBorder = Double.POSITIVE_INFINITY;

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
        return Math.exp(x);
    }
}
