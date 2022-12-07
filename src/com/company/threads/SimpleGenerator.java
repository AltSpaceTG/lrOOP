package com.company.threads;

import com.company.functions.basic.Log;

import java.util.Random;

public class SimpleGenerator implements Runnable {

    final Task task;

    public SimpleGenerator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        double left, right, step;
        Random r = new Random();
        for(int i = 0; i<task.getItersCount();++i) {
            try {
                task.getSem().acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            left = 100 * r.nextDouble();
            right = 100 + 100 * r.nextDouble();
            step = r.nextDouble();

            synchronized (task) {
                task.setFunc(new Log());
                task.setLeft(left);
                task.setRight(right);
                task.setStep(step);
                task.setReady(true);
            }
            System.out.println("Source[" + i + "]: <left:" + left + "> <right:" + right + "> <step:" + step + ">" );

        }
    }
}
