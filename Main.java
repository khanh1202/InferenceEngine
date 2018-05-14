package com.company;

public class Main {

    public static void main(String[] args) {
	    // write your code here
        if (args.length < 2)
            System.out.println("Insufficient input");
        else
        {
            TextProcessor.exportKBAndQuery(args[1]);
            KnowledgeBase base = new KnowledgeBase(TextProcessor.get_kb());
            InferenceEngine engine = new InferenceEngine(args[0], base, TextProcessor.get_query());
            engine.solve();
        }
    }
}
