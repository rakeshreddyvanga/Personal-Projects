package iu.edu.cs.p532.pair13.acquire.player.parsers;
import iu.edu.cs.p532.pair13.acquire.player.components.Board;
import iu.edu.cs.p532.pair13.acquire.player.components.Hotel;
import iu.edu.cs.p532.pair13.acquire.player.components.Tile;
import iu.edu.cs.p532.pair13.acquire.player.constants.GameParameters;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;


public class BoardManager implements GameParameters{
	public static boolean checkBoard(Board board) throws GameException{

		String hotelName;
		int adjCounter = 0;
		for(Hotel h : board.getHotels()){
			for (Tile t : h.getTiles()){
				if(t.equals(board.getTileByRowCol(rows.inverse().get(t.getRow()), t.getColumn())));
				else 
					throw new GameException("board does not have " +
							"the tiles that belong to its hotels");
			}
		}
		for (int i=1;i<=ROWS;i++){
			for (int j=1; j<=COLS;j++){
				adjCounter = 0;
				/*if(board.getStatusOfRowCol(i,j).equals("singleton")){
					boolean chk = board.getStatusOfRowCol(i-1,j).equals("free") &&
							board.getStatusOfRowCol(i,j-1).equals("free") &&
							board.getStatusOfRowCol(i+1,j).equals("free") &&
							board.getStatusOfRowCol(i,j+1).equals("free");
					if(!chk){
						throw new GameException("board has two " +
								"consecutive occupied tiles that are not part of a hotel chain");
						
					}

				}*/
				if(!board.getStatusOfRowCol(i,j).equals("singleton") 
						&& !board.getStatusOfRowCol(i,j).equals("free")){
					hotelName = board.getStatusOfRowCol(i,j);

					if(!board.getStatusOfRowCol(i-1,j).equals("free")){
						if(!board.getStatusOfRowCol(i-1,j).equals(hotelName)){
							throw new GameException(hotelName+
									" has adjacent occupied tiles that are not part of its chain");
						}
						adjCounter++;
					}
					if(!board.getStatusOfRowCol(i,j-1).equals("free")){
						if(!board.getStatusOfRowCol(i,j-1).equals(hotelName)){
							throw new GameException(hotelName+
									" has adjacent occupied tiles that are not part of its chain");
							
						}
						adjCounter++;
					}
					if(!board.getStatusOfRowCol(i+1,j).equals("free")){
						if(!board.getStatusOfRowCol(i+1,j).equals(hotelName)){
							throw new GameException(hotelName+
									" has adjacent occupied tiles that are not part of its chain");
							
						}
						adjCounter++;
					}
					if(!board.getStatusOfRowCol(i,j+1).equals("free")){
						if(!board.getStatusOfRowCol(i,j+1).equals(hotelName)){
							throw new GameException(hotelName+
									" has adjacent occupied tiles that are not part of its chain");
							
						}
						adjCounter++;
					}
					if(adjCounter == 0){
						throw new GameException(hotelName+
								" has only one tile in its chain");
						
					}
				}
			}
		}
		return true;
	}
	
}