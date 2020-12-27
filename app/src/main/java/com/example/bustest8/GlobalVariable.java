package com.example.bustest8;

import android.app.Application;

import java.util.ArrayList;

public class GlobalVariable extends Application {
    private double Lat1, Lng1, Lat2, Lng2;
    private String q[] = null;
    private String BS1[] = new String[5];
    private String BS2[] = new String[5];
    private double D1[] = new double[10];
    private double D2[] = new double[10];
    private String TF[][] = new String[10][2];

    public void setBS1(String[] BS1) {
        this.BS1 = BS1;
    }
    public void setBS2(String[] BS2) { this.BS2 = BS2; }
    public void setTF(String[][] TF) {
        this.TF = TF;
    }
    public void setD1(double[] d1) {
        D1 = d1;
    }
    public void setD2(double[] d2) {
        D2 = d2;
    }

    public String[] getBS1() {
        return BS1;
    }
    public String[] getBS2() { return BS2; }

    public String[][] getTF() {
        return TF;
    }

    public double[] getD1() {
        return D1;
    }
    public double[] getD2() {
        return D2;
    }

    public void setQ(String[] q) { this.q = q; }
    public String[] getQ() {
        return q;
    }
}

