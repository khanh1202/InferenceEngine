package com.company;

import java.util.*;

/**
 * Implement Forward Chaining algorithm
 */
public class ForwardChaining extends Method {
    private Map<Clause, Integer> count = new HashMap<>(); //a table indexed by clause, indicates the number of premises
    private Map<SingleClause, Boolean> inferred = new HashMap<>(); //a table indexed by symbol, each entry initially false
    private Queue<SingleClause> agenda = new ArrayDeque<>(); //a list of symbols known to be true

    public ForwardChaining(KnowledgeBase kB, String query)
    {
        super(kB, query);
        SingleClause[] singleClauses = getKB().getSingleclauses().values().toArray(new SingleClause[getKB().getSingleclauses().entrySet().size()]);
        setupChaining(singleClauses);
    }

    /**
     * Add values to the private fields
     * @param singleClauses the list of all proposional symbols in the system
     */
    public void setupChaining(SingleClause[] singleClauses)
    {
        for (SingleClause clause: singleClauses)
        {
            inferred.put(clause, false);
        }

        for (Clause clause: getKB().getClauses())
        {
            if (clause instanceof SingleClause)
            {
                agenda.add((SingleClause)clause);
            }
            else
            {
                count.put(clause, ((ImplicationClause) clause).getCl1().getNumOfPremises());
            }
        }
    }

    @Override
    public void solve() {
        while (!agenda.isEmpty())
        {
            SingleClause trueSymbol = agenda.poll();
            if (!inferred.get(trueSymbol))
            {
                inferred.put(trueSymbol, true);
                for (Clause clause: getKB().getClauses())
                {
                    if ((clause instanceof ImplicationClause))
                    {
                        if (((ImplicationClause)clause).getCl1().containsClause(trueSymbol))
                        {
                            count.put(clause, count.get(clause) - 1);
                            int countRemaining = count.get(clause);
                            if (countRemaining == 0)
                            {
                                if (((ImplicationClause) clause).getCl2().getSymbol().equals(getQuery()))
                                {
                                    inferred.put(((ImplicationClause) clause).getCl2(), true);
                                    printResult(inferred);
                                    return;
                                }
                                else
                                {
                                    agenda.add(((ImplicationClause) clause).getCl2());
                                }
                            }
                        }
                    }
                }
            }
        }
        printResult(null);
    }

    public void printResult(Map<SingleClause, Boolean> inferred)
    {
        if (inferred == null)
            System.out.println("No");
        else
        {
            System.out.print("YES: ");
            for (Map.Entry<SingleClause, Boolean> entry: inferred.entrySet())
            {
                if (entry.getValue())
                    System.out.print(entry.getKey().getSymbol() + " ");
            }
        }
    }
}
