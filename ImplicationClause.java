package com.company;

public class ImplicationClause implements Clause {
    private ANDClause cl1;
    private SingleClause cl2;


    public ImplicationClause(ANDClause cl1, SingleClause cl2) {
        this.cl1 = cl1;
        this.cl2 = cl2;
    }

    @Override
    public boolean evaluate() {
        return ((!getCl1().evaluate()) || (getCl2().evaluate()));
    }

    public ANDClause getCl1() {
        return cl1;
    }

    public SingleClause getCl2() {
        return cl2;
    }
}
