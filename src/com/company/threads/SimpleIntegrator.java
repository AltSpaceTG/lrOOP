package com.company.threads;

import com.sun.corba.se.impl.presentation.rmi.StubFactoryStaticImpl;

public class SimpleIntegrator implements Runnable {

    final Task task;

    public SimpleIntegrator(Task task) {this.task = task; }

    @Override
    public void run() {
        double left, right, step, result;
        for (int i = 0; i < task.getItersCount(); ++i) {

            while (!task.isReady()) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            synchronized (task) {

                left = task.getLeft();
                right = task.getRight();
                step = task.getStep();
                result = task.getFunc().calculateIntegral(right, left, step);
                task.setReady(false);

            }

            System.out.println("Result for source[" + i + "]: <left:" + left + "> <right:" + right + "> <step:" + step + ">" + " REZULT: " + result);
            task.getSem().release();
        }
    }
}
