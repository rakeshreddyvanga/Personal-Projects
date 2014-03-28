package iu.edu.cs.p532.pair13.acquire.player.playerstrategies;


import iu.edu.cs.p532.pair13.acquire.player.components.PState;
import iu.edu.cs.p532.pair13.acquire.player.components.Share;
import iu.edu.cs.p532.pair13.acquire.player.exceptions.GameException;

public class eval2 implements IEvaluate{
	public static final int maxHotelSize = 41;
	public static final int totalShares = 25;

	public double evaluateState(PState state,int PlayerID) throws GameException{double pts = 0.0;
	Player cPlayer = state.getPlayers().get(PlayerID);
	if(cPlayer.getCash() >= 0 && cPlayer.getCash() < 50)
		pts = 5;
	if(cPlayer.getCash() >= 50 && cPlayer.getCash() < 100)
		pts = 10;
	if(cPlayer.getCash() >= 100 && cPlayer.getCash() < 175)
		pts = 15;
	if(cPlayer.getCash() >= 175 && cPlayer.getCash() < 250)
		pts = 20;
	if(cPlayer.getCash() >= 250 && cPlayer.getCash() < 300)
		pts = 25;
	if(cPlayer.getCash() >= 300 && cPlayer.getCash() < 400)
		pts = 30;
	if(cPlayer.getCash() >= 400 && cPlayer.getCash() < 500)
		pts = 35;
	if(cPlayer.getCash() >= 500 && cPlayer.getCash() < 600)
		pts = 40;
	if(cPlayer.getCash() >= 600 && cPlayer.getCash() < 700)
		pts = 45;
	if(cPlayer.getCash() >= 700 && cPlayer.getCash() < 800)
		pts = 55;
	if(cPlayer.getCash() >= 800 && cPlayer.getCash() < 900)
		pts = 60;
	if(cPlayer.getCash() >= 900 && cPlayer.getCash() < 1000)
		pts = 65;
	if(cPlayer.getCash() >= 1000 && cPlayer.getCash() < 1200)
		pts = 70;
	if(cPlayer.getCash() >= 1200 && cPlayer.getCash() < 1400)
		pts = 75;
	if(cPlayer.getCash() >= 1600 && cPlayer.getCash() < 1800)
		pts = 80;
	if(cPlayer.getCash() >= 2000 && cPlayer.getCash() < 2200)
		pts = 85;
	if(cPlayer.getCash() >= 2200 && cPlayer.getCash() < 2400)
		pts = 90;
	if(cPlayer.getCash() >= 2400 && cPlayer.getCash() < 2800)
		pts = 95;
	if(cPlayer.getCash() >= 2800 && cPlayer.getCash() < 3000)
		pts = 100;
	if(cPlayer.getCash() >= 3200 && cPlayer.getCash() < 3400)
		pts = 105;
	if(cPlayer.getCash() >= 3400 && cPlayer.getCash() < 3600)
		pts = 110;
	if(cPlayer.getCash() >= 3800 && cPlayer.getCash() < 4000)
		pts = 115;
	if(cPlayer.getCash() >= 3000 && cPlayer.getCash() < 3200)
		pts = 120;
	if(cPlayer.getCash() >= 4000 && cPlayer.getCash() < 5000)
		pts +=   125;
	if(cPlayer.getCash() >= 5000 && cPlayer.getCash() < 5400)
		pts +=   130;
	if(cPlayer.getCash() >= 5400 && cPlayer.getCash() < 5600)
		pts +=   135;
	if(cPlayer.getCash() >= 5600 && cPlayer.getCash() < 5800)
		pts +=   140;
	if(cPlayer.getCash() >= 5800 && cPlayer.getCash() < 6000)
		pts +=   145;
	if(cPlayer.getCash() >= 6000 && cPlayer.getCash() < 6200)
		pts +=  150;
	if(cPlayer.getCash() >= 6200 && cPlayer.getCash() < 6400)
		pts +=  155;
	if(cPlayer.getCash() >= 6400 && cPlayer.getCash() < 6600)
		pts +=  160;
	if(cPlayer.getCash() >= 6800 && cPlayer.getCash() < 7000)
		pts +=  165;
	if(cPlayer.getCash() >= 7000 && cPlayer.getCash() < 8000)
		pts +=  170;
	if(cPlayer.getCash() >= 3000 && cPlayer.getCash() < 4000)
		pts +=  175;
	if(cPlayer.getCash() >= 2000 && cPlayer.getCash() < 3000)
		pts +=  185;
	if(cPlayer.getCash() >= 8000 && cPlayer.getCash() < 9000)
		pts +=  190;
	if(cPlayer.getCash() >= 9000 && cPlayer.getCash() < 10000)
		pts +=  195;
	if(cPlayer.getCash() >= 10000 && cPlayer.getCash() < 11000)
		pts += 200;
	if(cPlayer.getCash() >= 11000 && cPlayer.getCash() < 12000)
		pts +=  205;
	if(cPlayer.getCash() >= 12000 && cPlayer.getCash() < 13000)
		pts +=  210;
	if(cPlayer.getCash() >= 13000 && cPlayer.getCash() < 140000)
		pts +=  220;
	else if(cPlayer.getCash() >= 15000)
		pts +=  250;		
	
	pts /= 500;
	
	double shareValue = 0;
	for(Share s : cPlayer.getShares()){
		shareValue += state.getBoard().getHotelByName(s.getLabel()).getStockCost() * s.getCount();				
		shareValue /= (totalShares - state.getRemShareCountByLabel(s.getLabel()));
	}

	//System.out.println(shareValue);
	
	pts = pts + shareValue/1800;
	
	pts = pts > 1 ? 1 : pts ;
	//System.out.println(pts);
	return pts;
	}
}
