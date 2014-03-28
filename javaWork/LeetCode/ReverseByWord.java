package AnswersCheck;

public class ReverseByWord {

	public static void main(String[] args) {
		String str = "rakesh   ";
		System.out.println(reverse(str));

	}
	
	public static String reverse(String sentence)
 {
		char[] ans = new char[sentence.length()];
		String word = "";

		int unusedlen = sentence.length();
		for (int i = 0; i <= sentence.length(); i++) {
			if (i < sentence.length() && sentence.charAt(i) != ' ') {
				word = word.concat(""+sentence.charAt(i));

			}

			else if (i == sentence.length() || sentence.charAt(i) == ' ') {
				int index = unusedlen - word.length();
				int k = 0;
				
				for (int j = index; j < unusedlen; j++) {
					ans[j] = word.charAt(k);
					k++;
				}
				if (index-1> 0) {
				ans[index-1] = ' ';
				index++;
			}
				unusedlen = unusedlen - word.length() - 1;
				word = "";

			}
		}

		if (word.length() == sentence.length())
			return sentence;
		else
			return new String(ans);
	}
	
	public void longestPalindromeDP(String s) {
		  int n = s.length();
		  int longestBegin = 0;
		  int maxLen = 1;
		 // bool table[1000][1000] = {false};
		  for (int i = 0; i < n; i++) {
		   // table[i][i] = true;
		  }
		  for (int i = 0; i < n-1; i++) {
		   // if (s[i] == s[i+1]) {
		     // table[i][i+1] = true;
		      longestBegin = i;
		      maxLen = 2;
		    }
		  //}
		  for (int len = 3; len <= n; len++) {
		    for (int i = 0; i < n-len+1; i++) {
		      int j = i+len-1;
		     /* if (s[i] == s[j] && table[i+1][j-1]) {
		        table[i][j] = true;*/
		        longestBegin = i;
		        maxLen = len;
		      }
		    }
		  //}
		  //return s.substr(longestBegin, maxLen);
		}

}
