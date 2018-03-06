package com.capgemini.kabanos.common.utility;

import java.util.List;

public class ArrayUtils {

	public static <T> String toString(T[] array) {
		StringBuilder sb = new StringBuilder();	
		sb.append("[");
		for(T t: array) {
			sb.append(t.toString());
			sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static <T> List<T> megre(List<T> l, List<T> r) {
		l.addAll(r);
		return l;
	}
}
