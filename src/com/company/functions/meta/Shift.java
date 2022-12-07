package com.company.functions.meta;

import com.company.functions.Function;
import com.company.functions.FunctionPoint;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

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
