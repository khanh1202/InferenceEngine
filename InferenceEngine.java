package com.company;

public class InferenceEngine {
    private Method chosenAlgorithm;

    public InferenceEngine(String algorithm, KnowledgeBase kB, String query)
    {
        if (algorithm.equals("TT"))
            chosenAlgorithm = new TruthTable(kB, query);
        else if (algorithm.equals("FC"))
            chosenAlgorithm = new ForwardChaining(kB, query);
        else if (algorithm.equals("BC"))
            chosenAlgorithm = new BackwardChaining(kB, query);
    }

    public void solve()
    {
        chosenAlgorithm.solve();
    }
}
