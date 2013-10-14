using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace knapsackProblem
{
    class Program
    {
        int[][] matrix =null  ;
        public void knapsack(int [] weight, int n, int capacity, int [] value)
        {
            matrix = new int[n][];
            for (int i = 0; i < n; i++)
            {
                matrix[i] = new int[capacity];
            }
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < capacity; j++)
                {
                    if(i == 0 || j == 0)
                    {
                        matrix[i][j] = 0;
                    }
                    else if(weight[i] > j)
                    {
                        matrix[i][j] = matrix[i - 1][j];
                    }
                    else {
                        matrix[i][j] = maximum (matrix[i-1][j] , matrix[i-1][j - weight[i]] + value[i]);
                    }
                }
            }
            Console.WriteLine("the optimal solution is " + matrix[n-1][capacity-1]);
                   
            
        }
        public int maximum(int a, int b)
        {
            if (a > b)
            {
                return a;
            }
            else
            {
                return b;
            }
        }

        public void checkItem(int n,int capacity,int[] weight)
        {           
           int i=n-1;	
           int j = capacity-1;
            int [] check = new int[n];
           for (int m = 0; m < n; m++)
			{
		        check[m] = 0;	 
			}		
	
	    while(i > 0 && j > 0)
        {
	        if(matrix[i][j] != matrix[i-1][j])
            {
			    check[i]=1;
			    j=j-weight[i];
            }
		    i=i-1;
        }

        for (int k = 0; k < n; k++)
        {
            if (check[k] == 1)
            {
                Console.WriteLine(k+1);
            }
        }
			

        }
        static void Main(string[] args)
        {
            Program obj = new Program();
            int[] weight = { 5, 4, 6, 3 };
            int[] value = { 10, 40, 30, 50 };
            obj.knapsack(weight, 4, 10, value);
            obj.checkItem(4, 10, weight);
            Console.ReadLine();
        }
    }
}
