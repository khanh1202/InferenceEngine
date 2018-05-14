package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KnowledgeBase class contains all information the agent knows about the world, and converts statements into logical clauses
 */
public class KnowledgeBase {
    private String[] statements; //an array of unparsed statements from the main function
    private List<Clause> clauses = new ArrayList<>(); //instantiated clauses
    private Map<String, SingleClause> singleclauses = new HashMap<>(); //list of single clauses to keep track of number of symbols

    public KnowledgeBase(String[] statements)
    {
        this.statements = statements;
        parseStatements();
    }

    /**
     * Convert string statements into meaningful clauses
     */
    public void parseStatements()
    {
        for (String statement: statements)
        {
            if (!statement.contains("=>")) //if the clause contains a single proposional symbol
            {
                if (singleclauses.containsKey(statement)) //if the single clause symbol has existed in the list of single clauses
                    clauses.add(singleclauses.get(statement));
                else
                {
                    SingleClause newSingleClause = new SingleClause(statement);
                    clauses.add(newSingleClause);
                    singleclauses.put(newSingleClause.getSymbol(), newSingleClause);
                }
            }
            else
            {
                String[] partsOfImplication = statement.split("=>");
                String[] partsOfConjuction = partsOfImplication[0].split("&");
                List<SingleClause> conjunctionClauses = new ArrayList<>();
                for (String partOfConjunction: partsOfConjuction)
                {
                    if (singleclauses.containsKey(partOfConjunction))
                        conjunctionClauses.add(singleclauses.get(partOfConjunction));
                    else
                    {
                        SingleClause newSingleClause = new SingleClause(partOfConjunction);
                        conjunctionClauses.add(newSingleClause);
                        singleclauses.put(newSingleClause.getSymbol(), newSingleClause);
                    }
                }
                ANDClause leftImplication = new ANDClause(conjunctionClauses);
                if (singleclauses.containsKey(partsOfImplication[1]))
                    clauses.add(new ImplicationClause(leftImplication, singleclauses.get(partsOfImplication[1])));
                else
                {
                    SingleClause rightImplication = new SingleClause(partsOfImplication[1]);
                    clauses.add(new ImplicationClause(leftImplication, rightImplication));
                    singleclauses.put(rightImplication.getSymbol(), rightImplication);
                }
            }
        }
    }

    public Map<String, SingleClause> getSingleclauses() {
        return singleclauses;
    }

    public void setEvaluation(String symbol, boolean value)
    {
        singleclauses.get(symbol).setEvaluation(value);
    }

    public List<Clause> getClauses() {
        return clauses;
    }
}
