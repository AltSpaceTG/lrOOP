package com.company.functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable {

    private double x,y;

    public FunctionPoint() {
        this.x=0;
        this.y=0;
    }

    public FunctionPoint(double _x,double _y) {
        this.x=_x;
        this.y=_y;
    }

    public FunctionPoint(FunctionPoint functionPoint) {
        this.x = functionPoint.getX();
        this.y = functionPoint.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return ("(" + x + "; " + y + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof FunctionPoint)) return false;
        return (((FunctionPoint) o).getX() == this.x && ((FunctionPoint) o).getY() == this.y);
    }

    @Override
    public int hashCode() {
        Double x1 = new Double(x), y1 = new Double(y);
        return x1.hashCode()*31+y1.hashCode();
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
