package com.company;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BackwardChaining extends Method {
    private SingleClause queryClause;
    private Stack<ANDClause> frontier = new Stack<>();
    private List<SingleClause> visited = new ArrayList<>();

    public BackwardChaining(KnowledgeBase kB, String query)
    {
        super(kB, query);
        queryClause = getKB().getSingleclauses().get(query);
        setupChaining();
    }

    public void setupChaining()
    {
        for (Clause clause: getKB().getClauses())
        {
            if (clause instanceof SingleClause)
            {
                ((SingleClause) clause).setEvaluation(true);
                visited.add((SingleClause) clause);
            }
        }
    }

    @Override
    public void solve() {
        List<ANDClause> firstClause = symbolsToImply(queryClause);
        frontier.addAll(firstClause);
        visited.add(queryClause);
        while (!frontier.isEmpty())
        {
            ANDClause currentClause = frontier.pop();
            if (currentClause.evaluate())
            {
                printResult(visited);
                return;
            }
            for (SingleClause nestedClause:currentClause.getClauses())
            {
                if (!nestedClause.evaluate())
                {
                    List<ANDClause> nextChain = symbolsToImply(nestedClause);
                    if (nextChain != null && !visited.contains(nestedClause))
                    {
                        visited.add(nestedClause);
                        addNewClauseToFrontier(currentClause, nextChain, nestedClause);
                        break;
                    }
                    else
                        break;
                }
            }
        }
        System.out.print("NO");
    }

    public void addNewClauseToFrontier(ANDClause currentClause, List<ANDClause> clausesToAdd, SingleClause clauseToRemove)
    {
        currentClause.removeClause(clauseToRemove);
        for (ANDClause clauseToAdd: clausesToAdd)
        {
            ANDClause newANDClause = currentClause.replica();
            newANDClause.addAll(clauseToAdd);
            frontier.push(newANDClause);
        }
    }

    public void printResult(List<SingleClause> clauses)
    {
        System.out.print("YES: ");
        for (SingleClause clause: clauses)
            System.out.print(clause.getSymbol() + " ");
    }

    public List<ANDClause> symbolsToImply(SingleClause sc)
    {
        List<ANDClause> result = new ArrayList<>();

        for (Clause clause: getKB().getClauses())
        {
            if (clause instanceof ImplicationClause && ((ImplicationClause) clause).getCl2() == sc)
                result.add(((ImplicationClause) clause).getCl1());
        }

        if (result.size() != 0)
            return result;
        return null;
    }
}
