package com.capgemini.kabanos.generator;

import com.capgemini.kabanos.common.enums.SourceType;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );

        String template = new Generator().generateTemplate("KAB-1", SourceType.JIRA);

        System.out.println(template);
    }
}
