package com.ankon.problem2.testcases;

public class Input {
    private int a;
    private int b;

    public Input(int b) {
        this.b = b;
    }

    public Input() {
    }

    public Input(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "Input{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }
}