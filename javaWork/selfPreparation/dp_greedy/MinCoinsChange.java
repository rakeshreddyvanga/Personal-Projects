package selfPreparation;

public class MinCoinsChange {

	public static void main(String[] args) {
		int coins[] = {9,6,5,1};
		System.out.println(dp(coins,11,coins.length));

	}
	
	public static int recur(int coins[], int sum, int n){
		if(coins==null) return -1;
		
		if(sum == 0) return 0;
		
		int res = Integer.MAX_VALUE;
		
		for(int coin=0;coin < n;coin++){
			if(coins[coin] <= sum){
				int subRes = recur(coins,sum-coins[coin],n);
				
				if(subRes != Integer.MAX_VALUE && subRes + 1 < res)
					res = subRes+1;
			}
		}
		return res;		
	}
	
	public static int dp(int coins[], int sum, int n) {
		if(coins==null) return -1;
		
		int[] table = new int[sum+1];
		table[0] = 0;
		int subRes = -1;
		
		for (int i = 1; i < table.length; i++) {
			table[i] = Integer.MAX_VALUE;
		}
		
		for(int curSum=1;curSum<=sum;curSum++){
			
			for(int coin=0;coin<n;coin++){
				if(curSum>=coins[coin]){
					subRes = table[curSum-coins[coin]];
					if(subRes != Integer.MAX_VALUE && subRes+1 < table[curSum])
						table[curSum]=subRes+1;
				}
			}
		}
		
		return table[sum];
	}

}
