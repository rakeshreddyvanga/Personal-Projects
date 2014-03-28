package AnswersCheck;

import java.util.HashMap;

public class Equilibrium {
	/**
	 * @author Rakesh Reddy Vanga
	 * @version 1.0.0
	 * @param 
	 * @return 
	 */
	public static void main(String[] args) {
		int A[] = { -7,1,5,2,-4,3,0};
		System.out.println(solution(A));

	}
	    public static int solution(int[] A) {
	        HashMap<Integer, Long> frnt = new HashMap<Integer, Long>();
	       HashMap<Integer, Long> back = new HashMap<Integer, Long>();
	        long frntSum = (long) 0, backSum = (long) 0;
	        for(int i =0;i<A.length;i++) {
	            frntSum += (long)A[i];
	            backSum += (long)A[A.length-i-1];
	            if(!frnt.containsKey(i)) {
	                frnt.put(i,frntSum);
	            }
	            
	            if(!back.containsKey(i)) {
	                back.put(i,backSum);
	            }
	            
	        }
	        
	        for(int i = 0;i<A.length;i++) {
	            if(i == 0 && back.get(i+1) == 0)
	                return i;
	            if( i == A.length-1 && frnt.get(i-1) == 0)
	                return i;
	            if(frnt.get(i-1) == back.get(i+1))
	                return i;
	                
	        }
	        
	        return -1;
	        
	    }
	}

