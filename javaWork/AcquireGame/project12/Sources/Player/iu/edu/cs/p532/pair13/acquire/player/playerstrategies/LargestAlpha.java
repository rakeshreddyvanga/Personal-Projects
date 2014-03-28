package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;

import iu.edu.cs.p532.pair13.acquire.player.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.player.components.Share;
import iu.edu.cs.p532.pair13.acquire.player.components.Tile;

import java.util.ArrayList;

public class LargestAlpha implements PlayerStrategy {

	@Override
	public Share ChooseShare(ArrayList<Share> shares) {
		Share Smallest = shares.get(0);
		for(int i =1;i<shares.size();i++){
			if(shares.get(i).getLabel().compareTo(Smallest.getLabel()) < 0)
				Smallest = shares.get(i);
		}
		return Smallest;
	}

	@Override
	public Tile ChooseTile(ArrayList<Tile> tiles) {
		Tile largest = tiles.get(0);
		for(int i = 1 ; i<tiles.size() ; i++)
		{
			if(tiles.get(i).getRow().compareTo(largest.getRow()) > 0 ||  
				(tiles.get(i).getRow().equals(largest.getRow()) && 
						tiles.get(i).getColumn() > largest.getColumn()))
			{
				largest = tiles.get(i);
			}
		}
		
		return largest;
	}

	@Override
	public String ChooseFoundngHotel(ArrayList<Hotel> hotels) {
		Hotel Smallest = hotels.get(0);
		for(int i =1;i<hotels.size();i++){
			if(hotels.get(i).getName().compareTo(Smallest.getName()) < 0)
				Smallest = hotels.get(i);
		}
		return Smallest.getName();
	}
	

}
