package selfPreparation;

import java.util.Scanner;

public class Squareroot 
{

//Newton - Rahson Method
	public static void main(String[] args) 
	{
		System.out.println("Please enter a number to find the squareroot");
		Scanner scan = new Scanner(System.in);
		double num =Integer.parseInt(scan.next());
		double x= num;
		double y = (0.5)*(x +(num/x));
		while(x!=y)
		{
			x = y;
			y= (0.5)*(x +(num/x));
		}
		System.out.println("Final squareroot of "+num+" ----> "+y);

	}

}
