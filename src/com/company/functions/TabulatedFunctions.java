package com.company.functions;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class TabulatedFunctions {

    private TabulatedFunctions() {}

    public static TabulatedFunction tabulate(FunctionType type, Function function, double leftX, double rightX, int pointsCount) {
        if(function.getRightDomainBorder()<rightX || function.getLeftDomainBorder()>leftX) throw new IllegalArgumentException();
        double[] array = new double[pointsCount];
        for(int i = 0;i<pointsCount;++i) {
            double x = leftX + ((rightX-leftX)/(pointsCount-1))*i;
            array[i] = function.getFunctionValue(x);
        }
        switch (type) {
            case ARRAY:
                return new ArrayTabulatedFunction(leftX,rightX,array);
            case LINKEDLIST:
                return new LinkedListTabulatedFunction(leftX,rightX,array);
            default:
                throw new IllegalArgumentException();
        }

    }

    public static TabulatedFunction createTabulatedFunction(FunctionType type, double leftX, double rightX, int pointCount) {
        switch (type) {
            case ARRAY:
                return new ArrayTabulatedFunction(leftX,rightX,pointCount);
            case LINKEDLIST:
                return new LinkedListTabulatedFunction(leftX,rightX,pointCount);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static TabulatedFunction createTabulatedFunction(FunctionType type, double leftX, double rightX, double values[]) {
        switch (type) {
            case ARRAY:
                return new ArrayTabulatedFunction(leftX,rightX,values);
            case LINKEDLIST:
                return new LinkedListTabulatedFunction(leftX,rightX,values);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static TabulatedFunction createTabulatedFunction(String type, double leftX, double rightX, int pointCount) {
        Class clss = null;
        try {
            clss = Class.forName("com.company.functions."+type);
            Class[] funcClassParams = {double.class, double.class, int.class};
            return (TabulatedFunction) clss.getConstructor(funcClassParams).newInstance(leftX,rightX,pointCount);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new IllegalArgumentException();
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException();
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException();
        }
    }

    public static TabulatedFunction createTabulatedFunction(String type, double leftX, double rightX, double[] values)  {
        Class clss = null;
        try {
            clss = Class.forName("com.company.functions."+type);
            Class[] funcClassParams = {double.class, double.class, double[].class};
            return (TabulatedFunction) clss.getConstructor(funcClassParams).newInstance(leftX,rightX,values);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new IllegalArgumentException();
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException();
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException();
        }
    }

    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out) {
        PrintWriter pw = new PrintWriter(out);
        int size = function.getPointsCount();
        pw.print(size + " ");
        for(int i = 0;i<size;++i) {
            pw.print( String.format("%.5g%n",function.getPointX(i)) + " " + String.format("%.5g%n",function.getPointY(i)) + " ");
        }
        pw.flush();
    }

    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws InappropriateFunctionPointException {
        Scanner s = new Scanner(in);
        int size = s.nextInt();
        FunctionPoint[] points = new FunctionPoint[size];
        for(int i = 0;i<size;++i) {
            double x = s.nextDouble(), y = s.nextDouble();
            System.out.println(x + " " + y);
            points[i] = new FunctionPoint(x,y);
        }
        return new ArrayTabulatedFunction(points);
    }

    public static void writeTabulatedFunction(TabulatedFunction function, Writer out) {
        PrintWriter pw = new PrintWriter(out);
        int size = function.getPointsCount();
        pw.print(size);
        for(int i = 0;i<size;++i) {
            pw.print(function.getPointX(i) + " " + function.getPointY(i) + " ");
        }
        pw.flush();
    }

    public static TabulatedFunction readTabulatedFunction(Reader in) throws InappropriateFunctionPointException {
        Scanner s = new Scanner(in);
        int size = s.nextInt();
        FunctionPoint[] points = new FunctionPoint[size];
        for(int i = 0;i<size;++i) {
            double x = s.nextDouble(), y = s.nextDouble();
            System.out.println(x + " " + y);
            points[i] = new FunctionPoint(x,y);
        }
        return new ArrayTabulatedFunction(points);
    }
}
