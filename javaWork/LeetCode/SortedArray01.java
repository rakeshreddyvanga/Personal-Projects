package AnswersCheck;

public class SortedArray01 {

	public static void main(String[] args) {
		int [] a = {2,2,3,3,4,4,4,4,4,5,5,6};//,6,6,6,6};
		System.out.print(count(a,0,a.length-1,1));

	}

	public static int count(int [] a, int start, int last, int req) {
		if(start > last)
			return 0;
		
		int middle = (start+last)/2, count = 0;
		
		
		if(a[middle] == req) {
			count++;
			int i = middle+1,j = middle-1;
			while (i <= last && a[middle] == a[i]) {
				count++;
				i++;
			}
			while(j>= start && a[middle] == a[j]) {
				count++;
				j--;
			}
		}
		else {
			
			if(req <= a[middle])
				count += count(a, start, middle-1,req);
			else
			count += count(a,middle+1,last,req);
			
		}
		
		return count;
	}

}
