package labb4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{

	private final int UNIT_SIZE = 20;
	private GameGrid grid;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y){
		return new int[] {(int) Math.floor(x/UNIT_SIZE), (int) Math.floor(y/UNIT_SIZE)};
	}
	
	/**
	 * Returns unit size, for Jframe good-looking extravaganza.
	 * @return an integer, unit size.
	 */
	int getUnitSize() {
		return UNIT_SIZE;
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for (int i = 0; i < grid.getSize(); i++) {
			for (int k = 0; k < grid.getSize(); k++) {
				g.drawRect(i*UNIT_SIZE, k*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
			}
		}
		for (int i = 0; i == grid.getSize(); i++) {
			for (int k = 0; k == grid.getSize(); i++) {
				if (grid.getLocation(i, k) == grid.ME) {
					g.setColor(Color.BLUE);
					g.drawOval(i*UNIT_SIZE, k*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
					System.out.println("MEMEME");
				}
				else if (grid.getLocation(i, k) == grid.OTHER) {
					g.setColor(Color.BLUE);
					g.drawOval(i*UNIT_SIZE, k*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				}
			}
		}
	}
	
}