package com.capgemini.kabanos.dynamicPseudoCucumber;

import java.util.Arrays;
import java.util.Map;

import com.capgemini.kabanos.dynamicPseudoCucumber.domain.StepExecutionResult;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		
		String[] d = {"Login as JohnDoe and type 123.45", "Login as JaneDoe and type 122.44"};
		
//		String[] d = { "Go to www.facebook.com", "Login as JohnDoe", "Open messages and count not readed",
//				"Click on first not readed message and count not readed messages", "Go back to main facebook page",
//				"Log out from facebook", "Open messages", "Create a new message to JaneDoe",
//				"write a very long text message a send it to the recipient", "Count total message count",
//				"Delete first messagem and count total messages" };

		Map<String, StepExecutionResult> report = new TestExecutor().generateExecuteionReport(Arrays.asList(d));

		for (String g : Arrays.asList(d)) {

			System.out.println(g);

			System.out.println("\t" + report.get(g).getState());
			System.out.println("\t\t\t\t\t" + report.get(g).getMessage());

			System.out.println(" ");
		}

		System.out.println("??");
	}
}