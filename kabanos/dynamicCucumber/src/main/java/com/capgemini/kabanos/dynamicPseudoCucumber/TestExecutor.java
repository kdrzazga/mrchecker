package com.capgemini.kabanos.dynamicPseudoCucumber;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import com.capgemini.kabanos.dynamicPseudoCucumber.annotations.Step;
import com.capgemini.kabanos.dynamicPseudoCucumber.domain.MethodInvokeResult;
import com.capgemini.kabanos.dynamicPseudoCucumber.domain.StepExecutionResult;
import com.capgemini.kabanos.dynamicPseudoCucumber.enums.StepState;
import com.capgemini.kabanos.dynamicPseudoCucumber.utils.StepValidator;

//TODP regexy i datadriven


public class TestExecutor {

	private static Reflections reflections = new Reflections(
			new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("")).setScanners(new SubTypesScanner(false),
					new TypeAnnotationsScanner(), new MethodAnnotationsScanner()));

	private Map<String, Object> stepClassMap = new HashMap<>();
	
	
	
	public Map<String, StepExecutionResult> generateExecuteionReport(List<String> stepList) {
		Map<String, Method> methodMap = this.mapMethodsByStep(stepList);

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
					stepReport = this.generateStepReport(step, prevState, methodMap.get(step));
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

	
	

	/*
	 * function returns all methods that have the @Step annotation
	 * mapped by step name for faster execution
	 */
	private Map<String, Method> mapMethodsByStep(List<String> stepList) {
		Set<Method> methods = reflections.getMethodsAnnotatedWith(Step.class);
		
		StepValidator.validateUniquenessOfMethods(methods, stepList);
		
		Map<String, Method> result = new HashMap<>();
		for (Method m : methods)
			result.put(m.getAnnotation(Step.class).value().trim(), m);

		return result;
	}

	
	private MethodInvokeResult invokeMethod(Method m) {
		StepState currentState;
		String message = "";

		try {
			// TODO ogarnac uruchamianie z parametrami
			// tj. testy datadriven
			
			if(!this.stepClassMap.containsKey(m.getDeclaringClass().toString())) {
				this.stepClassMap.put(m.getDeclaringClass().toString(), m.getDeclaringClass().newInstance());
			}
			
			m.invoke(this.stepClassMap.get(m.getDeclaringClass().toString()));

			currentState = StepState.SUCCESS;
		} catch (Exception e) {
			message = e.getMessage();
			currentState = StepState.FAILURE;
		}
		
		return new MethodInvokeResult(currentState, message);
	}
	
	private StepExecutionResult generateStepReport(String step, StepState previousState, Method m) {

		MethodInvokeResult invokeResult = this.invokeMethod(m);
		
		if (invokeResult.getState() == StepState.SUCCESS) {
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
		} else if (invokeResult.getState() == StepState.FAILURE) {
			switch (previousState) {
			case SUCCESS:
				return new StepExecutionResult(StepState.FAILURE, invokeResult.getMessage());

			case FAILURE:
				return new StepExecutionResult(StepState.FAILURE_AFTER_FAILURE, invokeResult.getMessage());

			case NULL_OBJECT:
				return new StepExecutionResult(StepState.FAILURE, "Failed as first step: " + invokeResult.getMessage());

			case SUCCESS_AFTER_FAILURE:
				return new StepExecutionResult(StepState.FAILURE_AFTER_FAILURE,
						"Step failded, and one of previous steps did also fail\n" + invokeResult.getMessage());

			default:
				break;
			}
		}

		// not covered
		// this should never be returned
		return null;
	}
}