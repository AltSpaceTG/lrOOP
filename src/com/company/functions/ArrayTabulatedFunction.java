package com.company.functions;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import static java.lang.Double.NaN;

public class ArrayTabulatedFunction implements TabulatedFunction, Serializable{

    FunctionPoint[] array = new FunctionPoint[0];
    int size = 0;

    public ArrayTabulatedFunction() {};

    public ArrayTabulatedFunction(double leftX, double rightX, int pointCount) {
        if((pointCount < 2) || (rightX <= leftX)) throw new IllegalArgumentException();
        this.array = new FunctionPoint[pointCount];
        this.size = pointCount;
        for(int i = 0;i<pointCount;++i) {
            FunctionPoint functionPoint = new FunctionPoint(leftX + ((rightX-leftX)/(pointCount-1))*i,0);
            this.array[i] = functionPoint;
        }
    }

    public ArrayTabulatedFunction(double leftX, double rightX, double values[]) {
        if((values.length < 2) || (rightX <= leftX)) throw new IllegalArgumentException();
        int pointCount = values.length;
        this.size = values.length;
        this.array = new FunctionPoint[pointCount];
        for(int i = 0;i<pointCount;++i) {
            FunctionPoint functionPoint = new FunctionPoint(leftX + ((rightX-leftX)/(pointCount-1))*i,values[i]);
            this.array[i] = functionPoint;
        }
    }

    public ArrayTabulatedFunction(FunctionPoint points[]) {
        if(points.length < 2) throw new IllegalArgumentException();
        this.size = points.length;
        this.array = points;
    }

    public double getLeftDomainBorder() {
        return (this.size == 0) ? NaN : this.array[0].getX();
    }

    public double getRightDomainBorder() {
        return (this.size == 0) ? NaN : this.array[size-1].getX();
    }

    public double getFunctionValue(double x) {
        if(x<getLeftDomainBorder() || x>getRightDomainBorder()) return NaN;
        for(int i = 0;i< this.size;++i) {
            if(x<array[i].getX()) {
                double x1 = this.array[i-1].getX(), x2 = this.array[i].getX(), y1,y2;
                y1 = this.array[i-1].getY(); y2 = this.array[i].getY();
                return  (((x-x1)/(x2-x1))*(y2-y1) + y1);

            }
        }
        return NaN;
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

    public int getPointsCount() {
        return this.size;
    }

    public FunctionPoint getPoint(int index) {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
                return this.array[index];
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
        if      (((index == 0) ? false : (this.array[index+1].getX()<=point.getX()))
                ||
                ((index == (size-1)) ? false : (this.array[index-1].getX()>=point.getX()))) throw new InappropriateFunctionPointException();
        this.array[index] = point;
    }

    public double getPointX(int index) {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
        return this.array[index].getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if((index < 0) || (index >= size) || (x>this.getRightDomainBorder() || x<this.getLeftDomainBorder())) throw new FunctionPointIndexOutOfBoundsExaption();
        if      (((index == 0) ? false : (this.array[index-1].getX()<=x))
                ||
                ((index == (size-1)) ? false : (this.array[index+1].getX()>=x))) throw new InappropriateFunctionPointException();
        double temp = this.array[index].getY();
        this.array[index] = new FunctionPoint(x, temp);
    }

    public double getPointY(int index) {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
        return this.array[index].getY();
    }

    public void setPointY(int index, double y) {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
        this.array[index].setY(y);
    }

    public void deletePoint(int index) {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
        if(size <= 3) throw new IllegalStateException();
        --this.size;

        System.arraycopy(this.array,0,this.array,0, index);
        System.arraycopy(this.array,index+1,this.array,index, this.size-index);
        System.out.println(size);
        print();
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if(this.isPointExists(point.getX())) throw new InappropriateFunctionPointException();
        if(this.size==array.length) {
            for(int index = 0;index<array.length;++index) { // ЕСЛИ ЭЛЕМЕНТ НЕ НАШЕЛ, ТО ДОБАВИТЬ В КОНЕЦ
                if(point.getX()<this.array[index].getX() || index == size) {
                    FunctionPoint[] temp = new FunctionPoint[size+1];
                    System.arraycopy(this.array,0,temp,0, index);
                    System.arraycopy(this.array,index,temp,index+1, size-index);
                    temp[index] = point;
                    this.array = temp;
                    ++this.size;
                    return;
                }
            }
        }
        for(int index = 0;index<this.size;++index) {
            if(point.getX()<this.array[index].getX()) {
                System.arraycopy(this.array,0,array,0, index);
                for(int i = size-1;i>index;--i){
                    this.array[i] = this.array[i-1];
                }
                this.array[index] = point;
                ++this.size;
                return;
            }
        }

    }

    public boolean isPointExists(double x) {
        for(int index = 0;index<this.size;++index) {
            if(this.array[index].getX() == x) return true;
        }
        return false;
    }

    public void print () {
        for(int i = 0;i<size;++i) {
            System.out.println("(" + this.array[i].getX() + ";" + this.array[i].getY() + ")");
        }
    }

    @Override
    public String toString() {
        String result = "{";
        for(int i = 0;i<size;++i) {
            result += this.getPoint(i).toString();
            if(i!=size-1) result+= ", ";
        }
        result += "}";
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ArrayTabulatedFunction)) return false;
        boolean result = true;
        for (int i=0;i<size; ++i) {
            result = (result && (this.getPoint(i).equals(((ArrayTabulatedFunction) o).getPoint(i))));
        }
        return result;
    }

    @Override
    public int hashCode() {
        int result = size;
        for (int i=0;i<size; ++i) {
            result = (int) (result*31 + this.getPoint(i).getX());
            result = (int) (result*31 + this.getPoint(i).getY());
        }
        return result;
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Override
    public Iterator<FunctionPoint> iterator() {
        return Arrays.stream(array).iterator();
    }
}
