package AnswersCheck;

public class Merge {

	public static void main(String[] args) {

		int[] A = new int[8];
		A[0] = 1;
		A[1] = 4;
		A[2] = 6;
		A[3] = 8;

		int[] B = { 0, 3, 7, 9 };
		merge(A, 4, B, 4);
		for (int i = 0; i < 8; i++)
			System.out.print(A[i] + "\t");
	}

	public static void merge(int A[], int m, int B[], int n) {

		int j = 0, index = 0;
		for (int i = m; j < n; i++) {
			A[i] = B[j];
			j++;
		}
		
		j =0;
		while( m+j < n && index < n){
			if(A[index] > A[m+j]){
				int temp = A[m+j];
				A[m+j] = A[index];
				A[index] = temp;				
			}
			else
				j++;
		
			index ++;
		}

	}
}
