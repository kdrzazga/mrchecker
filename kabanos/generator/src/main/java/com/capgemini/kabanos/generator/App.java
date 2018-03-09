package com.capgemini.kabanos.generator;

import java.util.Properties;

import com.capgemini.kabanos.common.enums.ApplicationConfigType;
import com.capgemini.kabanos.common.enums.SourceType;
import com.capgemini.kabanos.common.utility.PropertiesUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	Properties properties = PropertiesUtils.loadProperties(ApplicationConfigType.GENERATOR);
    	
        System.out.println( "Hello World!" );

        String template = new Generator(properties).generateTemplate("KAB-1", SourceType.JIRA);

        System.out.println(template);
    }
}
