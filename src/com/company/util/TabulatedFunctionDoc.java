package com.company.util;

import com.company.functions.*;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import sun.plugin2.main.client.WMozillaServiceDelegate;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;

public class TabulatedFunctionDoc implements TabulatedFunction, Serializable {

    private TabulatedFunctionDoc() {};
    private TabulatedFunction func;
    private boolean modified;
    private boolean fileNameAssigned;
    private String fileName;

    public TabulatedFunctionDoc(double leftX, double rightX, int pointsCount) {
        func = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        modified = true;
        fileNameAssigned = false;
    }

    public TabulatedFunctionDoc(ArrayTabulatedFunction function) {
        func = function;
        modified = true;
        fileNameAssigned = false;
    }

    public void newFunction (double leftX, double rightX, int pointsCount) {
        func = new ArrayTabulatedFunction(leftX, rightX, pointsCount);
        modified = true;
    }

    public void tabulateFunction(Function function, double leftX, double rightX, int pointsCount) {
        func = TabulatedFunctions.tabulate(FunctionType.ARRAY,func, leftX, rightX, pointsCount);
        modified = true;
    }

    public void saveFunctionAs(String newFileName) {
        fileNameAssigned = true;
        this.fileName = newFileName;

        JSONObject obj = new JSONObject();
        obj.put("size", func.getPointsCount());

        JSONArray listOfX = new JSONArray();
        for(int i=0;i<func.getPointsCount();++i) {
            listOfX.add(func.getPointX(i));
        }

        JSONArray listOfY = new JSONArray();
        for(int i=0;i<func.getPointsCount();++i) {
            listOfY.add(func.getPointY(i));
        }

        obj.put("X", listOfX);
        obj.put("Y", listOfY);

        modified = false;

        try (FileOutputStream file = new FileOutputStream("C:\\Users\\user\\IdeaProjects\\project\\" + newFileName)) {
            PrintWriter pw = new PrintWriter(file);
            pw.write(obj.toJSONString());
            pw.flush();
            file.close();
        } catch (IOException e) {

        }
    }

    public void saveFunction() {
        if(modified==false) return;
        if(fileNameAssigned == true) {

            JSONObject obj = new JSONObject();
            obj.put("size", func.getPointsCount());

            JSONArray listOfX = new JSONArray();
            for(int i=0;i<func.getPointsCount();++i) {
                listOfX.add(func.getPointX(i));
            }

            JSONArray listOfY = new JSONArray();
            for(int i=0;i<func.getPointsCount();++i) {
                listOfY.add(func.getPointX(i));
            }

            obj.put("X", listOfX);
            obj.put("Y", listOfY);

            modified = false;
            try (FileOutputStream file = new FileOutputStream("C:\\Users\\user\\IdeaProjects\\project\\" + fileName)) {
                PrintWriter pw = new PrintWriter(file);
                pw.write(obj.toJSONString());
                pw.flush();
                file.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("uncheked")
    public void loadFunction() {
        if(!fileNameAssigned) return;
        JSONParser jsonParser = new JSONParser();

        try (Reader reader = new FileReader("C:\\Users\\user\\IdeaProjects\\project\\" + fileName)) {

            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONArray listOfX = (JSONArray) jsonObject.get("X");
            JSONArray listOfY = (JSONArray) jsonObject.get("Y");

            FunctionPoint[] pl = new FunctionPoint[listOfX.size()];

            for(int i=0;i<listOfX.size();++i) {
                pl[i] = new FunctionPoint((Double) listOfX.get(i), (Double) listOfY.get(i));
            }

            func = new ArrayTabulatedFunction(pl);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFunction(String newFileName) {
        if(!fileNameAssigned) return;
        JSONParser jsonParser = new JSONParser();

        try (Reader reader = new FileReader(newFileName)) {

            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            int count = (int) jsonObject.get("size");

            JSONArray listOfX = (JSONArray) jsonObject.get("X");
            JSONArray listOfY = (JSONArray) jsonObject.get("Y");

            FunctionPoint[] pl = new FunctionPoint[count];

            for(int i=0;i<count;++i) {
                pl[i] = new FunctionPoint((Double) listOfX.get(i), (Double) listOfY.get(i));
            }

            func = new ArrayTabulatedFunction(pl);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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
        return func.getFunctionValue(x);
    }

    @Override
    public double calculateIntegral(double a, double b, double step) {
        if (a>this.getRightDomainBorder() || b<this.getLeftDomainBorder() || a<b) throw new IllegalArgumentException();
        double rezult = 0;
        while(a<b) {
            rezult += (this.getFunctionValue(a) + this.getFunctionValue(a+step))*step/2;
            a+=step;
        }
        return rezult;
    }

    @Override
    public int getPointsCount() {
        return func.getPointsCount();
    }

    @Override
    public FunctionPoint getPoint(int index) {
        return func.getPoint(index);
    }

    @Override
    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        func.setPoint(index,point);
        modified = true;
    }

    @Override
    public double getPointX(int index) {
        return func.getPointX(index);
    }

    @Override
    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        func.setPointX(index, x);
        modified = true;
    }

    @Override
    public double getPointY(int index) {
        return func.getPointY(index);
    }

    @Override
    public void setPointY(int index, double y) {
        func.setPointY(index,y);
        modified = true;
    }

    @Override
    public void deletePoint(int index) {
        func.deletePoint(index);
        modified = true;
    }

    @Override
    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        func.addPoint(point);
        modified = true;
    }

    @Override
    public boolean isPointExists(double x) {
        return func.isPointExists(x);
    }

    @Override
    public void print() {
        func.print();
    }

    @NotNull
    @Override
    public Iterator<FunctionPoint> iterator() {
        return func.iterator();
    }
}
