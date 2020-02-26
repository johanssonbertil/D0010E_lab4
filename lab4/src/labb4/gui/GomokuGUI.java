package labb4.gui;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;
import lab4.gui.ConnectionWindow;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	private JFrame frame;
	private GamePanel gameGridPanel;
	private JLabel messageLabel;
	private JButton connectButton, newGameButton, disconnectButton;
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		gameGridPanel = new GamePanel(g.getGameGrid());
		messageLabel = new JLabel();
		
		connectButton = new JButton();
		connectButton.setText("Connect");
		newGameButton = new JButton();
		newGameButton.setText("New Game");
		disconnectButton = new JButton();
		disconnectButton.setText("Disconnect");
		
		Container contentPane = frame.getContentPane();
		SpringLayout layout = new SpringLayout();
		contentPane.setLayout(layout);
		
		contentPane.add(gameGridPanel);
		contentPane.add(messageLabel);
		contentPane.add(connectButton);
		contentPane.add(newGameButton);
		contentPane.add(disconnectButton);
		
		layout.putConstraint(SpringLayout.NORTH, gameGridPanel, 6, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, gameGridPanel, 6, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.EAST, gameGridPanel, 6, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, messageLabel, 6, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, messageLabel, 6, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, connectButton, 6, SpringLayout.NORTH, messageLabel);
		layout.putConstraint(SpringLayout.WEST, connectButton, 6, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.NORTH, connectButton, 6, SpringLayout.SOUTH, gameGridPanel);
		layout.putConstraint(SpringLayout.WEST, newGameButton, 6, SpringLayout.EAST, connectButton);
		layout.putConstraint(SpringLayout.NORTH, newGameButton, 6, SpringLayout.SOUTH, gameGridPanel);
		layout.putConstraint(SpringLayout.WEST, disconnectButton, 6, SpringLayout.EAST, newGameButton);
		layout.putConstraint(SpringLayout.NORTH, disconnectButton, 6, SpringLayout.SOUTH, gameGridPanel);
		
		frame.pack();
		frame.setVisible(true);
		
		
		
		gameGridPanel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				int[] mousePosition;
				mousePosition = gameGridPanel.getGridPosition(e.getX(), e.getY());
				gamestate.move(mousePosition[0], mousePosition[1]);
			}
			
		});
		connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ConnectionWindow cw = new ConnectionWindow(c);
			}
			
		});
		newGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gamestate.newGame();
			}
			
		});
		disconnectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gamestate.disconnect();
			}
			
		});
	}
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}