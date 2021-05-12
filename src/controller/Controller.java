/**
 * controller class that communicates with the view and the model
 * <br>
 * @author kahila Kalombo <218095095@student.uj.ac.za>
 */

package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.GuiView;

import model.sockets.ClientSocket;

public class Controller extends Application{
	private ClientSocket clientSocket = new ClientSocket();
	
	// connecting the client to the server
	public boolean connectToServer() {
		return (clientSocket.getConnected());
	}
	
	public ClientSocket getClient() {
		return (clientSocket);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		new GuiView(primaryStage);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
