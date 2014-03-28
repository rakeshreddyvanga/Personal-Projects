package AnswersCheck;

import java.util.ArrayList;
//import java.util.Hashtable;

public class PascalTriangleII {

	public static void main(String[] args) {
		
		//System.out.print(fact(13)+"-"+fact(12));
		//print(getRow(30));
		getRow(30);
	}
	
	public static long fact(long  num)
	{
		if(num == 0)
			return 1;
		 
		return num * fact(num-1);
	}
	 public static ArrayList<Integer> getRow(int rowIndex) {
		 //Hashtable<String, Integer> map = new Hashtable<String, Integer>();
		 
	        ArrayList<Integer> ret = new ArrayList<Integer>();
	        ret.add(1);
	        for(int i=1;i<=rowIndex;i++) {
	        	ret.add(ret.get(i-1) * (rowIndex-i+1)/i);
	        	/*double res = ret.get(i-1) * (rowIndex-i+1)/i ;
	        	int ires = (int)res;
	        	ret.add(ires);
	        	System.out.println("Integer:" + ires + "\tlong:"+ res);*/
	        }
	        return ret;
	    }

	 public static void print(ArrayList<Integer> ret)
	 {
		 System.out.print("[");
		 for(int i =0;i<ret.size();i++)
			 System.out.print(" "+ret.get(i));
		 System.out.println("]");
	 }
}
