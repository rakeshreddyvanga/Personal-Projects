package AnswersCheck;

public class RotateImage {

	public static void main(String[] args) {
	
		int [][] matrix = {{1,2},{3,4}};
		rotate(matrix);
		for(int i =0;i<matrix.length;i++){
            for(int j=0;j<matrix.length;j++)
            	 System.out.print(matrix[i][j]);
            
            System.out.println();
		}
	}
	
	public static void rotate(int[][] matrix) {
        int [][] b = new int[matrix.length][matrix.length];
        
        for(int i =0;i<matrix.length;i++){
            for(int j=0;j<matrix.length;j++) 
                b[i][j] = matrix[matrix.length-1-j][i];
        }
        
        
        for(int i = 0; i < b.length; i++)
            matrix[i] =  b[i].clone();
    }
}
