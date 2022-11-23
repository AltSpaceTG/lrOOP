package com.company;
import com.company.functions.*;
import com.company.functions.basic.Cos;
import com.company.functions.basic.Sin;

import java.io.*;

public class Main {

    public static void main(String[] args) {

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
}
