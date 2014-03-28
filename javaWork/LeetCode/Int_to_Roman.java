package AnswersCheck;



public class Int_to_Roman {

	public static void main(String[] args) {
		System.out.println(intToRoman(2014));
		}

	public static String intToRoman(int num) {

		StringBuilder ret = new StringBuilder();
		while (num > 0) {
			if (num >= 1000) {
				num -= 1000;
				 ret.append("M");
			} else if (num >= 900) {
				num -= 900;
				 ret.append("CM");
			} else if (num >= 500) {
				num -= 500;
				 ret.append("D");
			} else if (num >= 400) {
				num -= 400;
				 ret.append("CD");
			} else if (num >= 100) {
				num -= 100;
				 ret.append("C");
			} else if (num >= 90) {
				num -= 90;
				ret.append("XC");
			} else if (num >= 50) {
				num -= 50;
				ret.append("L");
			} else if (num >= 40) {
				num -= 40;
				 ret.append("XL");
			} else if (num >= 10) {
				num -= 10;
				ret.append("X");
			} else if (num >= 9) {
				num -= 9;
				ret.append("IX");
			} else if (num >= 5) {
				num -= 5;
				 ret.append("V");
			} else if (num >= 4) {
				num -= 4;
				 ret.append("IV");
			} else {
				num -= 1;
				 ret.append("I");
			}

		}
		return ret.toString();
	}
}
