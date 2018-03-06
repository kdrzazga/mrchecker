package com.capgemini.kabanos.common.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Implementation implements Serializable{

	private long id;
	private String lines;
	private String[] separatedLines = {};
	private int occurrences;
	
	public Implementation() {
		this.lines = "";
		this.occurrences = 1;
	}
	
	public void incrementOccurences(int incBy) {
		this.occurrences += incBy;
	}
	
	public void addLine(String line) {
		this.lines = this.lines.concat("\n").concat(line).trim();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lines == null) ? 0 : lines.hashCode());
		
		result = prime * result;
		
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
		
		Implementation other = (Implementation) obj;
		if (lines == null) {
			if (other.lines != null)
				return false;
			
		} else if (lines.length() != other.lines.length())
			return false;

		if(this.lines.compareTo(other.lines) != 0)
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return this.getLines();
	}
	
	public String getLines() {
		return lines;
	}
	public void setLines(String lines) {
		this.lines = lines;
	}
	public int getOccurrences() {
		return occurrences;
	}
	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String[] getSeparatedLines() {
		return this.separatedLines = lines.split("\n");
	}

	public void setSeparatedLines(String[] separatedLines) {
		this.separatedLines = separatedLines;
	}
}
