package com.capgemini.kabanos.common.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Preposition {

	private long id;
	private String loggerStep;
	private String formattedLoggerStep;
	private long totalNumber = 1;
	private Project project;
	private Set<Implementation> implementations = new HashSet<Implementation>();
	private Set<Preposition> predecessors = new HashSet<Preposition>();
	
	public Preposition() {}
	
	public Preposition(String loggerStep, String formattedLoggerStep) {
		this.loggerStep = loggerStep;
		this.formattedLoggerStep = formattedLoggerStep;
	}
	
	public void addImplementation(Implementation impl) {
		for(Implementation iter : this.implementations) {
			if(iter.equals(impl)) {
				iter.incrementOccurences(impl.getOccurrences());
				return;
			}
		}
		//if not find in set then add
		implementations.add(impl);
	}	
	
	public void addImplementations(Collection<Implementation> impls) {
		for(Implementation impl : impls) {
			this.addImplementation(impl);
		}
	}	
	
	public void addPredecessor(Preposition pred) { 
		if(pred != null)
			this.predecessors.add(pred);
	}	
	
	public void addPredecessors(Collection<Preposition> preds) {
		this.predecessors.addAll(preds);
	}		
	public String getLoggerStep() {
		return loggerStep;
	}
	public void setLoggerStep(String loggerStep) {
		this.loggerStep = loggerStep;
	}
	public Set<Implementation> getImplementations() {
		return implementations;
	}
	public void setImplementations(Set<Implementation> implementations) {
		this.implementations = implementations;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((loggerStep == null) ? 0 : loggerStep.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Preposition other = (Preposition) obj;
		if (formattedLoggerStep == null) {
			if (other.formattedLoggerStep != null)
				return false;
		} else if (!formattedLoggerStep.equals(other.formattedLoggerStep))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nKEY:\n\t" + this.loggerStep);
		sb.append("\nFORMATTED KEY:\n\t" + this.formattedLoggerStep);
		sb.append("\nFOUND TIMES:\n\t" + this.totalNumber);
		sb.append("\nPROJECT:\n\t" + this.project.getName() + "\n");
		sb.append("IMPLEMENTATION:\n");
		
		for(Implementation implementation : this.implementations) {
			for(String line : implementation.getLines().split("\n")) {
				sb.append("\t" + line.toString() + "\n");
			}
		}
		
		sb.append("\nPREDECESSORS:\n");
		if(this.predecessors.size() == 0) {
			sb.append("\t No predecessor\n");
		}
		else {
			int x = 1;
			for(Preposition prep : this.predecessors) {
				sb.append("\t" + (x++) + ") " + prep.getLoggerStep() + "\n");
			}
		}
		sb.append("_______________________________\n");
		return sb.toString();
	}

	public Set<Preposition> getPredecessors() {
		return predecessors;
	}

	public void setPredecessors(Set<Preposition> predecessors) {
		this.predecessors = predecessors;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void addTotalNumber(long totalNumber) {
		this.totalNumber += totalNumber;
	}
	
	public long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(long totalNumber) {
		this.totalNumber = totalNumber;
	}

	public String getFormattedLoggerStep() {
		return formattedLoggerStep;
	}

	public void setFormattedLoggerStep(String formattedLoggerStep) {
		this.formattedLoggerStep = formattedLoggerStep;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
}