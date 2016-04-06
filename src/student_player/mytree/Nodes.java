package student_player.mytree;

import hus.HusMove;

//Nic Yantzi
//Object that I will use when performing Minimax algorithm to store the score and move associated with that score. 

public class Nodes {
	
	private HusMove move;
	private int score;
	
	public Nodes(){
		
	}
	
	public void setScore(int score){
		this.score = score;
	}
	public void setMove(HusMove move){
		this.move = move;
	}
	public int getScore(){
		return score;
	}
	public HusMove getMove(){
		return move;
	}
	
}
