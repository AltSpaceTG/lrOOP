package com.company.functions;

import java.io.Serializable;

import static java.lang.Double.NaN;

public class LinkedListTabulatedFunction implements TabulatedFunction, Serializable {

    private FunctionNode head;
    private int size;

    private int lastUsedIndex;
    private FunctionNode lastUsedNode;


    private class FunctionNode {
        FunctionPoint point;
        FunctionNode prev, next;

        FunctionNode() {
            point = null; prev = null; next = null;
        }
        FunctionNode(FunctionPoint p) {
            this.point = p;
            this.prev = null;
            next = null;
        }
        FunctionNode(FunctionPoint p, FunctionNode prev) {
            this.point = p;
            this.prev = prev;
            this.next = null;
        }
        FunctionNode(FunctionPoint p, FunctionNode prev, FunctionNode next) {
            this.point = p;
            this.prev = prev;
            this.next = next;
        }
    }

    public double getLeftDomainBorder() {
        return (this.size == 0) ? NaN : head.next.point.getX();
    }

    public double getRightDomainBorder() {
        if (this.size == 0) return NaN;
        FunctionNode temp = lastUsedNode;
        while (temp.next != null) {
            temp = temp.next;
            ++lastUsedIndex;
        }
        return temp.point.getX();
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, int pointCount) {
        if((pointCount < 2) || (rightX <= leftX)) throw new IllegalArgumentException();
        size = pointCount;
        head = new FunctionNode();
        FunctionNode prev = head;
        for(int i = 0;i<pointCount;++i) {
            FunctionPoint functionPoint = new FunctionPoint(leftX + ((rightX-leftX)/(pointCount-1))*i,0);
            FunctionNode temp = new FunctionNode(functionPoint,prev);
            prev.next = temp;
            prev = temp;
        }
        lastUsedIndex = pointCount-1;
        lastUsedNode = prev;
    }

    public LinkedListTabulatedFunction(double leftX, double rightX, double values[]) {
        if((values.length < 2) || (rightX <= leftX)) throw new IllegalArgumentException();
        head = new FunctionNode();
        int pointCount = values.length;
        size = pointCount;
        FunctionNode prev = head;
        for(int i = 0;i<pointCount;++i) {
            FunctionPoint functionPoint = new FunctionPoint(leftX + ((rightX-leftX)/(pointCount-1))*i,values[i]);
            FunctionNode temp = new FunctionNode(functionPoint,prev);
            prev.next = temp;
            prev = temp;
        }
        lastUsedIndex = pointCount-1;
        lastUsedNode = prev;
    }

    public double getFunctionValue(double x) {
        if(x<getLeftDomainBorder() || x>getRightDomainBorder()) return NaN;
        FunctionNode temp = head.next;
        while (temp != null) {
            if(x==temp.point.getX()) return  temp.point.getY();
            if(x<temp.point.getX()) {

                double x1 = temp.prev.point.getX(), x2 = temp.point.getX(), y1,y2;
                y1 = temp.prev.point.getY(); y2 = temp.point.getY();

                return  (((x-x1)/(x2-x1))*(y2-y1) + y1);
            }
            temp = temp.next;
        }
        return NaN;
    }


    public int getPointsCount() {
        return this.size;
    }

    public FunctionPoint getPoint(int index) {
        return getNodeByIndex(index).point;
    }

    public FunctionNode getNodeByIndex(int index) {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
        if(lastUsedIndex==index) return lastUsedNode;
        if(lastUsedIndex>index) {
            for(int i = 0;i< (lastUsedIndex-index);++i) {
                lastUsedNode = lastUsedNode.prev;
            }
        } else if(lastUsedIndex<index) {
            for(int i = 0;i< (index-lastUsedIndex);++i) {
                lastUsedNode = lastUsedNode.next;
            }
        }
        lastUsedIndex = index;
        return lastUsedNode;
    }

    public void setPoint(int index, FunctionPoint point) throws InappropriateFunctionPointException {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
        if      (((index == 0) ? false : (this.getPoint(index+1).getX()<=point.getX()))
                ||
                ((index == (size-1)) ? false : (this.getPoint(index-1).getX()>=point.getX()))) throw new InappropriateFunctionPointException();
        this.getPoint(index).setX(point.getX());
        this.getPoint(index).setY(point.getY());
    }

    public double getPointX(int index) {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
        return this.getPoint(index).getX();
    }

    public void setPointX(int index, double x) throws InappropriateFunctionPointException {
        if((index < 0) || (index >= size) || (x>this.getRightDomainBorder() || x<this.getLeftDomainBorder())) throw new FunctionPointIndexOutOfBoundsExaption();
        if      (((index == 0) ? false : (this.getPoint(index+1).getX()<=x))
                ||
                ((index == (size-1)) ? false : (this.getPoint(index-1).getX()>=x))) throw new InappropriateFunctionPointException();
        this.getPoint(index).setX(x);
    }

    public double getPointY(int index) {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
        return this.getPoint(index).getY();
    }

    public void setPointY(int index, double y) {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
        this.getPoint(index).setY(y);
    }

    public void deletePoint(int index) {
        if((index < 0) || (index >= size)) throw new FunctionPointIndexOutOfBoundsExaption();
        if(size <= 3) throw new IllegalStateException();
        FunctionNode temp = getNodeByIndex(index);

        if(index == 0) {
            head.next = temp.next;
            temp.prev = head;
        }
        if(index == size-1) {
            temp.prev.next = null;
            --this.size;
            return;
        }
        FunctionNode prevs = temp.prev;
        prevs.next = temp.next;
        temp.next.prev = prevs;
        lastUsedNode = head.next;
        lastUsedIndex = 0;
        --this.size;
    }

    public void addPoint(FunctionPoint point) throws InappropriateFunctionPointException {
        if(this.isPointExists(point.getX())) throw new InappropriateFunctionPointException();
        FunctionNode temp = head.next;
        for(int index = 0;index<this.size;++index) {
            if (point.getX() < temp.point.getX()) {
                FunctionNode inserted = new FunctionNode(point, temp.prev, temp);
                temp.prev.next = inserted;
                temp.prev = inserted;
                ++size;
                lastUsedNode = head.next;
                lastUsedIndex = 0;
                return;
            }
            if (temp.next == null) {
                FunctionNode inserted = new FunctionNode(point, temp, null);
                temp.next = inserted;
                lastUsedNode = head.next;
                lastUsedIndex = 0;
                ++size;
                return;
            }
            temp = temp.next;
        }
    }

    public boolean isPointExists(double x) {
        for(int index = 0;index<this.size;++index) {
            if(this.getPoint(index).getX() == x) return true;
        }
        return false;
    }

    public void print () {
        FunctionNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
            System.out.println("(" + temp.point.getX() + ";" + temp.point.getY() + ")");
        }
    }

    private FunctionNode addNodeToTail() {
        FunctionNode node = getNodeByIndex(size-1);
        FunctionNode temp = new FunctionNode(new FunctionPoint(),node);
        node.next = node;
        ++this.size;
        return temp;
    }

    private FunctionNode addNodeByIndex(int index) {
        FunctionNode temp = head.next;
        for(int i = 0;i<index;++i) {
            temp = temp.next;
        }
        ++this.size;
        FunctionNode inserted = new FunctionNode(new FunctionPoint(), temp.prev, temp.next);
        temp.prev.next = inserted;
        temp.next.prev = inserted;

        return inserted;
    }

    private FunctionNode deleteNodeByIndex(int index) {
        FunctionNode temp = head.next;
        for(int i = 0;i<index;++i) {
            temp = temp.next;
        }
        --this.size;
        temp.prev.next = temp.next;
        temp.next.prev = temp.prev;
        return temp;
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
        if (!(o instanceof LinkedListTabulatedFunction)) return false;
        boolean result = true;
        for (int i=0;i<size; ++i) {
            result = (result && (this.getPoint(i).equals(((LinkedListTabulatedFunction) o).getPoint(i))));
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

}
