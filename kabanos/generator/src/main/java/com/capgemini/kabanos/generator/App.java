package com.capgemini.kabanos.generator;

import java.io.IOException;

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
        
        
        new Generator().generateTemplate("KAB-1", SourceType.JIRA);
    }
}
