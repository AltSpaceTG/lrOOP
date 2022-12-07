package com.company;
import com.company.functions.*;
import com.company.functions.basic.Cos;
import com.company.functions.basic.Log;
import com.company.functions.basic.Sin;
import com.company.threads.SimpleGenerator;
import com.company.threads.SimpleIntegrator;
import com.company.threads.Task;
import org.apache.tomcat.jni.Time;
import sun.java2d.loops.GraphicsPrimitive;

import java.io.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {

        //nonThread();

        simpleThreads();



        TabulatedFunction func = TabulatedFunctions.createTabulatedFunction("ArrayTabulatedFunction", 1, 5, new double[]{1, 2, 3, 4, 5});



        /*
        double[] array = {1,2,3,4,5};

        TabulatedFunction temp = new LinkedListTabulatedFunction(1, 5, array);
        temp.deletePoint(3);
        temp.deletePoint(3);
        temp.print();
        temp.addPoint(new FunctionPoint(4,8));
        temp.addPoint(new FunctionPoint(3.5, 7));
        System.out.println(temp.getFunctionValue(2.6));
        temp.print();
        System.out.println();
         */

        /*
        TabulatedFunction temp = new ArrayTabulatedFunction(), tabSin = TabulatedFunctions.tabulate(new Sin(), 0, Math.PI, 64);
        ArrayTabulatedFunction tabCos = TabulatedFunctions.tabulate(new Cos(), 0, Math.PI, 64);

        System.out.println(tabCos);

        try {
            FileOutputStream out = null;
            out = new FileOutputStream("D:\\LABS\\text.txt");
            TabulatedFunctions.outputTabulatedFunction(tabSin, out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */


        /*
        try {
            FileInputStream in = new FileInputStream("D:\\LABS\\text.txt");
            temp = TabulatedFunctions.inputTabulatedFunction(in);
            in.close();
        } catch (InappropriateFunctionPointException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         */



        /*
        try {
            System.out.println("Creating...");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            System.out.println("Serializing...");
            oos.writeObject(TabulatedFunctions.tabulate(new Log(),0,10,11));
            oos.flush();
            baos.flush();
            oos.close();
            baos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            System.out.println("Deserializing...");
            TabulatedFunction func = (TabulatedFunction) ois.readObject();
            func.print();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
         */
    }

    public static void nonThread() {
        Random r = new Random();
        Semaphore sem = new Semaphore(0);
        Task task = new Task(new Log(), 100 * r.nextDouble(), 100 + 100*r.nextDouble(), r.nextDouble(),100, sem);

        System.out.println("Source: <left:" + task.left + "> <right:" + task.right + "> <step:" + task.step + ">" );
        System.out.println("Rezult: " + task.func.calculateIntegral(task.right,task.left,task.step));
    }

    public static void simpleThreads() {
        Random r = new Random();
        Semaphore sem = new Semaphore(1);
        Task task = new Task(new Log(), 100 * r.nextDouble(), 100 + 100*r.nextDouble(), r.nextDouble(),100, sem);

        Thread generator = new Thread(new SimpleGenerator(task));
        Thread integrator = new Thread(new SimpleIntegrator(task));

        generator.start();
        integrator.start();
    }
}
