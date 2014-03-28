package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;
import iu.edu.cs.p532.pair13.acquire.player.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.player.components.Share;
import iu.edu.cs.p532.pair13.acquire.player.components.Tile;

import java.util.ArrayList;

public interface PlayerStrategy{
	public Share ChooseShare(ArrayList<Share> shares);
	public Tile ChooseTile(ArrayList<Tile> tiles);
	public String ChooseFoundngHotel(ArrayList<Hotel> hotels);
}