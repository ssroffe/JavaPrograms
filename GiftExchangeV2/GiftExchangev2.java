/* Written by
   Seth Roffe
*/
//package giftexchange;

import java.util.*;
import java.io.*;
import java.util.regex.*;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.stage.Stage;
import javafx.geometry.*;
//import javax.mail.*;
//import javax.mail.internet.*;
//import javax.mail.Message.*;

public class GiftExchangev2 extends Application {
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

        GridPane grid = GridSetup();

        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);

        Text autoEmail = new Text("Would you like to automate the emailing?");
        grid.add(autoEmail,0,0,1,2);

        Text notice = new Text("Note: only for gmail host email");
        notice.setFill(Color.FIREBRICK);
        grid.add(notice,0,2);

        Button yes = new Button("Yes");
        Button no = new Button("No");

        HBox ynbtn = new HBox(10);
        ynbtn.setAlignment(Pos.CENTER);
        ynbtn.getChildren().addAll(yes,no);
        grid.add(ynbtn,0,3); 
        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();
                getEmailPswd();
            }
        });

        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();
            }
        });
        primaryStage.show();
    }

    public void getEmailPswd() {
        Stage stage = new Stage();
        stage.setTitle("Enter Email username and password");

        GridPane grid = GridSetup();
        
        Scene scene = new Scene(grid);
        stage.setScene(scene);

        Text title = new Text("Please input your gmail username and password");
        grid.add(title,0,0,2,1);

        Label userName = new Label("User Name:");
        grid.add(userName,0,1);

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
        hb.getChildren().addAll(ok,cancel);

        grid.add(hb,1,3);

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Text errMessage = new Text();
                errMessage.setFill(Color.FIREBRICK);
                grid.add(errMessage,1,4);
                if (userTf.getText().length() == 0 || !isValidEmail(userTf.getText())) {
                    errMessage.setText("You need to input a valid email address!");
                }
                else if (pwPf.getText().length() == 0) {
                    errMessage.setText("You need to input a password!");
                }
                else {
                    GetPartyEmail(userTf.getText(),pwPf.getText());
                    stage.close();
                }
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
                UsingEmails();
            }
        });
                    
        stage.show();

    }

    public void GetPartyEmail(String userName, String password) {

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> emails = new ArrayList<String>();


        Stage stage = new Stage();
        GridPane grid = GridSetup();

        Scene scene = new Scene(grid);
        
        int count = 1;

        final ScrollBar sc = new ScrollBar();

        final VBox vb = new VBox();

        vb.setLayoutX(5);
        vb.setSpacing(10);

        sc.setLayoutX(scene.getWidth()-sc.getWidth());
        sc.setMin(0);
        sc.setOrientation(Orientation.VERTICAL);
        sc.setPrefHeight(180);
        sc.setMax(360);

        

    }

    public GridPane GridSetup() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        return grid;
    }

    public boolean isValidEmail(String email) {
        //Check if email is valid
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if (mat.matches()) {
            return true;
        }
        else {
            return false;
        }
    }
}
