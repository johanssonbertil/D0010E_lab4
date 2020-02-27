package lab4.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board.
 * Also responsible for converting pixel coordinates to an index in the grid array from GameGrid.
 */

public class GamePanel extends JPanel implements Observer{

	private final int UNIT_SIZE = 20;
	private GameGrid grid;
	
	/**
	 * The constructor
	 * Sets dimensions.
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
	
	/**
	 * Paints the board and add crosses and circles where necessary.
	 * 
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for (int i = 0; i < grid.getSize(); i++) {
			for (int k = 0; k < grid.getSize(); k++) {
				g.drawRect(i*UNIT_SIZE, k*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
			}
		}
//		int u = 2, p=2;
//		g.drawOval(u*UNIT_SIZE+1, p*UNIT_SIZE+1, UNIT_SIZE-2, UNIT_SIZE-2);
//		g.drawOval(u*UNIT_SIZE+2, p*UNIT_SIZE+2, UNIT_SIZE-4, UNIT_SIZE-4);
//		int o = 1, q=1;
//		g.drawLine(o*UNIT_SIZE+1, q*UNIT_SIZE+1, (o+1)*UNIT_SIZE-2, (q+1)*UNIT_SIZE-2);
//		g.drawLine(o*UNIT_SIZE+2, q*UNIT_SIZE+1, (o+1)*UNIT_SIZE-1, (q+1)*UNIT_SIZE-2);
//		g.drawLine(o*UNIT_SIZE+1, (q+1)*UNIT_SIZE-1, (o+1)*UNIT_SIZE-2, q*UNIT_SIZE+2);
//		g.drawLine(o*UNIT_SIZE+2, (q+1)*UNIT_SIZE-1, (o+1)*UNIT_SIZE-1, q*UNIT_SIZE+2);
//		g.drawLine(o*UNIT_SIZE+3, (q+1)*UNIT_SIZE-1, (o+1)*UNIT_SIZE, q*UNIT_SIZE+2);
		g.setColor(Color.BLACK);
		for (int i = 0; i < grid.getSize(); i++) {
			for (int k = 0; k < grid.getSize(); k++) {
				if (grid.getLocation(i, k) == grid.ME) {
					g.drawOval(i*UNIT_SIZE+1, k*UNIT_SIZE+1, UNIT_SIZE-2, UNIT_SIZE-2);
					g.drawOval(i*UNIT_SIZE+2, k*UNIT_SIZE+2, UNIT_SIZE-4, UNIT_SIZE-4);
//					g.setColor(Color.BLUE);
//					g.fillOval(i*UNIT_SIZE, k*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				}
				else if (grid.getLocation(i, k) == grid.OTHER) {
					g.drawLine(i*UNIT_SIZE+1, k*UNIT_SIZE+1, (i+1)*UNIT_SIZE-2, (k+1)*UNIT_SIZE-2);
					g.drawLine(i*UNIT_SIZE+2, k*UNIT_SIZE+1, (i+1)*UNIT_SIZE-1, (k+1)*UNIT_SIZE-2);
					
					
					g.drawLine(i*UNIT_SIZE+1, (k+1)*UNIT_SIZE-1, (i+1)*UNIT_SIZE-2, k*UNIT_SIZE+2);
					g.drawLine(i*UNIT_SIZE+2, (k+1)*UNIT_SIZE-1, (i+1)*UNIT_SIZE-1, k*UNIT_SIZE+2);
					g.drawLine(i*UNIT_SIZE+3, (k+1)*UNIT_SIZE-1, (i+1)*UNIT_SIZE, k*UNIT_SIZE+2);
//					g.setColor(Color.RED);
//					g.fillOval(i*UNIT_SIZE, k*UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				}
			}
		}
	}
	
}