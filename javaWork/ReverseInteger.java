package AnswersCheck;

public class ReverseInteger {

	public static void main(String[] args) {
		
		//System.out.print(1/10);
		System.out.print(reverse(100));

	}
	
	public static int reverse(int x){
		
		if(x < 0)
		{
			return ~(reverse(~(x) + 1)) + 1;
		}
		
		int newX = 0;
		while(true)
		{
			newX += x % 10;
			x = x/10;
			if(x == 0)
				break;
			
			newX *= 10;
			if(x < 10)
			{
				newX  += x;
				break;
			}
		}
			
		return newX;
	}
}
