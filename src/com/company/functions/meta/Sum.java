package com.company.functions.meta;

import com.company.functions.Function;
import com.company.functions.FunctionPoint;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

public class Sum implements Function {

    Function func1;
    Function func2;

    public Sum(Function temp1, Function temp2) {
        this.func1 = temp1;
        this.func2 = temp2;
    }

    @Override
    public double getLeftDomainBorder() {
        return (func1.getLeftDomainBorder()+func2.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return (func1.getRightDomainBorder()+func2.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return (func1.getFunctionValue(x) + func2.getFunctionValue(x));
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
