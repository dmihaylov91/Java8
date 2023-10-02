package com.java8.groupBy;

import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringToCharMap {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String s = "acbefdaabfffff";
		Map<String, Long> map = s.chars().mapToObj(c -> String.valueOf((char)c)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
		
		Map<String, Long> finalMap = new LinkedHashMap<>();
		map.entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
		
		System.out.println(finalMap);

	}

}
