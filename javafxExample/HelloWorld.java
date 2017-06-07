 
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
 
public class HelloWorld extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
	primaryStage.setTitle("JavaFx welcome");

	GridPane grid = new GridPane();
	grid.setAlignment(Pos.CENTER);
	grid.setHgap(10);
	grid.setVgap(10);
	grid.setPadding(new Insets(25,25,25,25));
	
	Scene scene = new Scene(grid, 300, 275);
	primaryStage.setScene(scene);

	scene.getStylesheets().add(this.getClass().getResource("Login.css").toExternalForm());
	
	Text scenetitle = new Text("Welcome");
	grid.add(scenetitle,0,0,2,1);

	Label user = new Label("User Name:");
	grid.add(user,0,1);

	TextField userText = new TextField();
	grid.add(userText,1,1);

	Label pw = new Label("Password:");
	grid.add(pw,0,2);
	PasswordField pwBox = new PasswordField();
	grid.add(pwBox,1,2);

	Button btn = new Button("Sign in");
	HBox hbBtn = new HBox(10);
	hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
	hbBtn.getChildren().add(btn);
	grid.add(hbBtn,1,4);

	final Text target = new Text();
	grid.add(target,1,6);
	
	btn.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent e) {
		    target.setFill(Color.FIREBRICK);
		    target.setText("Sign in button pressed");
		}
	    });

	scenetitle.setId("welcome-text");
	target.setId("actiontarget");
	
	primaryStage.show();
    }
}
