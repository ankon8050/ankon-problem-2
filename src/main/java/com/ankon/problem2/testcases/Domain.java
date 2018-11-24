package com.ankon.problem2.testcases;

public class Domain {

    private int b;
    private int c;

    public Domain() {
    }

    public Domain(int b) {
        this.b = b;
    }

    public Domain(int b, int c) {
        this.b = b;
        this.c = c;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "b=" + b +
                ", c=" + c +
                '}';
    }
}
