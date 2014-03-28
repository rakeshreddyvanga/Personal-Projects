package AnswersCheck;

public class RotatedIndex {

	public static void main(String[] args) {  
		int [] a = {1,2,3,1,1,1,1,1};
		System.out.print(rotate(a,0,a.length-1));

	}
	
	public static int rotate(int [] a, int start, int end) {
		
		int middle = (start+end)/2, ret = -1;
		
		if(a[start] > a[end]) {
			
			if((end-start) == 1) 
					return end;
			
			if(a[start] > a[middle]) {
				ret = rotate(a,start,middle);
			}
			else  {
				ret = rotate(a,middle,end);
			}
		}
		else {
			System.out.println("No Solution  " + a[start]+" -- "+a[end]);
		}
		
		return ret;
		
	}

}
