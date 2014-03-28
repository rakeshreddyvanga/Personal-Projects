package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;

import iu.edu.cs.p532.pair13.acquire.player.components.PState;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;

import java.util.Random;




public class RandomStrategy implements iStrategy{
	
	public PState chooseState(PState state) throws GameException{
		GameTree gameTree = new GameTree(state);
		gameTree.generate();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(gameTree.subtrees.size());
		return gameTree.subtrees.get(randomInt).Data;
	}

}
