package iu.edu.cs.p532.pair13.acquire.admin;

//import iu.edu.cs.p532.pair13.acquire.exceptions.GameException;


public class gametester{
	public static void main(String argv[]){

		/*try {
			int oCount = 0,rCount =0,lCount = 0, sCount = 0;
			for(int j=1;j<=1;j++){
				Game game = new Game();
				String[] players = {"eval1","rand1","eval2","rand2"};
				State state = game.runGame(players);
				System.out.println("Game : " + j);
				if(state.getPlayers().get(0).getName().equals("ordered")){
					oCount++;
				}
				else if(state.getPlayers().get(0).getName().equals("random")){
					rCount++;
				}
				else if(state.getPlayers().get(0).getName().equals("lalpha")){
					lCount++;
				}
				else{
					sCount++;
				}
				for(int i =0;i<4;i++)
					System.out.println(state.getPlayers().get(i).getName() + " " + state.getPlayers().get(i).getCash());
				System.out.println("-------------");
			}
			System.out.println("Ordered Won " + oCount + " times");
			System.out.println("Random Won " + rCount + " times");
			System.out.println("Largest-Alpha Won " + lCount + " times");
			System.out.println("Smallest-Anti Won " + sCount + " times");
		}catch (GameException b){
			String ErrorElement = "<error msg=\""+b.getMessage()+"\" />";
			System.out.println(ErrorElement);
			b.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
*/
	}
}