package hus;

import hus.HusBoardState;
import hus.HusPlayer;
import hus.HusMove;

import student_player.mytools.MyTools;
import student_player.mytree.Nodes;
import student_player.node.Node;

import java.util.ArrayList;
import java.util.Random;

/** A random Hus player. */
public class RandomHusPlayer extends HusPlayer {
    Random rand = new Random();

    public RandomHusPlayer() { super("RandomHusPlayer"); }
    public HusMove chooseMove(HusBoardState board_state)
    {
    	long startTime = System.nanoTime();
    	//System.out.println("Turn Number: "+board_state.getTurnNumber());
    	if (board_state.getTurnNumber() == 0) {
    		Node startNode = new Node(board_state);
    		startNode = callMiniMaxAB(board_state, -10000, 10000, 7, startNode, startTime);
    		int tempMove = 0;
	    	int tempHighScore = -10;
	    	for (int i=0; i<startNode.numChildren(); i++) {
	    		if (tempHighScore < startNode.getChildAt(i).getScore()) {
	    			tempHighScore = startNode.getChildAt(i).getScore();
	    			tempMove = i;
	    		}
	    	}
	    	HusMove move = board_state.getLegalMoves().get(tempMove);
	    	//System.out.println("Time left over: "+(double)(System.nanoTime() - startTime)/1000000000);
	    	return move;
    	}
    	else {
    	int seed = eval(board_state);
    	//System.out.println("Seed count: "+seed);
	    	if (44 < seed && seed < 51) {
		    	Node root = new Node(board_state);
		    	//System.out.println("MyLegalMoves: "+board_state.getLegalMoves().size());
		    	root = callMiniMaxAB(board_state, -10000, 10000, 6, root, startTime);
		    	int tempMove = 0;
		    	int tempHighScore = -10;
		    	for (int i=0; i<root.numChildren(); i++) {
		    		if (tempHighScore < root.getChildAt(i).getScore()) {
		    			tempHighScore = root.getChildAt(i).getScore();
		    			tempMove = i;
		    		}
		    	}
		    	HusMove move = board_state.getLegalMoves().get(tempMove);
		    	//System.out.println("Time left over: "+(double)(System.nanoTime() - startTime)/1000000000);
		    	return move;
	    	} else if ((51 <= seed && seed <= 70) || (25 <= seed && seed <= 44)) {
	    		Node startNode = new Node(board_state);
	    		//System.out.println("MyLegalMoves: "+board_state.getLegalMoves().size());
	    		startNode = callMiniMaxAB(board_state, -10000, 10000, 7, startNode, startTime);
	    		int tempMove = 0;
		    	int tempHighScore = -10;
		    	for (int i=0; i<startNode.numChildren(); i++) {
		    		if (tempHighScore < startNode.getChildAt(i).getScore()) {
		    			tempHighScore = startNode.getChildAt(i).getScore();
		    			tempMove = i;
		    		}
		    	}
		    	HusMove move = board_state.getLegalMoves().get(tempMove);
		    	//System.out.println("Time left over: "+(double)(System.nanoTime() - startTime)/1000000000);
		    	return move;
	    	} else if ((70 < seed && seed <= 80) || (15 <= seed && seed < 25)) {
	    		Node startNode = new Node(board_state);
	    		//System.out.println("MyLegalMoves: "+board_state.getLegalMoves().size());
	    		startNode = callMiniMaxAB(board_state, -10000, 10000, 7, startNode, startTime);
	    		int tempMove = 0;
		    	int tempHighScore = -10;
		    	for (int i=0; i<startNode.numChildren(); i++) {
		    		if (tempHighScore < startNode.getChildAt(i).getScore()) {
		    			tempHighScore = startNode.getChildAt(i).getScore();
		    			tempMove = i;
		    		}
		    	}
		    	HusMove move = board_state.getLegalMoves().get(tempMove);
		    	//System.out.println("Time left over: "+(double)(System.nanoTime() - startTime)/1000000000);
		    	return move;
	    	} 
	    	/*
	    	else if ((80 < seed && seed <= 96) || (0 <= seed && seed < 15)) {
	    		Node startNode = new Node(board_state);
	    		System.out.println("MyLegalMoves: "+board_state.getLegalMoves().size());
	    		startNode = callMiniMaxAB(board_state, -10000, 10000, 9, startNode, startTime);
	    		int tempMove = 0;
		    	int tempHighScore = -10;
		    	for (int i=0; i<startNode.numChildren(); i++) {
		    		if (tempHighScore < startNode.getChildAt(i).getScore()) {
		    			tempHighScore = startNode.getChildAt(i).getScore();
		    			tempMove = i;
		    		}
		    	}
		    	HusMove move = board_state.getLegalMoves().get(tempMove);
		    	//System.out.println("Time left over: "+(double)(System.nanoTime() - startTime)/1000000000);
		    	return move;
	    	}
	    	*/
	    	else {
	    		Node root = new Node(board_state);
	    		HusMove move = null;
	    		int tempMove = 0;
	    		double tempHighWinLoss = 0;
	    		
		    	for (int i=0; i<board_state.getLegalMoves().size(); i++) {
		    		HusBoardState tempBoard = (HusBoardState) board_state.clone();
		    		tempBoard.move(board_state.getLegalMoves().get(i));
		    		Node child = new Node(tempBoard);
		    		double winLoss = monteCarlo(child);
		    		//System.out.println("Move: "+i+" with perctWinLoss: "+winLoss);
		    		if (tempHighWinLoss < winLoss) {
		    			//System.out.println("New move being chosen");
		    			tempMove = i;
		    			tempHighWinLoss = winLoss;
		    			 move = board_state.getLegalMoves().get(tempMove);
		    		} else {
		    			//System.out.println("Keep old move");
		    		}
		    	}
		    	//System.out.println("Time left over: "+(double)(System.nanoTime() - startTime)/1000000000);
		    	return move;
	    	}
    	}
    }
    
    public Node callMiniMaxAB(HusBoardState board_state, int alpha, int beta, int depth, Node root, long startTime) {
    	ArrayList<HusMove> boardMoves = new ArrayList<HusMove>();
    	boardMoves = board_state.getLegalMoves();
    	for(int i = 0; i < boardMoves.size(); i++) {
    		//System.out.println("Time left over: "+(double)(System.nanoTime() - startTime)/1000000000);
    		HusBoardState tempBoard = (HusBoardState) board_state.clone();
    		tempBoard.move(boardMoves.get(i));
    		//System.out.println("OppLegalMoves: "+tempBoard.getLegalMoves().size());
    		Node child = new Node(tempBoard);
    		int highScore = minAB(tempBoard, alpha, beta, depth-1);
    		child.setScore(highScore);
    		root.addChild(child);
    		
    	}
    	return root;
    }
    
    public int maxAB(HusBoardState board_state, int alpha, int beta, int depth) {
    	if (depth == 0 || board_state.getLegalMoves().size() == 0) {
    		int score = eval(board_state);
    		return score;
    	} else {
	    	for (int i=0; i<board_state.getLegalMoves().size(); i++) {
	    		HusBoardState tempBoard = (HusBoardState) board_state.clone();
	    		tempBoard.move(tempBoard.getLegalMoves().get(i));
	    		alpha = Math.max(alpha, minAB(tempBoard, alpha, beta, depth-1));
	    		if (alpha >= beta) {
	    			return beta;
	    		}
	    	}
	    	return alpha;
    	}
    }

    public int minAB(HusBoardState board_state, int alpha, int beta, int depth) {
    	if (depth == 0 || board_state.getLegalMoves().size() == 0) {
    		int score = eval(board_state);
    		return score;
    	} else {
	    	for (int i=0; i<board_state.getLegalMoves().size(); i++) {
	    		HusBoardState tempBoard = (HusBoardState) board_state.clone();
	    		tempBoard.move(tempBoard.getLegalMoves().get(i));
	    		beta = Math.min(beta, maxAB(tempBoard, alpha, beta, depth-1));
	    		if (alpha >= beta) {
	    			return alpha;
	    		}
	    	}
	    	return beta;
    	}
    }
    
    public int eval(HusBoardState board_state) {
    	int seeds = 0;
    	int[][] noSeeds = board_state.getPits();
    	int[] myPits = noSeeds[player_id];
    	
    	for (int i = 0; i<32; i++) {
    		seeds = seeds + myPits[i];
    	}
    	return seeds;
    }
    
    public double monteCarlo(Node n) {
    	
    	HusBoardState original = (HusBoardState) n.getBoard().clone();
    	int tempHighWinLoss = 0;
    	for (int i = 0; i<original.getLegalMoves().size(); i++) {
    		HusBoardState cloned_board = (HusBoardState) original.clone();
    		cloned_board.move(cloned_board.getLegalMoves().get(i));
    		for (int j = 0; j<cloned_board.getLegalMoves().size(); j++) {
    			HusBoardState secondLevel_cloned_board = (HusBoardState) cloned_board.clone();
    			secondLevel_cloned_board.move(secondLevel_cloned_board.getLegalMoves().get(j));
    			for(int k = 0; k < 10; k++){
    	    		HusBoardState temp_cloned_board = (HusBoardState) secondLevel_cloned_board.clone();
    	    		
    			   	while (!temp_cloned_board.gameOver()) {
    			   		temp_cloned_board.move(temp_cloned_board.getRandomMove());
    			   	}
    			   	if(player_id == temp_cloned_board.getWinner()) {
    			   		n.setWin();
    			   	} else {
    			   			n.setLoss();
    			  	}
    	    	}
    		}
    	}
    	return n.getWinLoss();
    	
    	/*
    	HusBoardState original = (HusBoardState) n.getBoard().clone();
    	tempPlay:
    	for(int k = 0; k < 1000; k++){
    		HusBoardState cloned_board = (HusBoardState) original.clone();
    		
		   	while (!cloned_board.gameOver()) {
		   		cloned_board.move(cloned_board.getRandomMove());
		   	}
		   	if(player_id == cloned_board.getWinner()) {
		   		n.setWin();
		   	} else {
		   			n.setLoss();
		  	}
    	}
    	return n.getWinLoss();
    	*/
    }
    
}

    
    /** Choose moves randomly. */
//    public HusMove chooseMove(HusBoardState board_state)
//    {
        // Pick a random move from the set of legal moves.
//        ArrayList<HusMove> moves = board_state.getLegalMoves();
//        HusMove move = moves.get(rand.nextInt(moves.size()));
//        return move;
//    }
//}
//    	public HusMove chooseMove(HusBoardState board_state) {
//    	   	HusBoardState cloned_board_state_levelA = (HusBoardState) board_state.clone();
//        	HusBoardState cloned_board_state_levelB = (HusBoardState) board_state.clone();
//        	HusBoardState cloned_board_state_levelC = (HusBoardState) board_state.clone();
//        	HusBoardState cloned_board_state_levelD = (HusBoardState) board_state.clone();
//        	HusBoardState cloned_board_state_levelE = (HusBoardState) board_state.clone();
//        	
//        	int addedSeeds = 0;
//        	ArrayList<HusMove> movesA = board_state.getLegalMoves();
//        	ArrayList<HusMove> movesB;// = board_state.getLegalMoves();
//        	ArrayList<HusMove> movesC;
//        	ArrayList<HusMove> movesD;
//        	ArrayList<HusMove> movesE;
//        	
//        	HusMove bestHusMoveC = movesA.get(0);
//        	HusMove bestHusMoveB = movesA.get(0);
//        	HusMove bestHusMoveA = movesA.get(0);
//        	HusMove bestHusMoveD = movesA.get(0);
//        	HusMove bestHusMoveE = movesA.get(0);
//        	 
//        	int maxSeedsA = 0;
//        	int minSeedsB = 92;
//        	int maxSeedsC = 0;
//        	int minSeedsD = 92;
//        	int maxSeedsE = 0;
//        	
//        	int addedSeedsA = 0;
//        	int addedSeedsB = 0;
//        	int addedSeedsC = 0;
//        	int addedSeedsD = 0;
//        	int addedSeedsE = 0;
//        	
//        	int[][] pits;
//        	int[] my_pits;
//        	int[][] testPits;
//        	int[] op_pits;
//        	
//        	for(int levelA = 0; levelA < movesA.size(); levelA ++){
//        		cloned_board_state_levelA = (HusBoardState) board_state.clone();
//        		cloned_board_state_levelA.move(movesA.get(levelA));
//        		movesB = cloned_board_state_levelA.getLegalMoves();
//        	//	System.out.print("At Player 1 move ");
//        	//	System.out.println(movesA.get(levelA).getPit());
//        		minSeedsB = 92;
//        		loopB:
//        		for(int levelB = 0;levelB < movesB.size(); levelB++){
//        			//System.out.print("At player 2 move : ");
//        			//System.out.print(movesB.get(levelB).getPit());
//        			//System.out.print(" with minSeedsB : ");
//        			//System.out.println(minSeedsB);
//        			cloned_board_state_levelB = (HusBoardState) cloned_board_state_levelA.clone();
//        			cloned_board_state_levelB.move(movesB.get(levelB));
//        			movesC = cloned_board_state_levelB.getLegalMoves();
//        			maxSeedsC = 0;
//        			//bestHusMoveC = movesB.get(levelB)
//        			loopC:
//        			for(int levelC = 0; levelC < movesC.size(); levelC++){
//        				
//            			//cloned_board_state_levelC = (HusBoardState) cloned_board_state_levelB.clone();
//            			//cloned_board_state_levelC.move(movesC.get(levelC));
//            			/*addedSeedsC = 0;
//            			pits = cloned_board_state_levelC.getPits();
//            			my_pits = pits[player_id];
//            	      	for(int i = 0; i < 32; i++){
//                    		addedSeedsC = addedSeedsC + my_pits[i];
//                    	}
//            	      	*/
//            			cloned_board_state_levelC = (HusBoardState) cloned_board_state_levelB.clone();
//            			cloned_board_state_levelC.move(movesC.get(levelC));
//            			movesD = cloned_board_state_levelC.getLegalMoves();
//            			minSeedsD = 92;
//            			loopD:
//            	      	for(int levelD = 0; levelD < movesD.size(); levelD++){
//
//                			cloned_board_state_levelD = (HusBoardState) cloned_board_state_levelC.clone();
//                			cloned_board_state_levelD.move(movesD.get(levelD));
//                			maxSeedsE = 0;
//                			movesE = cloned_board_state_levelD.getLegalMoves();
//                			loopE:
//                			for(int levelE = 0; levelE < movesE.size(); levelE++){
//                	      	//	System.out.print("in level E loop: ");
//                	      	//	System.out.println(maxSeedsE);
//                	      		cloned_board_state_levelE = (HusBoardState) cloned_board_state_levelD.clone();
//                	      		cloned_board_state_levelE.move(movesE.get(levelE));
//                    			pits = cloned_board_state_levelE.getPits();
//                    			my_pits = pits[player_id];
//                    			addedSeedsE = 0;
//                    	      	for(int i = 0; i < 32; i++){
//                            		addedSeedsE = addedSeedsE + my_pits[i];
//                            	}	
//                    	      	if(addedSeedsE > maxSeedsE){
//                    	      	//	System.out.print("found a better move 5 than ");
//                    	      	//	System.out.print(maxSeedsE);
//                    	      	//	System.out.print(" it gives you : ");
//                    	      		//System.out.println(addedSeedsE);
//                    	      		bestHusMoveE = movesE.get(levelE);
//                    	      		maxSeedsE = addedSeedsE;
//                    	      	}
//                    	      	if(maxSeedsE >= minSeedsD){
//                    	      		//System.out.println("-- breaking out of loopE");
//                    	      		break loopE;
//                    	      	}  
//                    	      	
//                			}
//                			/*
//                			pits = cloned_board_state_levelD.getPits();
//                			my_pits = pits[player_id];
//                			
//                			addedSeedsD = 0;
//                	      	for(int i = 0; i < 32; i++){
//                        		addedSeedsD = addedSeedsD + my_pits[i];
//                        	}
//                	      	*/
//                	      	if(maxSeedsE < minSeedsD){
//                	  //    		System.out.print("found a better move 4 than ");
//                	  //    		System.out.print(minSeedsD);
//                	//      		System.out.print(" it gives you : ");
//                	//      		System.out.println(maxSeedsE);
//                	      		bestHusMoveD = movesD.get(levelD);
//                	      		minSeedsD = maxSeedsE;
//                	      	}
//                	      	if(minSeedsD <= maxSeedsC){
//                	      //		System.out.println("-- breaking out of loopD");
//                	      		break loopD;
//                	      	}  
//                	      	
//            	      	}
//            	      	
//            	      	if(minSeedsD > maxSeedsC){
//            	    //  		System.out.print("found a better move 3 than ");
//            	    //  		System.out.print(maxSeedsC);
//            	   //   		System.out.print(" it gives you : ");
//            	    //  		System.out.println(minSeedsD);
//            	      		bestHusMoveC = movesC.get(levelC);
//            	      		maxSeedsC = minSeedsD;
//            	      		
//            	      	}
//            	      	if(maxSeedsC >= minSeedsB){
//            //	      		System.out.println("-- breaking out of loopC");
//            	      		break loopC;
//            	      	}  	
//        			}	 
//        			/*pits = cloned_board_state_levelB.getPits();
//        			my_pits = pits[player_id];
//        			addedSeedsB = 0;
//        	      	for(int i = 0; i < 32; i++){
//                		addedSeedsB = addedSeedsB + my_pits[i];
//                	} 
//        	      	System.out.print(addedSeedsB);
//        	      	System.out.println(" seeds on your side");
//        	      	*/
//        			if(maxSeedsC < minSeedsB){
//        				minSeedsB = maxSeedsC;
//        				bestHusMoveB = movesB.get(levelB);
//        		//		System.out.print("-> move by player two of pit ");
//        		//		System.out.print(bestHusMoveB.getPit());
//        				//System.out.print(" with a number of beads ");
//        				//int[] op_pits = pits[opponent_id];
//        				//testPits = cloned_board_state_levelA.getPits();
//        				//op_pits = testPits[opponent_id];
//        				//System.out.print(op_pits[bestHusMoveB.getPit()]);
//        				       
//        		//		System.out.print(" after you make the move ");
//        		//		System.out.print(movesA.get(levelA).getPit());
//        		//		System.out.print(" gets you ");
//        		//		System.out.println(minSeedsB);
//        			}
//        			
//         	      	if(maxSeedsA >= minSeedsB){
//        	      //		System.out.println("-- breaking out of loopB");
//        	      		break loopB;
//        	      	}  
//        			
//        		}
//        		
//    			if(minSeedsB > maxSeedsA){
//    				maxSeedsA = minSeedsB;
//    				bestHusMoveA = movesA.get(levelA);
//    			//	System.out.print("BEST MOVE ALERT: If you play pit ");
//    			//	System.out.print(bestHusMoveA.getPit());
//    			//	System.out.print(" then they play ");
//    			//	System.out.print(bestHusMoveB.getPit());
//    			
//    			///	System.out.print(" you will have ");
//    			//	System.out.println(maxSeedsA);
//    			}
//        	}
//        	
//    		System.out.println(" next move will be ");
//    		System.out.println(bestHusMoveA.getPit());
//    		System.out.println(" then the move player B");
//    		System.out.println(bestHusMoveB.getPit());
//    		
//    		System.out.println(maxSeedsA);
//    		
//            return bestHusMoveA;//moves.get(chooseMin(board_state));
//        }
//        
//    }	
//    
//    
//    
  //Yantzi
  //My current studentPlayer chooseMove method. 


//  public HusMove chooseMove(HusBoardState board_state)
//      {
//          // Get the contents of the pits so we can use it to make decisions.
//          int[][] pits = board_state.getPits();
//
//          // Use ``player_id`` and ``opponent_id`` to get my pits and opponent pits.
//      	int[] my_pits = pits[player_id];
//          int[] op_pits = pits[opponent_id];
//          
//          System.out.println("Player_Id = "+player_id);
//
//          // Use code stored in ``mytools`` package.
//          MyTools.getSomething();
//          
//
//          // Get the legal moves for the current board state.
//          ArrayList<HusMove> moves = board_state.getLegalMoves();
//          
//          //BASIC VERSION THAT WAS GIVEN. 
//          HusMove move = moves.get(0);
//          
//          
//          //System.out.println("Running minimax method...");
//          /////////////////////////////////////////////
//          //V1
//          
//          //move = MinimaxDecision(moves, board_state);
//          
//          /////////////////////////////////////////////
//          //V2
//          
//          Nodes bestNode = Minimax(6, 0, board_state);
//          
//          HusMove bestMove = bestNode.getMove();
//          
//          //System.out.println("Move Chosen = " +bestMove.getPit());
//          
//          
//          //will return bestMove. 
//         
//          
//          ////////////////////////////////////////////
//          //Code Given
//
//          // We can see the effects of a move like this...
//          HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
//          cloned_board_state.move(move);
//
//          // But since this is a placeholder algorithm, we won't act on that information.
//         
//          
//          //return move;
//          
//          return bestMove;
//      }
//      
//      
//         
//      //VERSION 2.0 
//      //minimax method that finds the best move to make based on a evaluation function that looks 
//      //x levels deep, where x is a parameter (depth) passed in. 
//      
//      public Nodes Minimax(int depth, int myTurn, HusBoardState board_state) {
//      	
//      	
////      	if (myTurn == 0) System.out.println("My Turn");
////      	if (myTurn == 1) System.out.println("Opponents Turn");
//      	
//          if(depth == 0) {
//         
//          	//System.out.println("Reached depth of 0");
//          	//depth desired reached, we want to evaluate the score of the gameboard at this depth
//          
//      		
//      		//run evaluation function on board. 
//      		int score = (int) MyTools.EvaluationFunction(board_state, player_id, opponent_id);		
//  				
//  			Nodes currentNode = new Nodes();
//  			
//  			currentNode.setScore(score);
//  			
//          	return currentNode;
//          	
//          	
//          } else {
//          	
//          	//Depth desired not reached yet, depending on whose turn find min or max.
//          	
//              //Game not over so get a list of possible moves, then try each possible move. 
//          	
//          	ArrayList<HusMove> moves = board_state.getLegalMoves(); 
//          	
//          	//if (myTurn == 0) System.out.println("My Turn, find Max move from "+moves.size()+" children\n");
//          	//if (myTurn == 1) System.out.println("Opponents Turn, find Min from "+moves.size()+" children\n");
//          	
//          	//Create node for minimax tree
//          	
//  			Nodes currentNode = new Nodes();
//  			
//      		ArrayList<Integer> scores = new ArrayList<Integer>();
//
//
//          	for(int i = 0; i < moves.size(); i++){
//          		        		
//          		//list that will contain all the scores of curentNode's children
//     
//          		//clone the current board, then try the move i. 
//          		HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
//      			cloned_board_state.move(moves.get(i));
//  			
//      			if(myTurn == 0) { //maximizing function
//      				//System.out.println("My Turn find maximum. Depth = "+depth + " Current Move = "+i);
//      				
//      				//recursive call to find value at node
//      				Nodes lowerNode = new Nodes();
//      				
//      				lowerNode = Minimax((depth-1), 1, cloned_board_state);
//      				int lowerScore = lowerNode.getScore();
//      				
//      				scores.add(lowerScore);
//      				
//      				//once you are on the last possible move on that group of moves.
//  					int max = -100;
//      				
//      				if(i == (moves.size()-1)){
//      					//System.out.println(scores);
//      					//get max value in scores list
//      					for (int j = 0; j < scores.size(); j++){
//      						int current = scores.get(j);
//      						if(current > max){
//      							max = scores.get(j);
//      							//System.out.println("Max: "+max+" is move "+ j +" at depth of "+depth+".");
//      							//System.out.println("");
//      							//int value = moves.get(j).getPit();
//      							//System.out.println("Value of moves.get(j) = "+moves.get(j).getPit());
//
//      	        				currentNode.setMove(moves.get(j));
//      	        				currentNode.setScore(max);
//      						}
//      					}
//      				}
//      				
//      			} else if (myTurn == 1) { //minimizing function
//      				//System.out.println("Opponents Turn, find minimum. Depth = "+ depth+" Current Move = "+i);
//      				
//      				//recursive call to find value at node
//      				
//      				Nodes lowerNode = new Nodes();
//      				
//      				lowerNode = Minimax((depth-1), 0, cloned_board_state);
//      				int lowerScore = lowerNode.getScore();
//      				
//      				scores.add(lowerScore);
//      				
//      				//once you are on the last possible move on that group of moves.
//      				int min = 100;
//      				
//      				if(i == (moves.size()-1)){
//      					//System.out.println(scores);
//      					//get the min value in scores list
//      					for(int j = 0; j < scores.size(); j++){
//      						int current = scores.get(j);
//      						if(current < min){
//      							min = scores.get(j);
//      							//System.out.println("Min: "+min+" is move "+j+" at depth of "+depth+".");
//      							//System.out.println("");
//      							//int value = moves.get(j).getPit();
//      							//System.out.println("Value of moves.get(j) = "+moves.get(j).getPit());
//      							currentNode.setMove(moves.get(j));
//      							currentNode.setScore(min);
//      						}
//      					}
//      				}	
//      			}
//      			
//          	}
//          	return currentNode;
//          }       	
//      }
      
      
      //end of StudentPlayer
//  }

//    public HusMove chooseMove(HusBoardState board_state)
//    {
//        // Pick a random move from the set of legal moves.
//        //ArrayList<HusMove> moves = board_state.getLegalMoves();
//        //HusMove move = moves.get(rand.nextInt(moves.size()));
//    	
//    	int depth = 5;
//    	int height = 0;
//    	int[] temp = new int[2];
//    	ArrayList<HusMove> moves = board_state.getLegalMoves();
//    	temp = miniMaxTopLevel(board_state, height, depth);
//    	HusMove move = moves.get(temp[0]);
//        return move;
//    }
//    public int[] miniMaxTopLevel(HusBoardState board_state, int height, int depth) {
//
//    	ArrayList<HusMove> legalMoves = board_state.getLegalMoves();
//    	ArrayList<Integer> scores = new ArrayList<Integer>();
//    	int turn = board_state.getTurnPlayer();
//    	
//    	int[] temp = new int[2];
//    	for (int i = 0; i < legalMoves.size(); i++) {
//
//			HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
//			HusMove clone_move = legalMoves.get(i);
//			cloned_board_state.move(clone_move);
//
//			scores.add(i, miniMaxTree(turn, cloned_board_state, height+1, depth));
//			if (i == legalMoves.size() - 1) {
//				//max player's turn
//				//trying to maximize score
//				temp[0] = 0;
//				temp[1] = -10000;
//				for (int j = 0; j <scores.size(); j++) {
//					//if stored value is greater than next indexed score, keep last move
//					if (temp[1] > scores.get(j)) {
//						continue;
//					} else {
//						//stores the move which led to highest value
//						temp[0] = j;
//						//stores the score of highest value
//						temp[1] = scores.get(j);
//					}	
//				}
//			}
//    	}
//		return temp;
//    }
//
//    public int miniMaxTree(int turn, HusBoardState board_state, int height, int depth) {
//    			
//    	ArrayList<HusMove> legalMoves = board_state.getLegalMoves();
//	    ArrayList<Integer> scores = new ArrayList<Integer>();
//    	
//	    while (height != depth && legalMoves.size() != 0) {		
//		    
//	    //get all possible moves from current board state
//			for (int i = 0; i < legalMoves.size(); i++) {
//
//				HusBoardState cloned_board_state = (HusBoardState) board_state.clone();
//				HusMove clone_move = legalMoves.get(i);
//				cloned_board_state.move(clone_move);
//
//				scores.add(i, miniMaxTree(turn, cloned_board_state, height+1, depth));
//				
//				if (i == legalMoves.size() - 1) {
//					//max player's turn
//					//trying to maximize score
//					if (height%2 - 1 == 0) {
//						int[] temp = new int[2];
//						temp[0] = 0;
//						temp[1] = -10000;
//						for (int j = 0; j <scores.size(); j++) {
//							//if stored value is greater than next indexed score, keep last move
//							if (temp[1] > scores.get(j)) {
//								continue;
//							} else {
//								//stores the move which led to highest value
//								temp[0] = j;
//								//stores the score of highest value
//								temp[1] = scores.get(j);
//							}	
//						}
//						return temp[1];
//					//min player's turn
//					//trying to minimize score
//					} else {
//						int[] temp = new int[2];
//						temp[0] = 0;
//						temp[1] = 10000;
//						for (int j = 0; j <scores.size(); j++) {
//							//if stored value is greater than next indexed score, keep last move
//							if (temp[1] < scores.get(j)) {
//								continue;
//							} else {
//								//stores the move which led to highest value
//								temp[0] = j;
//								//stores the score of highest value
//								temp[1] = scores.get(j);
//							}
//						}
//						return temp[1];
//					}
//				}
//			}
//	    }
//	    return eval(board_state, turn, height);
//    }
//    
//    public int eval(HusBoardState board, int player, int height) {
//    	int[][] boardPits = board.getPits();
//    	int score = 0;
//    	for (int i = 0; i < boardPits[0].length; i++){
//        	score = score + boardPits[player][i];
//        }
//        return score;
//    }

