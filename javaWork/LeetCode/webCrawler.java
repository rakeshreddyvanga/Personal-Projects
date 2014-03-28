package AnswersCheck;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class webCrawler {
	   public static void main(String[] args)  {
	        try {
	            URL my_url = new URL("http://google.com");
	            BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));
	            String strTemp = "";
	            while(null != (strTemp = br.readLine())){
	            System.out.println(strTemp);
	        }
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}
