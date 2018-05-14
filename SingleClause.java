package com.company;

public class SingleClause implements Clause {
    private String symbol;
    private boolean evaluation;

    public SingleClause(String symbol)
    {
        this.symbol = symbol;
    }

    @Override
    public boolean evaluate() {
        return evaluation;
    }

    public String getSymbol() {
        return symbol;
    }


    public void setEvaluation(boolean evaluation) {
        this.evaluation = evaluation;
    }
}
