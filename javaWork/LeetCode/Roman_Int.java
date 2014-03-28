package AnswersCheck;

import java.util.HashMap;

public class Roman_Int {

	public static void main(String[] args) {
		
		System.out.print(romanToInt("MM"));
	}

	public static int romanToInt(String s) {
		
		char[] romans = s.toCharArray();
		int ret = 0;
		HashMap<String, Integer> ref = new HashMap<String, Integer>();
		ref.put("I", 1);
		ref.put("V", 5);
		ref.put("XC", 90);
		ref.put("CD", 400);
		ref.put("X", 10);
		ref.put("L", 50);
		ref.put("C", 100);
		ref.put("D", 500);
		ref.put("M", 1000);
		ref.put("XL", 40);
		ref.put("IX", 9);
		ref.put("IV", 4);
		ref.put("CM", 900);
		
		for(int i=0;i<romans.length;i++)
		{
			if(i+1 < romans.length) {
				String str = ""+romans[i] + romans[i+1];
				if(ref.containsKey(str)){
					ret += ref.get(str);
					i++;
					continue;
				}			
			}
			if(ref.containsKey(""+romans[i]))				
				ret += ref.get(""+romans[i]);
		}

		return ret;
	}

}
