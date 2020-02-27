/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;
	
    //Possible game states
	private final int NOT_STARTED = 0;
	private final int MY_TURN = 1;
	private final int OTHERS_TURN = 2;
	private final int FINISHED = 3;
	private int currentState;
	
	private GomokuClient client;
	
	private String message;
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 * Sets current state as 0 in other words not started.
	 * Creates a new GameGrid
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString(){return message;}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){return gameGrid;}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate as an index in the grid array.
	 * @param y the y coordinate as an index in the grid array.
	 * 
	 * Has a switch depending on currentState value.
	 * has 4 cases , NOT_STARTED , OTHERS_TURN , FINISHED , MY_TURN.
	 * 
	 * Only if its my turn a move is made.
	 */
	public void move(int x, int y){
		switch (currentState) {
		case NOT_STARTED:
			message = "Game not started";
			refresh();
			break;
		case OTHERS_TURN:
			message = "Others turn";
			refresh();
			break;
		case FINISHED:
			message = "Game already finished";
			refresh();
			break;
		case MY_TURN:
			if ( gameGrid.move(x, y, gameGrid.ME) ) { 
				client.sendMoveMessage(x, y);
				message = "Moved successfully";
				currentState = OTHERS_TURN;
				if (gameGrid.isWinner(gameGrid.ME)) {
					message = "YOU WON!";
					currentState = FINISHED;
				}
			} else {
				message = "Can't move there.";
			}
			refresh();
			break;
		}
	}
	
	/**
	 * Starts a new game with the current client
	 * Sets currentstate as OTHERS TURN,
	 * Clears the grid
	 */
	public void newGame(){
		currentState = OTHERS_TURN;
		gameGrid.clearGrid();
		message = "New game started, opponent go first.";
		client.sendNewGameMessage();
		refresh();
	}
	
	/**
	 * Other player has requested a new game, so the game state is changed
	 * accordingly
	 */
	public void receivedNewGame(){
		currentState = MY_TURN;
		gameGrid.clearGrid();
		message = "New game started, you go first.";
		refresh();
	}
	
	/**
	 * The connection to the other player is lost, so the game is interrupted
	 */
	public void otherGuyLeft(){
		currentState = NOT_STARTED;
		gameGrid.clearGrid();
		message = "Other gut left";
		refresh();
	}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect(){
		currentState = NOT_STARTED;
		gameGrid.clearGrid();
		message = "You disconnected";
		client.disconnect();
		refresh();
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x the x coordinate as an index in the grid array.
	 * @param y the y coordinate as an index in the grid array.
	 */
	public void receivedMove(int x, int y){
		gameGrid.move(x, y, gameGrid.OTHER);
		if ( gameGrid.isWinner(gameGrid.OTHER) ) {
			message = "You Lose";
			currentState = FINISHED;
		} else {
			currentState = MY_TURN;
			message = "Your turn";
		}
		refresh();
	}
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHERS_TURN;
			break;
		}
		setChanged();
		notifyObservers();
		
	}
	
	/**
	 * Notifies observers that a change has been made.
	 */
	
	private void refresh() {
		setChanged();
		notifyObservers();
	}
	
}