package com.capgemini.kabanos.dynamicPseudoCucumber.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.capgemini.kabanos.dynamicPseudoCucumber.annotations.Step;
import com.capgemini.kabanos.dynamicPseudoCucumber.exceptions.DuplicateMethodException;

public class StepValidator {

	private static String logDivider = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";

	public static void validateUniquenessOfMethods(Set<Method> methods, List<String> stepList) {
		Map<String, List<Method>> methodLocationMap = new HashMap<>();

		String errorMessage = "";

		methods.stream().forEach(el -> {
			String annotationValue = getStepValue(el);
			if (!methodLocationMap.containsKey(annotationValue)) {
				methodLocationMap.put(annotationValue, new ArrayList<>());
			}
			methodLocationMap.get(annotationValue).add(el);
		});

		List<List<Method>> duplicates = methodLocationMap.values().stream().filter(el -> el.size() > 1)
				.collect(Collectors.toList());

		if (duplicates.size() > 0) {
			errorMessage += generateNotUniequeMethodsErrorMessage(duplicates);
		}

		Map<String, List<String>> duplicateRegexMatchneMethodMap = new HashMap<>();
		stepList.stream().forEach(step -> {
			List<String> matchingMethods = methods.stream().map(met -> getStepValue(met))
					.filter(val -> step.matches("^" + val + "$")).collect(Collectors.toList());

			if (matchingMethods.size() > 1)
				duplicateRegexMatchneMethodMap.put(step, matchingMethods);
		});

		if (!duplicateRegexMatchneMethodMap.isEmpty())
			errorMessage += generateNotUniequeMatchRegexErrorMessage(duplicateRegexMatchneMethodMap, methodLocationMap);

		if (!errorMessage.equals("")) {
			throw new DuplicateMethodException(errorMessage);
		}
	}

	private static String generateNotUniequeMatchRegexErrorMessage(
			Map<String, List<String>> duplicateRegexMatchneMethodMap, Map<String, List<Method>> methodLocationMap) {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\n" + logDivider + "\n\n");
		sb.append("Multiple regex match for given Steps:\n");

		duplicateRegexMatchneMethodMap.keySet().stream().forEach(step -> {
			sb.append("Step: " + step).append("\n");

			duplicateRegexMatchneMethodMap.get(step).stream().distinct().forEach(duplicate -> {
				methodLocationMap.get(duplicate).forEach(method -> {
					sb.append("\tClass name:       " + method.getDeclaringClass()).append("\n");
					sb.append("\tFunction value:   " + method.getName()).append("\n");
					sb.append("\tStep value:   " + method.getAnnotation(Step.class).value()).append("\n\n");
				});
			});
		});

		sb.append(logDivider);

		return sb.toString();
	}

	private static String getStepValue(Method method) {
		return method.getAnnotation(Step.class).value();
	}

	private static String generateNotUniequeMethodsErrorMessage(List<List<Method>> duplicates) {
		StringBuilder sb = new StringBuilder();

		sb.append("\n\n" + logDivider + "\n\n");
		sb.append("Unable to execute tests because of duplicate Step names").append("\n");

		duplicates.forEach(duplicateMethods -> {
			sb.append("\nDuplicate Step key: " + duplicateMethods.get(0).getAnnotation(Step.class).value())
					.append("\n");

			duplicateMethods.forEach(m -> {
				sb.append("\tClass name:       " + m.getDeclaringClass()).append("\n");
				sb.append("\tFunction value:   " + m.getName()).append("\n\n");
			});
		});

		sb.append("NOTE").append("\n")
				.append("This step doen not detect regular expresions that detect equal strings\n")
				.append("Uniqueness of regex is checked only if a Step present on executon list\n")
				.append("matches two or more step implementations").append("\n\n");

		sb.append(logDivider);
		return sb.toString();
	}
}
