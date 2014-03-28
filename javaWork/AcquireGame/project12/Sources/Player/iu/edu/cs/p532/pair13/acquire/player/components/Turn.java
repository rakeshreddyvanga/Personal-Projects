package iu.edu.cs.p532.pair13.acquire.player.components;
import iu.edu.cs.p532.pair13.acquire.player.constants.GameParameters;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;
import iu.edu.cs.p532.pair13.acquire.player.playerstrategies.Player;
//import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;
import java.util.ArrayList;

public class Turn implements GameParameters{

	private Board board;
	private Player player;
	private ArrayList<Share> shares;
	private ArrayList<Hotel> xHotels;
	
	public Turn(Board board, Player player, ArrayList<Share> shares,ArrayList<Hotel> xHotels) {
		this.board = board;
		this.player = player;
		this.shares = shares;
		this.xHotels = xHotels;
	}
	public PState toPState() throws GameException{
		PState pState = new PState();
		pState.setBoard(this.board);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(this.player);
		/*Board board = pState.getBoard();
		Player opp1 = new Player(10000);
		ArrayList<Tile> oppTiles = opp1.getTiles();
		for (int i=1;i<=ROWS;i++){
			for (int j=1; j<=COLS;j++){
				if(board.getStatusOfRowCol(i,j).equals("free") && board.getAllocatedRowCol(i,j) == 0
						&& !board.InspectTile(board.getTileByRowCol(i,j)).equals("safe"))
					oppTiles.add(board.getTileByRowCol(i,j));
			}
		}*/
		players.add(new Player(10000));
		players.add(new Player(10000));
		players.add(new Player(10000));
		pState.setPlayers(players);
		pState.setRemainingShares(this.shares);
		return pState;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public ArrayList<Share> getShares() {
		return shares;
	}
	public void setShares(ArrayList<Share> shares) {
		this.shares = shares;
	}
	public ArrayList<Hotel> getxHotels() {
		return xHotels;
	}
	public void setxHotels(ArrayList<Hotel> xHotels) {
		this.xHotels = xHotels;
	}
}