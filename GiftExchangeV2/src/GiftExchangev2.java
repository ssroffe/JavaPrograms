/* Written by
   Seth Roffe
*/
package giftexchange;

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
import javafx.beans.value.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Message.*;

public class GiftExchangev2 extends Application {
    /** Performs a gift exchange pairing. Inputs include the number of people
	participating which must be greater than 2 people and the names of
	those people. The outputs are files that are named for the buyer
	containing a string that explains who that person is buying for.
    */

    private String userName;
    private String password;

    public void start(Stage primaryStage) throws FileNotFoundException, AddressException, MessagingException {
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
                try {
                    getEmailPswd();
                }
                catch (FileNotFoundException fne) {
                    System.out.println("File Not Found Exception");
                    fne.printStackTrace();
                }
                catch (AddressException ae) {
                    ae.printStackTrace();
                }
                catch (MessagingException me) {
                    me.printStackTrace();
                }

            }
        });

        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { 
                primaryStage.close();
                try {
                    GetPartyNoEmail();
                }
                catch (FileNotFoundException fne) {
                    fne.printStackTrace();
                }
            }
        });
        primaryStage.show();
    }

    public void getEmailPswd() throws FileNotFoundException, AddressException, MessagingException {
        Stage stage = new Stage();
        stage.setTitle("Gift Exchange -- Host Email");

        GridPane grid = GridSetup();
        
        Scene scene = new Scene(grid);
        stage.setScene(scene);

        Text title = new Text("Please input your gmail username and password");
        grid.add(title,0,0,2,1);

        Label username = new Label("User Name:");
        grid.add(username,0,1);

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
                    userName = userTf.getText();
                    password = pwPf.getText();
                    try {
                        GetPartyEmail();
                        stage.close();
                    }
                    catch (FileNotFoundException fne) {
                        System.out.println("File Not Found Exception");
                        fne.printStackTrace();
                    }
                    catch (AddressException ae) {
                        ae.printStackTrace();
                    }
                    catch (MessagingException me) {
                        me.printStackTrace();
                    }
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

    public void GetPartyEmail() throws FileNotFoundException, AddressException, MessagingException {

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> emails = new ArrayList<String>();

        Stage stage = new Stage();
        stage.setTitle("Gift Exchange -- Party input");
        GridPane grid = GridSetup();

        Scene scene = new Scene(grid);
        
        stage.setScene(scene);

        final ScrollPane sp = new ScrollPane();
        
        sp.setVmax(440);
        sp.setPrefSize(115,150);

        final VBox vb = new VBox();

        vb.setLayoutX(5);
        vb.setSpacing(5);

        sp.setContent(vb);

        grid.add(sp,0,0);
        Text emailLabel = new Text();

        //get the list of people

        Text namesText = new Text("Name:");

        TextField namesTf = new TextField();
        
        HBox hbNames = new HBox(10);
        hbNames.getChildren().addAll(namesText,namesTf);
        grid.add(hbNames,0,1);

        Text emailsText = new Text("Email:");

        TextField emailsTf = new TextField();

        HBox hbEmails = new HBox(10);
        hbEmails.getChildren().addAll(emailsText,emailsTf);
        grid.add(hbEmails,0,2);

        Button next = new Button("Next");
        Button done = new Button("Done");
        HBox hb = new HBox(10);
        hb.getChildren().addAll(next,done);
        grid.add(hb,0,3);

        next.setOnAction(new EventHandler<ActionEvent>() {
            int count = 1;
            Text errMsg = new Text("You need to input a valid email!");
            Text errMsgName = new Text("You need to input a person\'s name!");
            @Override
            public void handle(ActionEvent e) {

                errMsg.setFill(Color.FIREBRICK);
                errMsgName.setFill(Color.FIREBRICK);
                emailLabel.setText("Person " + (count) + "'s email is (No email will make a file for the person):");
                String person = namesTf.getText();
                String email = emailsTf.getText();

                if (person.length() == 0) {
                    grid.add(errMsgName,2,1);

                }
                else if ( !isValidEmail(email) && email.length() !=0 ) {
                    grid.add(errMsg,2,2);
                }
                else {
                    count++;
                    names.add(person);
                    grid.getChildren().remove(errMsgName);
                    grid.getChildren().remove(errMsg);
                    if (email.length() == 0) {
                        emails.add("");
                        Label label = new Label(person + "\t" + email);
                        vb.getChildren().add(label);
                    }
                    else {
                        Label label = new Label(person + "\t" + email);
                        emails.add(email);
                        vb.getChildren().addAll(label);
                    }
                    namesTf.clear();
                    emailsTf.clear();
                    namesTf.requestFocus();
                }
            }
        });

        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String person = namesTf.getText();
                String email = emailsTf.getText();
                if (person.length() == 0) {
                }
                else {
                    names.add(person);
                    if (email.length() == 0) {
                        emails.add("");
                    }
                    else {
                        emails.add(email);
                    }
                }
                stage.close();
                try {
                    ListConfirm(names,emails);
                }
                catch (FileNotFoundException fne) {
                    System.out.println("File Not Found Exception");
                    fne.printStackTrace();
                }
                catch (AddressException ae) {
                    ae.printStackTrace();
                }
                catch (MessagingException me) {
                    me.printStackTrace();
                }
            }
        });

        stage.show();


        
    }

    public void ListConfirm(ArrayList<String> names, ArrayList<String> emails) throws FileNotFoundException, AddressException, MessagingException {

        Stage stage = new Stage();
        stage.setTitle("Gift Exchange -- List Confirm");

        GridPane grid = GridSetup();

        Scene scene = new Scene(grid);

        stage.setScene(scene);

        final ScrollPane sp = new ScrollPane();
        
        sp.setVmax(440);
        sp.setPrefSize(200,200);

        final VBox vb = new VBox();

        vb.setLayoutX(5);
        vb.setSpacing(5);
        
        for (int i = 0; i < names.size(); i++) {
            Label label = new Label(names.get(i) + "\t" + emails.get(i));
            vb.getChildren().add(label);
        }


        Label listNames = new Label("The names in the list are:");
        grid.add(listNames,0,0);
        grid.add(sp,0,1);

        Label correct = new Label("Is this correct?");
        grid.add(correct,0,2);

        Button yes = new Button("Yes");
        Button no = new Button("No");
        HBox hbyesno = new HBox(10);
        hbyesno.getChildren().addAll(yes,no);
        grid.add(hbyesno,0,3);

        sp.setContent(vb);

        stage.show();

        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
                try {
                    EmailPeople(names,emails);
                }
                catch (FileNotFoundException fne) {
                    fne.printStackTrace();
                }
                catch (AddressException ae) {
                    ae.printStackTrace();
                }
                catch (MessagingException me) {
                    me.printStackTrace();
                }
            }
        });
        
        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) { 
                stage.close();
                try {
                    GetPartyEmail();
                }
                catch (FileNotFoundException fne) {
                    fne.printStackTrace();
                }
                catch (AddressException ae) {
                    ae.printStackTrace();
                }
                catch (MessagingException me) {
                    me.printStackTrace();
                }
            }
        });

    }

    public void EmailPeople(ArrayList<String> names, ArrayList<String> emails) throws FileNotFoundException, AddressException, MessagingException {

        ArrayList<Integer> exchange = new ArrayList<Integer>();
        for (int i = 0; i < names.size(); i++) {
            exchange.add(i);
        }

        //Don't want people to get themselves
        boolean getSelf = true;
        do {
            getSelf = false;
            Collections.shuffle(exchange);
            for (int i = 0; i < names.size(); i++) {
                if (exchange.get(i) == i) {
                    getSelf = true;
                }
            }
        } while (getSelf);

        Stage stage = new Stage();
        stage.setTitle("Gift Exchange -- Done");

        GridPane grid = GridSetup();
        
        Scene scene = new Scene(grid);
        stage.setScene(scene);

        try {
            String pathToJar = GiftExchangev2.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String path = new File(pathToJar).getParentFile().getPath();
            for (int i = 0; i < names.size(); i++) {
                if (emails.get(i) == "") {
                    //write files for people who don't have emails
                    PrintWriter output = new PrintWriter(path+"/"+names.get(i)+".txt");
                    //Map number in shuffled integer array to original names array.
                    output.print("You are buying for "+names.get(exchange.get(i))+".");
                    output.close();
                }
                else {
                    //email the people

                    Properties props = System.getProperties();
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.smtp.host", "smtp.gmail.com");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.port", "465");//"465"); // smtp port
                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName, password);
                        }
                    });

                    MimeMessage msg = new MimeMessage(session);

                    msg.setFrom(new InternetAddress(userName));
                    msg.setSubject("Gift Exchange Person Output");

                    msg.setRecipient(RecipientType.TO, new InternetAddress(emails.get(i)));
                    //add atleast simple body

                    MimeBodyPart body = new MimeBodyPart();
                    body.setText("You are buying for "+names.get(exchange.get(i))+".");


                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(body);
		    
                    msg.setContent(multipart);
                    Transport.send(msg);

                }
		
            }
        }
        catch (IOException ioe) {
            System.out.println("There was an IO exception.");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was an error");
        }

        Label doneLabel = new Label("Done. Files emailed. If there was no email inputted, a file was created containing who that person is buying for.");
        grid.add(doneLabel,0,0,1,2);

        Button btn = new Button("OK");
        grid.add(btn,0,3);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
            }
        });
    

        
            

    }
    
    public void GetPartyNoEmail() throws FileNotFoundException {

        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> emails = new ArrayList<String>();

        Stage stage = new Stage();
        stage.setTitle("Gift Exchange -- Party input");
        GridPane grid = GridSetup();

        Scene scene = new Scene(grid);
        
        stage.setScene(scene);

        final ScrollPane sp = new ScrollPane();
        
        sp.setVmax(440);
        sp.setPrefSize(115,150);

        final VBox vb = new VBox();

        vb.setLayoutX(5);
        vb.setSpacing(5);

        sp.setContent(vb);

        grid.add(sp,0,0);

        //get the list of people

        Text namesText = new Text("Name:");

        TextField namesTf = new TextField();
        
        HBox hbNames = new HBox(10);
        hbNames.getChildren().addAll(namesText,namesTf);
        grid.add(hbNames,0,1);

        Button next = new Button("Next");
        Button done = new Button("Done");
        HBox hb = new HBox(10);
        hb.getChildren().addAll(next,done);
        grid.add(hb,0,2);

        next.setOnAction(new EventHandler<ActionEvent>() {
            Text errMsgName = new Text("You need to input a person\'s name!");
            @Override
            public void handle(ActionEvent e) {

                errMsgName.setFill(Color.FIREBRICK);
                String person = namesTf.getText();

                if (person.length() == 0) {
                    grid.add(errMsgName,2,1);

                }
                else {
                    names.add(person);
                    grid.getChildren().remove(errMsgName);
                    emails.add("");
                    Label label = new Label(person);
                    vb.getChildren().add(label);
                    namesTf.clear();
                    namesTf.requestFocus();
                }
            }
        });

        done.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String person = namesTf.getText();
                if (person.length() == 0) {
                }
                else {
                    names.add(person);
                }
                stage.close();
                try {
                    ListConfirmNoEmail(names);
                }
                catch (FileNotFoundException fne) {
                    fne.printStackTrace();
                }

            }
        });

        stage.show();


        
    }
    public void ListConfirmNoEmail(ArrayList<String> names) throws FileNotFoundException {

        Stage stage = new Stage(); 
        stage.setTitle("Gift Exchange -- List Confirm");

        GridPane grid = GridSetup();

        Scene scene = new Scene(grid);

        stage.setScene(scene);

        final ScrollPane sp = new ScrollPane();
        
        sp.setVmax(440);
        sp.setPrefSize(200,200);

        final VBox vb = new VBox();

        vb.setLayoutX(5);
        vb.setSpacing(5);
        
        for (int i = 0; i < names.size(); i++) {
            Label label = new Label(names.get(i)); 
            vb.getChildren().add(label);
        }


        Label listNames = new Label("The names in the list are:");
        grid.add(listNames,0,0);
        grid.add(sp,0,1);

        Label correct = new Label("Is this correct?");
        grid.add(correct,0,2);

        Button yes = new Button("Yes");
        Button no = new Button("No");
        HBox hbyesno = new HBox(10);
        hbyesno.getChildren().addAll(yes,no);
        grid.add(hbyesno,0,3);

        sp.setContent(vb);

        stage.show();

        yes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
                try {
                    MakeFiles(names);
                }
                catch (FileNotFoundException fne) {
                    fne.printStackTrace();
                }

            }
        });
        
        no.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
                try {
                    GetPartyEmail();
                }
                catch (FileNotFoundException fne) {
                    fne.printStackTrace();
                }
                catch (AddressException ae) {
                    ae.printStackTrace();
                }
                catch (MessagingException me) {
                    me.printStackTrace();
                }
            }
        });

    }


    public void MakeFiles(ArrayList<String> names) throws FileNotFoundException {

        ArrayList<Integer> exchange = new ArrayList<Integer>();
        for (int i = 0; i < names.size(); i++) {
            exchange.add(i);
        }

        //Don't want people to get themselves
        boolean getSelf = true;
        do {
            getSelf = false;
            Collections.shuffle(exchange);
            for (int i = 0; i < names.size(); i++) {
                if (exchange.get(i) == i) {
                    getSelf = true;
                }
            }
        } while (getSelf);

        try {
            String pathToJar = GiftExchangev2.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String path = new File(pathToJar).getParentFile().getPath();
            for (int i = 0; i < names.size(); i++) {
                //write files for people who don't have emails
                PrintWriter output = new PrintWriter(path+"/"+names.get(i)+".txt");
                //Map number in shuffled integer array to original names array.
                output.print("You are buying for "+names.get(exchange.get(i))+".");
                output.close();
            }
        }
        catch (IOException ioe) {
            System.out.println("There was an IO exception");
            ioe.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Gift Exchange -- Done");

        GridPane grid = GridSetup();
        
        Scene scene = new Scene(grid);
        stage.setScene(scene);

        Label doneLabel = new Label("Done. Files Created. Files contain who that person is buying for.");
        grid.add(doneLabel,0,0,1,2);

        Button btn = new Button("OK");
        grid.add(btn,0,3);

        stage.show();

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
            }
        });
    


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
