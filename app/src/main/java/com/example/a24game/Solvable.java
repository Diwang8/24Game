package com.example.a24game;

public class Solvable {
    private int a;
    private int b;
    private int c;
    private int d;
    private int[] solutionOpID;
    private String solution;

    public Solvable(int first, int second, int third, int fourth) {
        a = first;
        b = second;
        c = third;
        d = fourth;
        solutionOpID = new int[7];
    }

    public boolean equalTo(double x, double y) {
        return Math.abs(x - y) < 0.000001;
    }

    private double operations(double x, double y, int opID) {
        if (opID == 0) {
            return x + y;
        } else if (opID == 1) {
            return x - y;
        } else if (opID == 2) {
            return y - x;
        } else if (opID == 3) {
            return x * y;
        } else if (opID == 4 && !(equalTo(y, 0))) {
            return x / y;
        } else if (opID == 5 && !(equalTo(x, 0))) {
            return y / x;
        } else {
            return 999999;
        }
    }

    public boolean isSolvable() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 0; k < 6; k++) {
                    double o, p, q;
                    o = operations(a, b, i);
                    p = operations(o, c, j);
                    q = operations(p, d, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = a;
                        solutionOpID[1] = b;
                        solutionOpID[2] = c;
                        solutionOpID[3] = d;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                    o = operations(a, b, i);
                    p = operations(o, d, j);
                    q = operations(p, c, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = a;
                        solutionOpID[1] = b;
                        solutionOpID[2] = d;
                        solutionOpID[3] = c;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                    o = operations(a, c, i);
                    p = operations(o, b, j);
                    q = operations(p, d, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = a;
                        solutionOpID[1] = c;
                        solutionOpID[2] = b;
                        solutionOpID[3] = d;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                    o = operations(a, c, i);
                    p = operations(o, d, j);
                    q = operations(p, b, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = a;
                        solutionOpID[1] = c;
                        solutionOpID[2] = d;
                        solutionOpID[3] = b;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                    o = operations(a, d, i);
                    p = operations(o, b, j);
                    q = operations(p, c, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = a;
                        solutionOpID[1] = d;
                        solutionOpID[2] = b;
                        solutionOpID[3] = c;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                    o = operations(a, d, i);
                    p = operations(o, c, j);
                    q = operations(p, b, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = a;
                        solutionOpID[1] = d;
                        solutionOpID[2] = c;
                        solutionOpID[3] = b;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                    o = operations(b, c, i);
                    p = operations(o, a, j);
                    q = operations(p, d, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = b;
                        solutionOpID[1] = c;
                        solutionOpID[2] = a;
                        solutionOpID[3] = d;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                    o = operations(b, c, i);
                    p = operations(o, d, j);
                    q = operations(p, a, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = b;
                        solutionOpID[1] = c;
                        solutionOpID[2] = d;
                        solutionOpID[3] = a;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                    o = operations(b, d, i);
                    p = operations(o, a, j);
                    q = operations(p, c, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = b;
                        solutionOpID[1] = d;
                        solutionOpID[2] = a;
                        solutionOpID[3] = c;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                    o = operations(b, d, i);
                    p = operations(o, c, j);
                    q = operations(p, a, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = b;
                        solutionOpID[1] = d;
                        solutionOpID[2] = c;
                        solutionOpID[3] = a;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                    o = operations(c, d, i);
                    p = operations(o, a, j);
                    q = operations(p, b, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = c;
                        solutionOpID[1] = d;
                        solutionOpID[2] = a;
                        solutionOpID[3] = b;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                    o = operations(c, d, i);
                    p = operations(o, b, j);
                    q = operations(p, a, k);
                    if (equalTo(q, 24)) {
                        solutionOpID[0] = c;
                        solutionOpID[1] = d;
                        solutionOpID[2] = b;
                        solutionOpID[3] = a;
                        solutionOpID[4] = i;
                        solutionOpID[5] = j;
                        solutionOpID[6] = k;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String combine(int m, int n, int opID) {
        String combined = "";
        if (opID == 0) {
            combined = "(" + m + "+" + n + ")";
        }
        if (opID == 1) {
            combined = "(" + m + "-" + n + ")";
        }
        if (opID == 2) {
            combined = "(" + n + "-" + m + ")";
        }
        if (opID == 3) {
            combined = "(" + m + "*" + n + ")";
        }
        if (opID == 4) {
            combined = "(" + m + "/" + n + ")";
        }
        if (opID == 5) {
            combined = "(" + n + "/" + m + ")";
        }
        return combined;
    }

    public String combine(String m, int n, int opID) {
        String combined = "";
        if (opID == 0) {
            combined = "(" + m + "+" + n + ")";
        }
        if (opID == 1) {
            combined = "(" + m + "-" + n + ")";
        }
        if (opID == 2) {
            combined = "(" + n + "-" + m + ")";
        }
        if (opID == 3) {
            combined = "(" + m + "*" + n + ")";
        }
        if (opID == 4) {
            combined = "(" + m + "/" + n + ")";
        }
        if (opID == 5) {
            combined = "(" + n + "/" + m + ")";
        }
        return combined;
    }

    public String generateSolutionAsString() {
        if (!isSolvable()) {
            solution = "no solution";
        } else {
            String partA;
            partA = combine(solutionOpID[0], solutionOpID[1], solutionOpID[4]);
            String partB = combine(partA, solutionOpID[2], solutionOpID[5]);
            solution = combine(partB, solutionOpID[3], solutionOpID[6]);
        }
        return solution;
    }
}
