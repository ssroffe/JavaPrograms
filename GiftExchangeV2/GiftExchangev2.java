/* Written by
   Seth Roffe
*/
package giftexchange;

import java.util.*;
import java.io.File;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Message.*;

public class GiftExchange {
    /** Performs a gift exchange pairing. Inputs include the number of people
	participating which must be greater than 2 people and the names of
	those people. The outputs are files that are named for the buyer
	containing a string that explains who that person is buying for.
    */
    public void start(Stage primaryStage) {
       UsingEmails();
    }

    public void UsingEmails() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Gift Exchange");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(25,25,25,25));

        Scene scene = new Scene(grid,600,300);
        primaryStage.setScene(scene);

        Text autoEmail = new Text("Would you like to automate the emailing?");
        grid.add(autoEmail,0,0,1,2);

        Text notice = new Text("Note: only for gmail host email");
        notice.setFill(Color.FIREBRICK);
        grid.add(notice,0,1);

        Button yes = new Button("Yes");
        Button no = new Button("No");

        HBox ynbtn = new HBox(10);
        ynbtn.setAlignment(Pos.CENTER);
        ynbtn.getChildren.addAll(yes,no);
        grid.add(ynbtn,0,2);

        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();
                EmailMethod();
            }
        });

        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();
            }
        });

    }

    public void EmailMethod() throws FileNotFoundException,AddressException,MessagingException {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> emails = new ArrayList<String>();
        
        boolean inputNames = true;
        int count = 1;

        String[] userPass = getEmailPswd();
}

    public String[] getEmailPswd() {
        String[] tmp = {"",""};
     
        GridPane grid = GridSetup();

        Text title = new Text("Please input your gmail username and password");
        grid.add(title,0,0,2,1);

        Label user = new Label("User Name:");
        grid.add(user,0,1);

        TextField userTf = new TextField();
        grid.add(userTf,1,1);

        Label pw = new Label("Password:");
        grid.add(pw,0,2);
        PasswordField pwPf = new PasswordField();
        grid.add(pwPf,1,2);

        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER);
        hb.getChildren.addAll(ok,cancel);

        grid.add(hb,0,3);


    }

    public GridPane GridSetup() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        return grid;
    }
