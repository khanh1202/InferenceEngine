package com.company;

import java.util.ArrayList;
import java.util.List;

public class ANDClause implements Clause {
    private List<SingleClause> clauses = new ArrayList<>();

    public ANDClause(List<SingleClause> clauses)
    {
        for (SingleClause clause: clauses)
            this.clauses.add(clause);
    }

    @Override
    public boolean evaluate() {
        for (SingleClause clause: getClauses())
        {
            if (!clause.evaluate())
                return false;
        }
        return true;
    }

    public int getNumOfPremises()
    {
        return getClauses().size();
    }

    public boolean containsClause(SingleClause clause)
    {
        return clauses.contains(clause);
    }

    public void addSingleClause(SingleClause clause)
    {
        clauses.add(0, clause);
    }

    public List<SingleClause> getClauses() {
        return clauses;
    }

    public void print()
    {
        for (SingleClause clause: clauses)
            System.out.print(clause.getSymbol() + " ");
        System.out.println();
    }

    public ANDClause replica()
    {
        return new ANDClause(clauses);
    }

    public void removeClause(SingleClause sc)
    {
        clauses.remove(sc);
    }

    public void addAll(ANDClause ands)
    {
        for (SingleClause and: ands.getClauses())
            clauses.add(0, and);
    }

}
