package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;

import iu.edu.cs.p532.pair13.acquire.player.components.PState;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;

public interface iStrategy {

	public PState chooseState(PState state) throws GameException;
	
}
