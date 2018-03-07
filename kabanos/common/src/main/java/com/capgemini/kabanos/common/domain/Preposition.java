package com.capgemini.kabanos.common.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Preposition {

	private long id;
	private Set<Preposition> predecessors = new HashSet<Preposition>();
	private String loggerStep;
	private long totalNumber = 1;
	private Set<Implementation> implementations = new HashSet<Implementation>();
	
	public Preposition() {}
	
	public Preposition(String loggerStep) {
		this.loggerStep = loggerStep;
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
		if (loggerStep == null) {
			if (other.loggerStep != null)
				return false;
		} else if (!loggerStep.equals(other.loggerStep))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nKEY:\n\t" + this.loggerStep);
		sb.append("\nfound times:\n\t" + this.totalNumber + "\n");
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
		System.out.println("ADDDDD");
		this.totalNumber += totalNumber;
	}
	
	public long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(long totalNumber) {
		this.totalNumber = totalNumber;
	}
}