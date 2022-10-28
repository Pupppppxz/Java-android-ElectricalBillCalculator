package com.example.electricitybillcalculator;

public class Electrical {

    public Electrical() {}

    public double calculate(double unit) {
        if (unit <= 150) return this.lessThan150(unit);
        else return this.moreThan150(unit);
    }

    private double lessThan150(double unit) {
        double[] ls = {2.3488, 2.9882, 3.2405, 3.6237, 3.7171, 4.2218, 4.4217};
        if (unit <= 15) {
            return unit * ls[0];
        }
        if (unit <= 25) {
            return unit * ls[1];
        }

        if (unit <= 35) {
            return unit * ls[2];
        }
        if (unit <= 100) {
            return unit * ls[3];
        }
        return unit * ls[4];
    }

    private double moreThan150(double unit) {
        double[] ls = {3.2484, 4.2218, 4.4217};
        if (unit <= 150) {
            return unit * ls[0];
        }
        if (unit <= 400) {
            return unit * ls[1];
        }
        return unit * ls[2];
    }
}
