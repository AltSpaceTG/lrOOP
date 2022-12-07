package com.company.threads;

import com.company.functions.Function;

import java.util.concurrent.Semaphore;

public class Task {

    public Function func;
    public double left, right, step;
    public int itersCount;
    private boolean isReady = false;
    Semaphore sem;

    private Task() {}

    public Task(Function func, double left, double right, double step, int itersCount, Semaphore sem) {
        this.func = func;
        this.left = left;
        this.right = right;
        this.step = step;
        this.itersCount = itersCount;
        this.sem = sem;
    }

    public void setFunc(Function func) {
        this.func = func;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getStep() {
        return step;
    }

    public Function getFunc() {
        return func;
    }

    public int getItersCount() {
        return itersCount;
    }

    public void setItersCount(int itersCount) {
        this.itersCount = itersCount;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean temp) {
        this.isReady = temp;
    }

    public Semaphore getSem() {
        return sem;
    }
}
