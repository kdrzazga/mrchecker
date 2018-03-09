package com.capgemini.kabanos.common.utility;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StringUtils {

	public static String[] trimArray(String[] array) {
		for (int x = 0; x < array.length; x++) {
			array[x] = array[x].trim();
		}
		return array;
	}

	public static String repeat(String str, int times) {
		StringBuilder sb = new StringBuilder();
		while (times-- > 0) {
			sb.append(str);
		}
		return sb.toString();
	}

	public static String justifyCode(String[] array) {
		StringBuilder sb = new StringBuilder();
		trimArray(array);

		int depth = 0;
		String tab = "";

		for (String line : array) {
			if (line.contains("}")) {
				depth--;
				tab = repeat("\t", depth);
			}

			sb.append(tab);
			sb.append(line);
			sb.append("\n");

			if (line.contains("{")) {
				depth++;
				tab = repeat("\t", depth);
			}
		}

		return sb.toString();
	}

	public static String justifyCode(List<String> list) {
		return justifyCode(list.toArray(new String[] {}));
	}

	public static boolean isStartsWithOneOf(String str, String[] prefixes) {
		trimArray(prefixes);
		str = str.trim();
		for (String prefix : prefixes) {
			if (!prefix.equals("") && str.startsWith(prefix))
				return true;
		}
		return false;
	}

	public static String formatLoggerStep(String line) {
		return line.toLowerCase().replaceAll(" ", "");//.replaceAll("\"\\+\"", "").replaceAll("\"\\+\\w+\\+\"", "____");
	}
	
	public static String formatMultiLineString(String line) {
		return line.replaceAll("\"\\s*\\+\\s*\"", "");
	}
	
	public static String formatConcatenatedVariablesInString(String line) {
		return line.replaceAll("\"\\s*\\+\\s*\\w+\\s*\\+\\s*\"", "____");
	}

	public static String generateHelpMassege(String lines) {
		return generateHelpMassege(Arrays.asList(lines.split("\n")));
	}
	
	public static String generateHelpMassege(List<String> lines) {

		int maxLength = lines.stream().map(el -> el.replaceAll("\t", "    ").length()).max(Comparator.naturalOrder())
				.get();

		StringBuilder sb = new StringBuilder();

		sb.append(repeat("=", maxLength + 4)).append("\n");

		for (String line : lines) {
			line = line.replaceAll("\t", "    ");
			sb.append("| ").append(line).append(repeat(" ", maxLength - line.length())).append(" |\n");
		}

		sb.append(repeat("=", maxLength + 4));

		return sb.toString();
	}
}
