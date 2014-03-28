package iu.edu.cs.p532.pair13.acquire.player.components;


public class Share{
	
	private String Label;
	private int Count;
	
	public Share(String label, int count) {
		this.Label = label;
		this.Count = count;
	}
	public Share(Share s) {
		this.Label = s.getLabel();
		this.Count = s.getCount();
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	
}