package com.company.functions.meta;

import com.company.functions.Function;

public class Power implements Function {

    Function func;
    int n;

    public Power(Function temp1, int power) {
        this.func = temp1;
        this.n = power;
    }

    @Override
    public double getLeftDomainBorder() {
        return func.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return func.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        double temp = func.getFunctionValue(x);
        double rez = temp;
        for (int i=1; i<n ; ++i) {
            rez*=temp;
        }
        return rez;
    }
}
