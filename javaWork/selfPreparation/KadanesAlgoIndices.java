package selfPreparation;

public class KadanesAlgoIndices {
	public static void main(String[] args) {
		 int nums[] = {2,-3,8,13,-20,2,28,-7,889};
		//int nums[] = { -2, -3, -8, -13, -20, -12, -4 };
		sum(nums);

	}

	public static void sum(int[] nums) {
		if (nums == null || nums.length == 0) {
			System.out.println("Not valid input.");
			return;
		}

		// check for all negative numbers case:
		// if all negatives found return the max number(min absolute value) from
		// them.
		int count = 0;
		int maxSum = Integer.MIN_VALUE;
		int maxSIdx = 0;
		for (int idx = 0; idx < nums.length; idx++) {
			if (nums[idx] < 0) // count if the number is negative
				count++;
			else
				break;
			if (maxSum < nums[idx]) {// at the same store the max value
				maxSum = nums[idx];
				maxSIdx=idx;
			}
		}
		if (count == nums.length) { // all the numbers are negative
			System.out.println(maxSum+" at ["+(maxSIdx+1)+", "+(maxSIdx+1)+"]");
			return;
		}
		maxSum = Integer.MIN_VALUE; // reset maxSum value
		maxSIdx = -1;int maxEIdx=-1;
		// Start Kadane's Algorithm
		int curSum = 0;
		int curSIdx=-1;int curEIdx=-1;
		for (int idx = 0; idx < nums.length; idx++) {
			if(curSum+nums[idx] < 0){
				curSum = 0;
				curSIdx=-1;
			}
			else {
				curSum = curSum+nums[idx];
				if(curSIdx==-1) curSIdx = idx;
				curEIdx = idx;
			}
			if(maxSum < curSum){
				maxSum = curSum;
				maxSIdx = curSIdx;
				maxEIdx = curEIdx;
			}
		}
		System.out.println(maxSum+" at ["+(maxSIdx+1)+", "+(maxEIdx+1)+"]");
	}

}
