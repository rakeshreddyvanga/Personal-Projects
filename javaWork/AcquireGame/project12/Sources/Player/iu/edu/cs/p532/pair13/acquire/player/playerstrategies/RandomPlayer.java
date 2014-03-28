package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;

import iu.edu.cs.p532.pair13.acquire.player.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.player.components.Share;
import iu.edu.cs.p532.pair13.acquire.player.components.Tile;
import iu.edu.cs.p532.pair13.acquire.player.constants.GameParameters;

import java.util.ArrayList;
import java.util.Random;


public class RandomPlayer implements PlayerStrategy,GameParameters{

	public Tile ChooseTile(ArrayList<Tile> tiles) {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(tiles.size());
		return tiles.get(randomInt);
	}
	public Share ChooseShare(ArrayList<Share> shares) {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(shares.size());
		return shares.get(randomInt);
	}
	public String ChooseFoundngHotel(ArrayList<Hotel> hotels) {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(hotels.size());
		return hotels.get(randomInt).getName();
	}

}