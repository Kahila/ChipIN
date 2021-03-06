package view;

import java.util.Arrays;

/*
 * @author Kahila kalombo
 * @version: 1.0
 * @class GuiView
 * This Class Contains only code that has to do with the user interface
 * */

import controller.Controller;
//import model.sockets.ChipINServer;

//import model.
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
//javafx imports
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class GuiView {
	private Controller controller = new Controller();
	
	private static String wall = "https://images.unsplash.com/photo-1459257831348-f0cdd359235f?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1650&q=80";
	private static Stage stage = null;
	private Scene sceneP1 = null;
	private Scene homeScene = null;
	
	private GridPane firstPane = null; // login pane
	private GridPane secondPane = null; // register pane
	private GridPane homePane = null; // landing page pane
	private Scene registerScence = null;
	private Alert alert = null;
	private boolean request = false;
	private String funds = "";
	private String[] selectFund = {};
	private int i = 0;
	private TextField username;
	private int amountInWallet;
	@SuppressWarnings("unused")
	private boolean transected = false;
	private int tr = 0;
	
	private final Spinner<Integer> spinner = new Spinner<Integer>();
	private final int initialValue = 1000;
	private boolean connected = false;
	
	private TextArea ta = null;
	private Label lpassword = new Label("Password:");
	private PasswordField  password = new PasswordField();
	
	private String clientUsername = "";
	
	//Parameterized constructor
		public GuiView(Stage stage1) {
			stage = stage1;
			setUpUI();
		}
		
		//method for setting up UI
		public void setUpUI() {
			stage.setTitle("ChipIN");
			
			register();
			stage.show();
		}
		
		//start of landing page
		//first page of GUI (landing page)
		private void fund() {
			firstPane = new GridPane();
			sceneP1 = new Scene(firstPane, 1500, 800);
			Label lhistory = new Label("transection history:");
			TextArea history = new TextArea();
			Button rfund = new Button("fund");
			Button logout = new Button("exit");
			 
			 logout.setOnAction(value ->{
				 controller.getClient().clientInput(clientUsername + ",exit");
				 System.exit(-1);
			 });
			 
			 Label amount = new Label("amount:");
			 SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, amountInWallet, initialValue);
			 spinner.setValueFactory(valueFactory);
			 
			 Label lclients = new Label("Clients:");
			 TextArea clients = new TextArea();
			 
			 firstPane.setAlignment(Pos.CENTER);
			 history.setDisable(true);
			 
//			 ComboBox<String> tfID = new ComboBox<String>(FXCollections.observableArrayList(selectFund));
			 ComboBox<String> tfID = new ComboBox<String>();
			 
			 firstPane.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				 @Override
				 public void handle(MouseEvent mouseEvent) {
					 controller.getClient().clientInput("his");
					 controller.getClient().clientInput("client");
					 controller.getClient().clientInput("get");
					 clients.setText((controller.getClient().getClients()));
					 funds = (controller.getClient().getFunding());
//					 history.appendText(funds);
					 selectFund = funds.split(",");
					 
					 while( i < selectFund.length) {
						 tfID.getItems().add(selectFund[i]);
						 i++;
					 }
					 
				    history.setText(controller.getClient().getTransection());
				 }
			 });
			
			 //action for funding
			 rfund.setOnAction(value ->{
				 if (tfID.getSelectionModel().getSelectedItem() != null) {
					 controller.getClient().clientInput(tfID.getSelectionModel().getSelectedItem() + "," + username.getText() +","+ spinner.getValue() +",send");
					 transected = true;					 
				 }
				 else {
					 alert.setAlertType(AlertType.ERROR);
						alert.setTitle("ERROR");
						alert.setContentText("Please select person to fund, if available");
						alert.show();
				 }
			 });
			 
			clients.setDisable(true);
			clients.setStyle("-fx-text-fill: #4cd137;");
			
			clients.setPrefWidth(300);
			clients.setPrefHeight(400);
			history.setPrefWidth(300);
			history.setPrefHeight(400);
			logout.setPrefWidth(300);
			logout.setPrefHeight(50);
			rfund.setPrefWidth(300);
			rfund.setPrefHeight(50);
			spinner.setPrefWidth(300);
			spinner.setPrefHeight(50);
			tfID.setPrefWidth(300);
			tfID.setPrefHeight(50);
			
			firstPane.setStyle("-fx-background-image: url('"+wall+"'); " + "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");
			lhistory.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			lclients.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			amount.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			
			firstPane.add(lhistory, 0, 0);
			firstPane.add(history, 0, 1);
			firstPane.add(lclients, 1, 0);
			firstPane.add(clients, 1, 1);
			firstPane.add(spinner, 1, 2);
			firstPane.add(rfund, 1, 3);
			firstPane.add(tfID, 0, 2);
			firstPane.add(logout, 0, 3);
			GridPane.setMargin(clients, new Insets(0, 10, 10, 20));
			GridPane.setMargin(lclients, new Insets(0, 0, 0, 20));
			GridPane.setMargin(history, new Insets(0, 10, 10, 20));
			GridPane.setMargin(lhistory, new Insets(0, 0, 0, 20));
			GridPane.setMargin(rfund, new Insets(10, 0, 0, 20));
			GridPane.setMargin(logout, new Insets(10, 0, 0, 20));
			GridPane.setMargin(tfID, new Insets(10, 0, 0, 20));
			GridPane.setMargin(amount, new Insets(0, 0, 0, 20));
			GridPane.setMargin(spinner, new Insets(0, 0, 0, 20));
			
//			clients.appendText(controller.getClient().getClients());
			
			stage.setScene(sceneP1);
		} // end of landing page
		
		// register page start
		private void register() {
			secondPane = new GridPane();
			registerScence = new Scene(secondPane, 1500, 800);
			
			SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 500000, initialValue);
			spinner.setValueFactory(valueFactory);
			amountInWallet = valueFactory.getValue();
			
			
			alert = new Alert(AlertType.NONE);
			
			Label lname = new Label("Username:");
			Label lwallet = new Label("Wallet Amount:");
			Label ltf = new Label("Account Type:");
			username = new TextField();
			Button register = new Button("submit");
			Button login = new Button("exit");
			String accountType[] = {"Fund Someone", "Get Funding"};
			
			ta = new TextArea();
			ta.setDisable(true);
			ta.setStyle("-fx-text-fill: #4cd137;");
			username.setFont(Font.font("Verdana", FontWeight.NORMAL, 12));
			ComboBox<String> tfID = new ComboBox<String>(FXCollections.observableArrayList(accountType));
			
			
			// checking if connected
			//attempting to connect to server
			if (!connected && (connected = controller.connectToServer()) || connected) {
				ta.appendText("Connected to the ChipIN server\n");
				ta.setStyle("-fx-font-weight: bold; -fx-text-fill: green;");
			}else {
				ta.appendText("Not connected to the ChipIN server\n");
				ta.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
			}
			
			login.setPrefWidth(300);
			login.setPrefHeight(50);
			tfID.setPrefWidth(300);
			tfID.setPrefHeight(50);
			register.setPrefWidth(300);
			register.setPrefHeight(50);
			username.setPrefWidth(300);
			username.setPrefHeight(50);
			password.setPrefWidth(300);
			password.setPrefHeight(50);
			spinner.setPrefWidth(300);
			spinner.setPrefHeight(50);
			ta.setPrefWidth(300);
			ta.setPrefHeight(50);

			login.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			register.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			lname.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			lpassword.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			lwallet.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			ltf.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			secondPane.setStyle("-fx-background-image: url('"+wall+"'); " + "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");
			secondPane.setAlignment(Pos.CENTER);
			
			secondPane.add(lname, 0, 0);
			secondPane.add(username, 0, 1);
			secondPane.add(lpassword, 1, 0);
			secondPane.add(password, 1, 1);
			secondPane.add(lwallet, 0, 2);
			secondPane.add(spinner, 0, 3);
			secondPane.add(ltf, 1, 2);
			secondPane.add(tfID, 1, 3);
			secondPane.add(register, 0, 6);
			secondPane.add(login, 1, 6);
			secondPane.add(ta, 0, 8);
			
			GridPane.setMargin(username, new Insets(0, 0, 10, 0));
			GridPane.setMargin(login, new Insets(0, 0, 10, 5));
			GridPane.setMargin(register, new Insets(0, 0, 10, 0));
			GridPane.setMargin(password, new Insets(0, 0, 10, 5));
			GridPane.setMargin(lpassword, new Insets(0, 0, 0, 5));
			GridPane.setMargin(spinner, new Insets(0, 0, 10, 0));
			GridPane.setMargin(tfID, new Insets(0, 0, 10, 5));
			GridPane.setMargin(ltf, new Insets(0, 0, 0, 5));
			GridPane.setMargin(ta, new Insets(0, 0, 10, 0));
			
			//redirect to register
			login.setOnAction(value ->{
				System.exit(-1);
			});
			
			register.setOnAction(value ->{
				if (connected == true) {
					if (password.getText().length() > 6  && username.getText().length() > 4) {
						if (tfID.getValue() == null) {
							alert.setAlertType(AlertType.ERROR);
							alert.setTitle("ERROR");
							alert.setContentText("please select account type");
							alert.show();
						}else { // take to funding request page
							if (controller.getClient().clientInput(username.getText()+","+password.getText()+","+spinner.getValue()+","+tfID.getValue()+ ",register") && tfID.getValue() == "Get Funding") {
								clientUsername = username.getText();
								homePage();
							}else {
								fund();
							}
						}
					}else {
						alert.setAlertType(AlertType.ERROR);
						alert.setTitle("ERROR");
						alert.setContentText("Make sure that the password is 7 charectors and above \nand username 5 charectors and above");
						alert.show();
					}
					
				} else {
					alert.setAlertType(AlertType.ERROR);
					alert.setTitle("ERROR");
					alert.setContentText("Error connecting to the server\nPlease make Sure The Server Is Running");
					alert.show();
				}
			});
			
			stage.setScene(registerScence);
		} // end of register page
		
		//start home page view
		private void homePage() {
			homePane = new GridPane();
			homeScene = new Scene(homePane, 1500, 800);
			Label lhistory = new Label("transection history:");
			TextArea history = new TextArea();
			Label lfund = new Label("request funding (details):");
			TextArea fund = new TextArea();
			Button logout = new Button("Exit");
			Button rfund = new Button("request funding");
			ProgressBar progressBar = new ProgressBar(0);
			Label lclients = new Label("Clients:");
			TextArea clients = new TextArea();
			progressBar.setProgress(0.0);
			
			Label amount = new Label("amount:");
			SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 500000, initialValue);
			spinner.setValueFactory(valueFactory);
			
			//setting event handler
			
			homeScene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			    @Override
			    public void handle(MouseEvent mouseEvent) {
			    	controller.getClient().clientInput("client");
			    	clients.setText((controller.getClient().getClients()));
			    	
			    	controller.getClient().clientInput("his");
			    	 if (controller.getClient().getTransection().length() > 0) {
			    		 String[] split = controller.getClient().getTransection().trim().split(",");
			    		 history.setText(controller.getClient().getTransection());
			    		 System.out.println("------------"+Arrays.toString(split));
			    		 progressBar.setProgress(tr++);			    		 
			    	 }
			    }
			});
			
			/// request find submit 
			rfund.setOnAction(value ->{
				if (!request && fund.getText().length() > 10) {
					fund.setDisable(true);
					spinner.setDisable(true);
					controller.getClient().clientInput(clientUsername + "," + fund.getText() +","+ spinner.getValue() +",qry");
					request = true;
				}else {
					alert.setAlertType(AlertType.ERROR);
					alert.setTitle("ERROR");
					alert.setContentText("Pleas make sure that the reason for funding is longer than 10 charectors");
					alert.show();
				}
			});
			
			logout.setOnAction(value ->{
				controller.getClient().clientInput(clientUsername + ",exit");
				System.exit(-1);
			});
			
			homePane.setAlignment(Pos.CENTER);
			
			history.setDisable(true);
			history.setStyle("-fx-text-fill: #4cd137;");
			
			clients.setDisable(true);
			clients.setStyle("-fx-text-fill: #4cd137;");
			
			fund.setPrefWidth(300);
			fund.setPrefHeight(400);
			clients.setPrefWidth(300);
			clients.setPrefHeight(400);
			history.setPrefWidth(300);
			history.setPrefHeight(400);
			logout.setPrefWidth(300);
			logout.setPrefHeight(50);
			rfund.setPrefWidth(300);
			rfund.setPrefHeight(50);
			progressBar.setPrefWidth(300);
			progressBar.setPrefHeight(50);
			spinner.setPrefWidth(300);
			spinner.setPrefHeight(50);
			
			homePane.setStyle("-fx-background-image: url('"+wall+"'); " + "-fx-background-position: center center; " + "-fx-background-repeat: stretch;");
			lhistory.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			lclients.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			lfund.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			amount.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
			
			homePane.add(lhistory, 0, 0);
			homePane.add(history, 0, 1);
			homePane.add(lclients, 1, 0);
			homePane.add(clients, 1, 1);
//			homePane.add(progressBar, 0, 2);
			homePane.add(lfund, 2, 0);
			homePane.add(fund, 2, 1);
//			homePane.add(amount, 2, 2);
			homePane.add(spinner, 2, 2);
			homePane.add(rfund, 2, 3);
			homePane.add(logout, 0, 3);
			GridPane.setMargin(clients, new Insets(0, 10, 10, 20));
			GridPane.setMargin(lclients, new Insets(0, 0, 0, 20));
			GridPane.setMargin(fund, new Insets(0, 10, 10, 20));
			GridPane.setMargin(lfund, new Insets(0, 0, 0, 20));
			GridPane.setMargin(history, new Insets(0, 10, 10, 20));
			GridPane.setMargin(lhistory, new Insets(0, 0, 0, 20));
			GridPane.setMargin(logout, new Insets(20, 0, 0, 20));
			GridPane.setMargin(rfund, new Insets(0, 0, 0, 20));
			GridPane.setMargin(amount, new Insets(0, 0, 0, 20));
			GridPane.setMargin(spinner, new Insets(0, 0, 0, 20));
			GridPane.setMargin(progressBar, new Insets(0, 0, 0, 20));
			
//			clients.appendText(controller.getClient().getClients());
			
			stage.setScene(homeScene);
		} // end home page
		
}
