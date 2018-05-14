package com.company;

public class TruthTable extends Method {
    private boolean table[][];
    private String[] symbols;
    private int numOfModels;

    public TruthTable(KnowledgeBase kB, String query)
    {
        super(kB, query);
        symbols = getKB().getSingleclauses().keySet().toArray(new String[getKB().getSingleclauses().keySet().size()]);
        initTable();
    }

    public void initTable()
    {
        //number of symbols is equal to 2 to the power of number of symbols
        numOfModels = (int)Math.pow(2, symbols.length);
        table = new boolean[numOfModels][symbols.length];

        for (int i = 0; i < numOfModels; i++)
        {
            //convert row number to a string of binary value
            String rowBinary = Integer.toBinaryString(i);
            while (rowBinary.length() < symbols.length)
                rowBinary = '0' + rowBinary; //0 padding to have a binary string whose length equal to number of symbols

            for (int j = 0; j < getKB().getSingleclauses().size(); j++)
            {
                if (rowBinary.charAt(j) == '1')
                    table[i][j] = false;
                else
                    table[i][j] = true;
            }

        }
    }

    @Override
    public void solve()
    {
        int result = 0;
        for (int i = 0; i < numOfModels; i++)
        {
            boolean alltrue = true;
            for (int j = 0; j < symbols.length; j++)
            {
                getKB().setEvaluation(symbols[j], table[i][j]);
            }

            for (Clause clause: getKB().getClauses())
            {
                if (!clause.evaluate())
                {
                    alltrue = false;
                    break;
                }
            }

            if (alltrue && getKB().getSingleclauses().get(getQuery()).evaluate())
                result++;
        }
        if (result != 0)
            System.out.println("YES: " + result);
        else
            System.out.println("NO");
    }
}
