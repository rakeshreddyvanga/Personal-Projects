package AnswersCheck;

import java.util.Arrays;

public class SingleNumber {

	public static void main(String[] args) {
		
		int[] A = {1,2,3,4,3,2,1};
		int ret = 6^5;
		System.out.println(ret);
		System.out.println(singleNumber(A,A.length));
		
	}
	
	/*public static int singleNumber(int[] A) {
		int ret = 0;
		for(int i=0;i<A.length;i++)
		{
			if(ret-A[i] > 0)
				ret -= A[i];
			else
				ret += A[i];
			
			System.out.println(ret);
		}
		return ret;
	}*/
	
	static int  singleNumber(int A[], int n) {
	   
	    while (--n!=0){
	    	A[n-1]^=A[n];
	    	System.out.println(A[n-1]+"--n-1="+(n-1));
	    	}
	    return A[0];
	}
	public static int singleNumber(int[] A, char t) {
		if(A.length%2 == 0)
		{
			System.out.println("The array has either two single numbers or no single number");
		
			return 0;
		}
		
		Arrays.sort(A);		
	
		
		for(int i=0;i+1<A.length;i+=2)
		{
			if(A[i] != A[i+1])
				return A[i];
		}
		
		return A[A.length-1];
        
    }

}
