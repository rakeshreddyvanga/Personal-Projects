package iu.edu.cs.p532.pair13.acquire.admin;
import java.util.HashMap;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;


public interface GameParameters {
	int ROWS = 9;
	int COLS = 12;
	int safeHotelSize = 11;
	int numberOfHotels = 7;
	
	String AM = "AMERICAN";
	String CO = "CONTINENTAL";
	String IM = "IMPERIAL";
	String WO = "WORLDWIDE";
	String TO = "TOWER";
	String SA = "SACKSON";
	String FE = "FESTIVAL";
	String Am = "American";
	String Co = "Continental";
	String Im = "Imperial";
	String Wo = "Worldwide";
	String To = "Tower";
	String Sa = "Sackson";
	String Fe = "Festival";
	String[] ListOfHotels = {AM,CO,IM,WO,TO,SA,FE};
	@SuppressWarnings("serial")
	public static final HashMap<Integer,String> rowMap = new HashMap<Integer,String>() {{
		put(1, "A");
		put(2, "B");
		put(3, "C");
		put(4, "D");
		put(5, "E");
		put(6, "F");
		put(7, "G");
		put(8, "H");
		put(9, "I");
		put(0, "zero");
		put(10, "extra");
	}};
	public BiMap<Integer,String> rows = HashBiMap.create(rowMap);
	@SuppressWarnings("serial")
	public static final HashMap<String,String> hotelNameConv = new HashMap<String,String>() {{
		put(AM, Am);
		put(IM, Im);
		put(FE, Fe);
		put(CO, Co);
		put(WO, Wo);
		put(TO, To);
		put(SA, Sa);
	}};
	
	
	
	
}
