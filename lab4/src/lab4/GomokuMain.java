package lab4;
		
import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import labb4.gui.GomokuGUI;

public class GomokuMain {
	
	public static void main(String[] args) {
		int port = 4010;
		if (args.length != 0) {
			port = Integer.parseInt(args[0]);
		}
		
		GomokuClient gomokuClient = new GomokuClient(port);
		GomokuGameState gomokuGameState = new GomokuGameState(gomokuClient);
		GomokuGUI gomokuGUI = new GomokuGUI(gomokuGameState, gomokuClient);
		
	}
	
}
