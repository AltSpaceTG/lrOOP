package com.company.functions.meta;

import com.company.functions.Function;

public class Scale implements Function{

    Function func;
    double xScale, yScale;

    public Scale(Function temp, double x, double y) {
        this.func = temp;
        this.xScale = x;
        this.yScale = y;
    }

    @Override
    public double getLeftDomainBorder() {
        return (func.getLeftDomainBorder()*xScale);
    }

    @Override
    public double getRightDomainBorder() {
        return (func.getRightDomainBorder()*xScale);
    }

    @Override
    public double getFunctionValue(double x) {
        return (func.getFunctionValue(x/xScale) * yScale);
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
