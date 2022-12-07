package com.company.functions;

public interface Function  {

    double getLeftDomainBorder();
    double getRightDomainBorder();
    double getFunctionValue(double x);
    double calculateIntegral(double a, double b, double step);
}
