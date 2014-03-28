package iu.edu.cs.p532.pair13.acquire.components;


import iu.edu.cs.p532.pair13.acquire.admin.BoardManager;
import iu.edu.cs.p532.pair13.acquire.admin.GameParameters;
import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;

public class Tile implements GameParameters{

	private String row;
	private int column;
	private String status;
	private int allocated;

	public Tile(int row, int col,String status,int allocated) throws GameException{
		this.setRow(row);
		this.setColumn(col);
		this.setStatus(status);
		this.setAllocated(allocated);
	}
	public Tile(String row, int col,String status,int allocated) throws GameException{
		this.setRow(row);
		this.setColumn(col);
		this.setStatus(status);
		this.setAllocated(allocated);
	}
	public Tile(Tile t){
		this.row = t.getRow();
		this.column = t.getColumn();
		this.status = t.getStatus();
		this.allocated = t.getAllocated();
	}
	public String getStatus() {
		return status;
	}
	public boolean setStatus(String status) throws GameException{
		if(status.equals("free")){
			this.status = status;
			return true;
		}
		else if(status.equals("singleton") || BoardManager.isHotel(status)){
			if (rows.inverse().get(row) > 0 && rows.inverse().get(row) <= ROWS && column > 0 && column <= COLS){
				this.status = status;
				return true;
			}
			else{
				throw new GameException("Invalid tile status for this tile");
			}
		}
		else
			throw new GameException("Invalid tile status");
	}
	public String getRow() {
		return row;
	}
	public boolean setRow(String row) throws GameException {
		if(rows.inverse().get(row) >= 0 && rows.inverse().get(row) <= ROWS + 1){
			this.row = row;
			return true;
		}
		else
			throw new GameException("Invalid tile row");
	}
	public boolean setRow(int row) throws GameException {
		if(row >= 0 && row <= ROWS + 1){
			this.row = rows.get(row);
			return true;
		}
		else
			throw new GameException("Invalid tile row");
		
	}
	public int getColumn() {
		return column;
	}
	public boolean setColumn(int column) throws GameException {
		if(column >= 0 && column <= COLS + 1){
			this.column = column;
			return true;
		}
		else
			throw new GameException("Invalid tile column");	
	}
	public int getAllocated() {
		return allocated;
	}
	public boolean setAllocated(int allocated) throws GameException {
		if(allocated == 0 || allocated == 1){
			this.allocated = allocated;
			return true;
		}
		else
			throw new GameException("Invalid tile allocation value");
		
	}
	public boolean equals(Tile tile){
		if(tile != null && tile.row.equals(this.row) && tile.column == this.column 
				&& this.status.equals(tile.status)  && this.allocated == tile.allocated)
			return true;
		
		//System.out.println("failed in tile comp" + tile.getRow()+tile.getColumn()+" "+this.getRow()+this.getColumn());
		return false;

	}

}