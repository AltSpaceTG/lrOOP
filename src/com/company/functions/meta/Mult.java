package com.company.functions.meta;

import com.company.functions.Function;

public class Mult implements Function{

    Function func1;
    Function func2;

    public Mult(Function temp1, Function temp2) {
        this.func1 = temp1;
        this.func2 = temp2;
    }

    @Override
    public double getLeftDomainBorder() {
        return (func1.getLeftDomainBorder() * func2.getLeftDomainBorder());
    }

    @Override
    public double getRightDomainBorder() {
        return (func1.getRightDomainBorder() * func2.getRightDomainBorder());
    }

    @Override
    public double getFunctionValue(double x) {
        return (func1.getFunctionValue(x) * func2.getFunctionValue(x));
    }

}
