package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid. Moves pieces and checks the win condition. 
 */

public class GameGrid extends Observable{
	
	private final int EMPTY = 0;
	public final int ME = 1;
	public final int OTHER = 2;
	
	private int size;
	private int[][] grid;
	private final int INROW = 5;
	
	/**
	 * Constructor
	 * Sets the size of the playing field, the parameter sets the length of both sides of the board.
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		this.size = size;
		grid = new int[size][size];
		clearGrid();
	}
	
	/**
	 * Reads a location of the grid
	 * This can return 0, 1, or 2. Which represents, EMPTY, ME, and OTHER respectively. 
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return grid[x][y];	
	}
	
	/**
	 * Returns the size of the side of the grid.
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player){
		if ( grid[x][y] == EMPTY ) {
			grid[x][y] = player;
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
	}
	
	/**
	 * Clears the grid of pieces
	 * The whole grid array is set to 0, which represents EMPTY.
	 */
	public void clearGrid(){
		for (int i = 0; i < size; i++) {
			for (int k = 0; k < size; k++) {
				grid[i][k] = EMPTY;
			}
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Check if a player has won. The win condition is that a player has 
	 * as many pieces in a row as defined by final integer INROW.
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if ( grid[x][y] == player ) {
					if ( x+INROW-1 < size) {
						int isPlayer = 1;
						for (int i = 1; i < INROW; i++) {
							if( grid[x+i][y] == player ) {
								isPlayer++;
							}
						}
						if ( isPlayer == INROW ) {
							return true;
						}
					}
					if ( y+INROW-1 < size) {
						int isPlayer = 1;
						for (int i = 1; i < INROW; i++) {
							if( grid[x][y+i] == player ) {
								isPlayer++;
							}
						}
						if ( isPlayer == INROW ) {
							return true;
						}
					}
					if ( (x+INROW-1 < size) && (y+INROW-1 < size)) {
						int isPlayer = 1;
						for (int i = 1; i < INROW; i++) {
							if( grid[x+i][y+i] == player ) {
								isPlayer++;
							}
						}
						if ( isPlayer == INROW ) {
							return true;
						}
					}
					if ( (x-INROW+1 >= 0) && (y+INROW-1 < size)) {
						int isPlayer = 1;
						for (int i = 1; i < INROW; i++) {
							if( grid[x-i][y+i] == player ) {
								isPlayer++;
							}
						}
						if ( isPlayer == INROW ) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	
}



