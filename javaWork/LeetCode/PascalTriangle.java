package AnswersCheck;

import java.util.ArrayList;

public class PascalTriangle {

	public static void main(String[] args) {
		print(generate(4));	
		print(generate(10));
			
	}

	public static ArrayList<ArrayList<Integer>> generate(int numRows) {
		ArrayList<ArrayList<Integer>> out = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> in;
		for (int i = 0; i < numRows; i++) {
			in = new ArrayList<Integer>();
			if (i == 0)
				in.add(1);

			else {
				in.add(1);
				ArrayList<Integer> temp = out.get(i - 1);

				for (int j = 0; j + 1 < temp.size(); j++)
					in.add(temp.get(j) + temp.get(j + 1));

				in.add(1);
			}
			out.add(in);
		}

		return out;
	}

	public static void print(ArrayList<ArrayList<Integer>> out) {
		System.out.println("[");
		for (int i = 0; i < out.size(); i++) {
			System.out.print("[");
				for(Integer item : out.get(i))
					System.out.print(" "+item+" ");
				System.out.println("]");
		}
		System.out.println("]");
	}

}
