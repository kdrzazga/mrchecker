package com.capgemini.kabanos.dynamicPseudoCucumber;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import com.capgemini.kabanos.dynamicPseudoCucumber.annotations.Step;
import com.capgemini.kabanos.dynamicPseudoCucumber.domain.StepExecutionResult;
import com.capgemini.kabanos.dynamicPseudoCucumber.enums.StepState;
import com.capgemini.kabanos.dynamicPseudoCucumber.exceptions.DuplicateMethodException;

//TODP regexy i datadriven


public class TestExecutor {

	private static Reflections reflections = new Reflections(
			new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("")).setScanners(new SubTypesScanner(false),
					new TypeAnnotationsScanner(), new MethodAnnotationsScanner()));

	private Map<String, Object> stepClassMap = new HashMap<>();
	
	
	public Map<String, StepExecutionResult> generateExecuteionReport(List<String> stepList) {
		Map<String, Method> methodMap = this.mapMethodsByStep(this.getTestSteps());

		Map<String, StepExecutionResult> executionReport = new HashMap<>();

		StepState prevState = StepState.NULL_OBJECT;
		StepExecutionResult stepReport;

		// if a step failed then further execution is not needed
		// but all further steps have to be marked with a status
		boolean executeSteps = true;

		for (String step : stepList) {
			step = step.trim();
			if (!executeSteps) {
				executionReport.put(step, new StepExecutionResult(methodMap.containsKey(step) ? StepState.NOT_REACHABLE
						: StepState.MISSING_IMPLEMENTATION_AND_NOT_REACHABLE));
			} else {
				if (methodMap.containsKey(step)) {
					stepReport = this.generateStepReport(prevState, methodMap.get(step));
					executionReport.put(step, stepReport);

					if (stepReport.getState() == StepState.FAILURE_AFTER_FAILURE
							|| (prevState == StepState.NULL_OBJECT && stepReport.getState() == StepState.FAILURE)) {
						executeSteps = false;
					} else {
						prevState = stepReport.getState();
					}
				} else {
					executionReport.put(step, new StepExecutionResult(StepState.MISSING_IMPLEMENTATION,
							"No implementation was found fot this step"));

					// if a step is missing then the path testing is terminated
					executeSteps = false;
				}
			}

		}

		return executionReport;
	}

	public Set<Method> getTestSteps() {
		Set<Method> methods = reflections.getMethodsAnnotatedWith(Step.class);

		validateUniquenessOfMethods(methods);

		return methods;
	}

	public Map<String, Method> mapMethodsByStep(Set<Method> methods) {
		Map<String, Method> result = new HashMap<>();

		for (Method m : methods) {
			result.put(m.getAnnotation(Step.class).value().trim(), m);
		}

		return result;
	}

	private StepExecutionResult generateStepReport(StepState previousState, Method m) {

		StepState currentState;
		String message = "";

		try {
			// TODO ogarnac uruchamianie z parametrami
			// tj. testy datadriven
			
			if(!this.stepClassMap.containsKey(m.getDeclaringClass().toString())) {
				this.stepClassMap.put(m.getDeclaringClass().toString(), m.getDeclaringClass().newInstance());
			}
			
			m.invoke(this.stepClassMap.get(m.getDeclaringClass().toString()));//m.getDeclaringClass().newInstance());

			currentState = StepState.SUCCESS;
		} catch (Exception e) {

			// TODO
			// dobrze by bylo gdyby message == to co na stackTrejsie
			// e.printStackTrace();
			message = e.getMessage();

			currentState = StepState.FAILURE;
		}

		if (currentState == StepState.SUCCESS) {
			switch (previousState) {
			case SUCCESS:
			case NULL_OBJECT:
				return new StepExecutionResult(StepState.SUCCESS);

			case FAILURE:
				return new StepExecutionResult(StepState.SUCCESS_AFTER_FAILURE,
						"Step succeded, but previous step did fail");

			case SUCCESS_AFTER_FAILURE:
				return new StepExecutionResult(StepState.SUCCESS_AFTER_FAILURE,
						"Step succeded, but one of previous steps did fail");

			default:
				break;
			}
		} else if (currentState == StepState.FAILURE) {
			switch (previousState) {
			case SUCCESS:
				return new StepExecutionResult(StepState.FAILURE, message);

			case FAILURE:
				return new StepExecutionResult(StepState.FAILURE_AFTER_FAILURE, message);

			case NULL_OBJECT:
				return new StepExecutionResult(StepState.FAILURE, "Failed as first step: " + message);

			case SUCCESS_AFTER_FAILURE:
				return new StepExecutionResult(StepState.FAILURE_AFTER_FAILURE,
						"Step failded, and one of previous steps did also fail\n" + message);

			default:
				break;
			}
		}

		// not covered
		// this should never be returned
		return null;
	}

	private void validateUniquenessOfMethods(Set<Method> methods) {
		Map<String, List<Method>> functionLocationMap = new HashMap<>();

		methods.stream().forEach(el -> {
			String annotationValue = el.getAnnotation(Step.class).value();
			if (!functionLocationMap.containsKey(annotationValue)) {
				functionLocationMap.put(annotationValue, new ArrayList<>());
			}
			functionLocationMap.get(annotationValue).add(el);
		});

		List<List<Method>> duplicates = functionLocationMap.values().stream().filter(el -> el.size() > 1)
				.collect(Collectors.toList());

		if (duplicates.size() > 0) {
			String errorMessage = generateNotUniequeMethodsErrorMessage(duplicates);
			throw new DuplicateMethodException(errorMessage);
		}
	}

	private String generateNotUniequeMethodsErrorMessage(List<List<Method>> duplicates) {
		StringBuilder sb = new StringBuilder();

		String logDivider = "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";

		sb.append("\n\n" + logDivider + "\n\n");
		sb.append("Unable to execute tests because of duplicate Step names").append("\n");

		for (List<Method> duplicateMethods : duplicates) {
			sb.append("\nDuplicate Step key: " + duplicateMethods.get(0).getAnnotation(Step.class).value())
					.append("\n");

			for (Method m : duplicateMethods) {
				sb.append("\tClass name:       " + m.getDeclaringClass()).append("\n");
				sb.append("\tFunction value:   " + m.getName()).append("\n\n");
			}
		}

		sb.append(logDivider);
		return sb.toString();
	}
}