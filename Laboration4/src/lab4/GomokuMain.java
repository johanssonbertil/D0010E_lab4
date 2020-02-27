package lab4;
		
import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {
	
	public static int port;
	
	public static void main(String[] args) {
		port = 4010;
		if (args.length != 0) {
			port = Integer.parseInt(args[0]);
		}
		
		GomokuClient gomokuClient = new GomokuClient(port);
		GomokuGameState gomokuGameState = new GomokuGameState(gomokuClient);
		GomokuGUI gomokuGUI = new GomokuGUI(gomokuGameState, gomokuClient);
		
	}
	
}
