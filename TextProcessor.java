package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles text file reading, text instantiation
 */
public class TextProcessor {
    private static String[] _kb;
    private static String _query;
    /**
     * Remove unnecessary white space in a string
     * @param input the string input
     * @return a String with no white space
     */
    public static String removeWhiteSpace(String input)
    {
        String[] parts = input.split(" ");
        String result = "";
        for (String part: parts)
        {
            result += part;
        }
        return result;
    }

    /**
     * Read the input file, assign _kb and _query
     * @param filename filename
     */
    public static void exportKBAndQuery(String filename)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String currentLine;
            String previousLine = "";
            while ((currentLine = br.readLine()) != null)
            {
                if (previousLine.equals("TELL"))
                {
                    currentLine = removeWhiteSpace(currentLine);
                    _kb = currentLine.split(";");
                }
                else if (previousLine.equals("ASK"))
                {
                    _query = currentLine;
                }
                previousLine = currentLine;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String[] get_kb() {
        return _kb;
    }

    public static String get_query() {
        return _query;
    }
}
