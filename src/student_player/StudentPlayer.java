package student_player;

import hus.HusBoardState;
import hus.HusPlayer;
import hus.HusMove;

import java.util.ArrayList;
import java.util.Random;
import java.lang.System;


import student_player.node.Node;

/** A Hus player submitted by a student. */
public class StudentPlayer extends HusPlayer {

    /** You must modify this constructor to return your student number.
     * This is important, because this is what the code that runs the
     * competition uses to associate you with your agent.
     * The constructor should do nothing else. */
    public StudentPlayer() { super("260513906"); }
    Random rand = new Random();
    /** This is the primary method that you need to implement.
     * The ``board_state`` object contains the current state of the game,
     * which your agent can use to make decisions. See the class hus.RandomHusPlayer
     * for another example agent. */
    public HusMove chooseMove(HusBoardState board_state)
    {
    	long startTime = System.nanoTime();
    	System.out.println("Turn Number: "+board_state.getTurnNumber());
    	if (board_state.getTurnNumber() == 0) {
    		Node startNode = new Node(board_state);
    		startNode = callMiniMaxAB(board_state, -10000, 10000, 7, startNode);
    		int tempMove = 0;
	    	int tempHighScore = -10;
	    	for (int i=0; i<startNode.numChildren(); i++) {
	    		if (tempHighScore < startNode.getChildAt(i).getScore()) {
	    			tempHighScore = startNode.getChildAt(i).getScore();
	    			tempMove = i;
	    		}
	    	}
	    	HusMove move = board_state.getLegalMoves().get(tempMove);
    		//HusMove move = board_state.getLegalMoves().get(6);
	    	//System.out.println("Time left over: "+(double)(System.nanoTime() - startTime)/1000000000);
	    	return move;
    	}
    	else {
    	int seed = eval(board_state);
    	System.out.println("Seed count: "+seed);
	    	if (44 < seed && seed <= 51) {
		    	Node root = new Node(board_state);
		    	System.out.println("MyLegalMoves: "+board_state.getLegalMoves().size());
		    	root = callMiniMaxAB(board_state, -10000, 10000, 6, root);
		    	int tempMove = 0;
		    	int tempHighScore = -10;
		    	for (int i=0; i<root.numChildren(); i++) {
		    		if (tempHighScore < root.getChildAt(i).getScore()) {
		    			tempHighScore = root.getChildAt(i).getScore();
		    			tempMove = i;
		    		}
		    	}
		    	HusMove move = board_state.getLegalMoves().get(tempMove);
		    	System.out.println("Time left over: "+(double)(2000000000 - (System.nanoTime() - startTime))/1000000000);		    	return move;
	    	} else if ((51 < seed && seed <= 70) || (35 < seed && seed <= 44)) {
	    		Node startNode = new Node(board_state);
	    		System.out.println("MyLegalMoves: "+board_state.getLegalMoves().size());
	    		startNode = callMiniMaxAB(board_state, -10000, 10000, 7, startNode);
	    		int tempMove = 0;
		    	int tempHighScore = -10;
		    	for (int i=0; i<startNode.numChildren(); i++) {
		    		if (tempHighScore < startNode.getChildAt(i).getScore()) {
		    			tempHighScore = startNode.getChildAt(i).getScore();
		    			tempMove = i;
		    			
		    		}
		    	}
		    	HusMove move = board_state.getLegalMoves().get(tempMove);
		    	System.out.println("Time left over: "+(double)(2000000000 - (System.nanoTime() - startTime))/1000000000);
		    	return move;
	    	} else if ((70 < seed && seed <= 80) || (15 <= seed && seed <= 35)) {
	    		Node startNode = new Node(board_state);
	    		System.out.println("MyLegalMoves: "+board_state.getLegalMoves().size());
	    		startNode = callMiniMaxAB(board_state, -10000, 10000, 8, startNode);
	    		int tempMove = 0;
		    	int tempHighScore = -10;
		    	for (int i=0; i<startNode.numChildren(); i++) {
		    		if (tempHighScore < startNode.getChildAt(i).getScore()) {
		    			tempHighScore = startNode.getChildAt(i).getScore();
		    			tempMove = i;
		    		}
		    	}
		    	HusMove move = board_state.getLegalMoves().get(tempMove);
		    	System.out.println("Time left over: "+(double)(2000000000 - (System.nanoTime() - startTime))/1000000000);		    	return move;
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
    
    
    //Null Move
    public int nullMove(HusBoardState board_state, int alpha, int beta, int depth) {
    	if (depth == 3) {
    		board_state.move(null);
    		int nullScore = nullMove(board_state, alpha, beta, depth-1);
    		return nullScore;
    	} else {
    		for (int i = 0; i < board_state.getLegalMoves().size(); i++) {
    			HusBoardState cloneBoard = (HusBoardState) board_state.clone();
    			cloneBoard.move(cloneBoard.getLegalMoves().get(i));
    			alpha = Math.max(alpha, minAB(cloneBoard, alpha, beta, depth-1));
	    		if (alpha >= beta) {
	    			return beta;
	    		}
	    	}
	    	return alpha;
    	}
    }
    
    public Node callMiniMaxAB(HusBoardState board_state, int alpha, int beta, int depth, Node root) {
    	for(int i = 0; i < board_state.getLegalMoves().size(); i++) {
    		//System.out.println("Time left over: "+(double)(System.nanoTime() - startTime)/1000000000);
    		HusBoardState tempBoard = (HusBoardState) board_state.clone();
    		tempBoard.move(board_state.getLegalMoves().get(i));
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
	    		//null move pruning
//	    		int nullScore = nullMove(tempBoard, alpha, beta, 3);
//	    		if (nullScore >= beta) {
//	    			return beta;
//	    		}
//	    		if (nullScore > alpha) {
//	    			alpha = nullScore;
//	    		}
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
