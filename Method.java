package com.company;

/**
 * Abstract class for inference method
 */
public abstract class Method {
    private KnowledgeBase knowledgeBase; //The knowledge base
    private String query; //the ASK statement

    protected Method(KnowledgeBase knowledgeBase, String query) {
        this.knowledgeBase = knowledgeBase;
        this.query = query;
    }

    public abstract void solve();

    public KnowledgeBase getKB() {
        return knowledgeBase;
    }

    public String getQuery() {
        return query;
    }
}
