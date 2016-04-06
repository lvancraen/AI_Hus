package student_player.mytools;

import hus.HusBoardState;

public class MyTools {
	
    public static double getSomething(){
        return Math.random();
    }
    
  //evaluation function that I call from my StudentPlayer Class.

  //put this into your MyTools class. 

      public static double EvaluationFunction(HusBoardState current_board, int player_id, int opponent_id){
      	
      	double score = 0;
      	//turn number
      	//int turns = current_board.getTurnNumber();
      	
      	
      	//want to check number of seeds
      	int seedsTotal = 0;

      	//want to check the number of pits that contain 1 pit for opponent
      	int opponents01Pits = 0; 
      	
      	//want to check how many seeds are in capture locations total seeds, not pits
      	
      	
      	int[][] currentPits = current_board.getPits();
  		int[] myPits = currentPits[player_id];
  		int[] oppPits = currentPits[opponent_id];
  		
  		
  		for(int i = 0; i < 32; i++){
  			seedsTotal = seedsTotal + myPits[i];
  			if (oppPits[i] == 0 || oppPits[i]==1){
  				opponents01Pits++;
  			}
  		}
      	
      	score = 0*(opponents01Pits) + 1*seedsTotal;
      	
      	
      	return score;
      	
      }

}

