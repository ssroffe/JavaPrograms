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

public class GiftExchangeGUI {
    
    /** Performs a gift exchange pairing. Inputs include the number of people
	participating which must be greater than 2 people and the names of
	those people. The outputs are files that are named for the buyer
	containing a string that explains who that person is buying for.
    */
    public static void main(String[] args) throws FileNotFoundException,AddressException,MessagingException {	
	
	Scanner input = new Scanner(System.in);

	JFrame inputFrame = new JFrame("Gift Exchange");
	inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	
	boolean useEmails = UsingEmails();

	if (useEmails) {
	    EmailMethod();
	}
	else {
	    FileMethod();
	}

	    
    }

    
    public static boolean isValidEmail(String email){
	//Check if valid email address
	
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if(mat.matches()){
            return true;
        }
	else{
            return false;
        }
    }

    public static String[] getEmailPswd() {
	boolean getEmail = true;
	String[] tmp = {"",""};
	Object[] nextDone = {"OK","Cancel"}; //button labels
	do { 

	    JPanel userPanel = new JPanel();
	    
	    JTextField username = new JTextField(40);
	    username.setPreferredSize(new Dimension(100,50));
	    username.setFont(new Font("Arial", Font.PLAIN,30));
	    
	    JPasswordField passwd = new JPasswordField(40);
	    passwd.setPreferredSize(new Dimension(100,50));
	    passwd.setFont(new Font("Arial", Font.PLAIN,30));
	    
	    JLabel userLabel = new JLabel("Email Username: ");
	    userLabel.setPreferredSize(new Dimension(400,50));
	    userLabel.setFont(new Font("Arial",Font.BOLD,24));
	    
	    JLabel passLabel = new JLabel("Password: ");
	    passLabel.setPreferredSize(new Dimension(400,50));
	    passLabel.setFont(new Font("Arial",Font.BOLD,24));
	    
	    userPanel.setLayout(new GridBagLayout());
	    GridBagConstraints grid = new GridBagConstraints();
	    grid.gridx = 0;
	    grid.gridy = 0;
	    grid.anchor = GridBagConstraints.WEST;
	    
	    userPanel.add(userLabel,grid);
	    grid.fill = GridBagConstraints.HORIZONTAL;
	    grid.gridy++;
	    userPanel.add(username,grid);
	    grid.fill = GridBagConstraints.HORIZONTAL;
	    grid.gridy++;
	    userPanel.add(passLabel,grid);
	    grid.fill = GridBagConstraints.HORIZONTAL;
	    grid.gridy++;
	    userPanel.add(passwd,grid);

	    int listPressed = JOptionPane.showOptionDialog(null,
							   userPanel, //message
							   "Gift Exchange - Host Email", //title
							   JOptionPane.YES_NO_OPTION,
							   JOptionPane.INFORMATION_MESSAGE,
							   null, //no custom icon
							   nextDone, //title of buttons
							   username //default button
							   );
	    
	    if (listPressed == JOptionPane.YES_OPTION) {

		if (username.getText().length() == 0 || !isValidEmail(username.getText())) {
		    //if Next was pushed but nothing was entered
		    JLabel errMessage = new JLabel("You need to input a valid email address");

		    errMessage.setFont(new Font("Arial",Font.BOLD,32));
		    
		    JOptionPane.showMessageDialog(null,
						  errMessage,
						  "Invalid Host email",
						  JOptionPane.WARNING_MESSAGE
						  );
		    getEmail = true;
		}
		
		else if ( passwd.getPassword().length == 0 ) {
		    JLabel errMessage = new JLabel("You need to input a password.");

		    errMessage.setFont(new Font("Arial",Font.BOLD,32));
		    
		    JOptionPane.showMessageDialog(null,
						  errMessage,
						  "No password inputted",
						  JOptionPane.WARNING_MESSAGE
						  );
		    getEmail = true;
		}
		
		else {
		    getEmail = false;
		    String user = username.getText();
		    String pass = new String(passwd.getPassword());
		    String[] userpass = {user,pass};
		    return userpass;
		}
	    }
	    else if (listPressed == JOptionPane.NO_OPTION){
		//cancel == cancel the program
		System.exit(0);
	    }
	    else if (listPressed == JOptionPane.CLOSED_OPTION) {
		System.exit(0);
	    }	    
	    
	} while (getEmail);
	return tmp;
	
    }


    public static void EmailPeople(String[] userpass, ArrayList<String> names, ArrayList<String> emails, ArrayList<Integer> exchange) throws FileNotFoundException,AddressException, MessagingException{
	try {
	    String pathToJar = GiftExchangeGUI.class.getProtectionDomain().getCodeSource().getLocation().getPath();
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
				return new PasswordAuthentication(userpass[0], userpass[1]);
			    }
			});

		    MimeMessage msg = new MimeMessage(session);
		    
		    msg.setFrom(new InternetAddress(userpass[0]));
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
    }

    public static boolean UsingEmails() {
	String[] yesNo = {"Yes","No"};
	JPanel userPanel = new JPanel();

	UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,36)));
	
	JLabel label = new JLabel("Would you like to automate the emailing?");
	label.setPreferredSize(new Dimension(800,50));
	label.setFont(new Font("Arial",Font.BOLD,24));

	JLabel notice = new JLabel("Note: Only for gmail host email");
	notice.setPreferredSize(new Dimension(400,50));
	notice.setFont(new Font("Arial",Font.BOLD,24));
	
	userPanel.setLayout(new GridBagLayout());
	GridBagConstraints grid = new GridBagConstraints();
	grid.gridx = 0;
	grid.gridy = 0;
	grid.anchor = GridBagConstraints.WEST;
	
	userPanel.add(label,grid);
	grid.fill = GridBagConstraints.HORIZONTAL;
	grid.gridy++;
	userPanel.add(notice,grid);
	grid.fill = GridBagConstraints.HORIZONTAL;
	grid.gridy++;

	
	int pressed = JOptionPane.showOptionDialog(null,
						       userPanel, //message
						       "Gift Exchange - Host Email", //title
						       JOptionPane.YES_NO_OPTION,
						       JOptionPane.QUESTION_MESSAGE,
						       null, //no custom icon
						       yesNo, //title of buttons
						       JOptionPane.YES_OPTION //default button
						       );
	if (pressed == JOptionPane.YES_OPTION) {
	    return true;
	}
	else if (pressed == JOptionPane.NO_OPTION) {
	    return false;
	}
	else if (pressed == JOptionPane.CLOSED_OPTION) {
	    System.exit(0);
	}
	return true;
    }

    public static void EmailMethod() throws FileNotFoundException,AddressException,MessagingException {
	//get the list of people's names and store it into an arraylist of strings
	ArrayList<String> names = new ArrayList<String>();
	ArrayList<String> emails = new ArrayList<String>();
	
	//enlarge buttons
	UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,36)));	

	boolean inputNames = true;
	int count = 1;

	String[] userpass = getEmailPswd();
	String username = userpass[0];
	String password = userpass[1];
	
	// Get the list of people
	do {

	    JLabel emailLabel = new JLabel("Person " + (count) + "'s email is (No email will make a file for the person):");
	    emailLabel.setPreferredSize(new Dimension(100,100));
	    emailLabel.setFont(new Font("Arial",Font.BOLD,34));
	    
	    JLabel messageLabel = new JLabel("Person "+(count)+" is:");
	    messageLabel.setPreferredSize(new Dimension(100,100));
	    messageLabel.setFont(new Font("Arial",Font.BOLD,36));

	    JPanel peoplePanel = new JPanel();

	    Object[] nextDone = {"Next","Done"}; //button labels

	    JTextField textField = new JTextField(40); //input
	    textField.setPreferredSize(new Dimension(100,50));
	    textField.setFont(new Font("Arial",Font.PLAIN,30));

	    JTextField emailField = new JTextField(40); //email input
	    emailField.setPreferredSize(new Dimension(100,50));
	    emailField.setFont(new Font("Arial",Font.PLAIN,30));
	    //panel.add(textField);

	    JTextArea currentList = new JTextArea(20,10); //Where the names are listed
	    currentList.setFont(new Font("Arial",Font.PLAIN,24));
	    
	    JLabel currentLabel = new JLabel("The current list is: ");
	    currentLabel.setPreferredSize(new Dimension(400,50));
	    currentLabel.setFont(new Font("Arial",Font.BOLD,24));
	    
	    
	    for (int i = 0; i < names.size(); i++) {
		currentList.append(names.get(i)+"\t" + emails.get(i)+"\n");
	    }
	    
	    currentList.setEditable(false); //make the list read-only
	    
	    JScrollPane scrollPeoplePane = new JScrollPane(currentList);
	    scrollPeoplePane.setPreferredSize(new Dimension(100,250));
	    peoplePanel.setLayout(new GridBagLayout());
	    GridBagConstraints grid = new GridBagConstraints();
	    grid.gridx = 0;
	    grid.gridy = 0;
	    grid.anchor = GridBagConstraints.WEST;

	    peoplePanel.add(currentLabel,grid);
	    grid.fill = GridBagConstraints.HORIZONTAL;
	    grid.gridy++;
	    peoplePanel.add(currentList,grid);
	    grid.fill = GridBagConstraints.HORIZONTAL;
	    grid.gridy++;
	    peoplePanel.add(messageLabel,grid);
	    grid.fill = GridBagConstraints.HORIZONTAL;
	    grid.gridy++;
	    peoplePanel.add(textField,grid);
	    grid.fill = GridBagConstraints.HORIZONTAL;
	    grid.gridy++;
	    peoplePanel.add(emailLabel,grid);
	    grid.fill = GridBagConstraints.HORIZONTAL;
	    grid.gridy++;
	    peoplePanel.add(emailField,grid);
	    
	    
	    
	    int optionPressed = JOptionPane.showOptionDialog(null,
							     peoplePanel, //message
							     "Gift Exchange", //title
							     JOptionPane.YES_NO_OPTION,
							     JOptionPane.QUESTION_MESSAGE,
							     null, //no custom icon
							     nextDone, //title of buttons
							     textField //default button
							    );
	    
	    // Add the person to the list whilst checking for errors
	    String person;
	    String email;
	    if (optionPressed == JOptionPane.YES_OPTION) {
		person = textField.getText();
		email = emailField.getText();
		
		if (person.length() == 0) {
		    //if Next was pushed but nothing was entered
		    JLabel errMessage = new JLabel("You need to input a person\'s name");

		    errMessage.setFont(new Font("Arial",Font.BOLD,32));
		    
		    JOptionPane.showMessageDialog(null,
						  errMessage,
						  "No Person inputted",
						  JOptionPane.WARNING_MESSAGE
						  );
		}
		
		else if ( !isValidEmail(email) && email.length() != 0 ) {
		    JLabel errMessage = new JLabel("That is not a valid email address");
		    errMessage.setFont(new Font("Arial", Font.BOLD, 32));
		    JOptionPane.showMessageDialog(null,
						  errMessage,
						  "Invalid email",
						  JOptionPane.WARNING_MESSAGE
						  );
		}

		else {
		    count++;
		    names.add(person);
		    if (email.length() == 0) {
			emails.add("");
		    }
		    else {
			emails.add(email);
		    }
		    
		    inputNames = true;
		}
	    }
	    else if (optionPressed == JOptionPane.NO_OPTION){
		// Done pushed - If nothing entered on last person, ignore that entry
		person = textField.getText();
		email = emailField.getText();
		if (person.length() == 0) {
		    inputNames = false;
		}
		else {
		    names.add(person);

		    if (email.length() == 0) {
			emails.add("");
		    }
		    else {
			emails.add(email);
		    }

		    inputNames = false;
		}
	    }
	    else if (optionPressed == JOptionPane.CLOSED_OPTION) {
		//exiting by closing the window
		System.exit(0);
	    }

	    // Check the names in the list
	    if (!inputNames) {
		Object[] confirmList = {"Yes","No"};
		
		JPanel listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(400,400));
		JLabel listLabel = new JLabel("The names in the list are: ");
		listLabel.setPreferredSize(new Dimension(400,50));
		listLabel.setFont(new Font("Arial",Font.BOLD,24));
		
		JLabel correctLabel = new JLabel("Is this correct?");
		correctLabel.setPreferredSize(new Dimension(400,50));
		correctLabel.setFont(new Font("Arial",Font.BOLD,24));	     

		JTextArea listOfPeople = new JTextArea(25,25); //Where the names are listed

		listOfPeople.setFont(new Font("Arial",Font.PLAIN,24));
		
		for (int i = 0; i < names.size(); i++) {
		    listOfPeople.append(names.get(i)+" - "+ emails.get(i)+"\n");
		}

		listOfPeople.setEditable(false); //make the list read-only

		JScrollPane scrollPane = new JScrollPane(listOfPeople);
		scrollPane.setPreferredSize(new Dimension(100,250));
		listPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;

		//format the window
		listPanel.add(listLabel,gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy++;
		listPanel.add(scrollPane,gbc);
		gbc.fill= GridBagConstraints.HORIZONTAL;
		gbc.gridy++;
		listPanel.add(correctLabel,gbc);
				
		
		int listPressed = JOptionPane.showOptionDialog(null,
							       listPanel, //message
							       "Gift Exchange", //title
							       JOptionPane.YES_NO_OPTION,
							       JOptionPane.INFORMATION_MESSAGE,
							       null, //no custom icon
							       confirmList, //title of buttons
							       confirmList[0] //default button
							       );

		if (listPressed == JOptionPane.YES_OPTION) {
		    //if yes was pushed, just continue on
		}
		else if (listPressed == JOptionPane.NO_OPTION){
		    //start from scratch if no was pushed
		    inputNames = true;
		    names.clear();
		    emails.clear();
		    count = 1;
		}
		else if (listPressed == JOptionPane.CLOSED_OPTION) {
		    System.exit(0);
		}
		
	    }

	} while (inputNames);
	
	//make an arrayList of integers to map people to
	ArrayList<Integer> exchange = new ArrayList<Integer>();
	for (int i = 0; i<names.size(); i++) {
	    exchange.add(i);
	}

	//Don't want people to get themselves
	boolean getSelf = true;
	do {
	    getSelf = false;
	    //shuffles the array of integers
	    Collections.shuffle(exchange);
	    for (int i = 0; i<names.size(); i++) {
		if (exchange.get(i) == i) {
		    getSelf = true;
		}
	    }
	} while (getSelf);

	JLabel doneLabel = new JLabel("Done. Files emailed. If there was no email a file was created containing who that person is buying for.");

	doneLabel.setFont(new Font("Arial",Font.PLAIN,34));
	JOptionPane.showMessageDialog(null,
				      doneLabel,
				      "Done Matching",
				      JOptionPane.INFORMATION_MESSAGE
				      );
	
	//write files and email people
	EmailPeople(userpass,names,emails,exchange);
    }	

    public static void FileMethod() throws FileNotFoundException {
	
	Scanner input = new Scanner(System.in);
	
	JFrame inputFrame = new JFrame("Gift Exchange");
	inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//get the list of people's names and store it into an arraylist of strings
	ArrayList<String> names = new ArrayList<String>();

	//enlarge buttons
	UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,36)));

	

	boolean inputNames = true;
	int count = 1;

	// Get the list of people
	do {
	    JLabel messageLabel = new JLabel("Person "+(count)+" is:");
	    messageLabel.setPreferredSize(new Dimension(100,100));
	    messageLabel.setFont(new Font("Arial",Font.BOLD,36));

	    JPanel peoplePanel = new JPanel();

	    //panel.add(messageLabel);

	    Object[] nextDone = {"Next","Done"}; //button labels

	    JTextField textField = new JTextField(30); //input
	    textField.setPreferredSize(new Dimension(100,50));
	    textField.setFont(new Font("Arial",Font.PLAIN,30));
	    //panel.add(textField);

	    JTextArea currentList = new JTextArea(20,10); //Where the names are listed
	    currentList.setFont(new Font("Arial",Font.PLAIN,24));
	    
	    JLabel currentLabel = new JLabel("The current list is: ");
	    currentLabel.setPreferredSize(new Dimension(400,50));
	    currentLabel.setFont(new Font("Arial",Font.BOLD,24));
	    
	    
	    for (int i = 0; i < names.size(); i++) {
		currentList.append(names.get(i)+"\n");
	    }
	    
	    currentList.setEditable(false); //make the list read-only
	    
	    JScrollPane scrollPeoplePane = new JScrollPane(currentList);
	    scrollPeoplePane.setPreferredSize(new Dimension(100,250));
	    peoplePanel.setLayout(new GridBagLayout());
	    GridBagConstraints grid = new GridBagConstraints();
	    grid.gridx = 0;
	    grid.gridy = 0;
	    grid.anchor = GridBagConstraints.WEST;

	    peoplePanel.add(currentLabel,grid);
	    grid.fill = GridBagConstraints.HORIZONTAL;
	    grid.gridy++;
	    peoplePanel.add(currentList,grid);
	    grid.fill = GridBagConstraints.HORIZONTAL;
	    grid.gridy++;
	    peoplePanel.add(messageLabel,grid);
	    grid.fill = GridBagConstraints.HORIZONTAL;
	    grid.gridy++;
	    peoplePanel.add(textField,grid);
	    
	    
	    
	    int optionPressed = JOptionPane.showOptionDialog(null,
							     peoplePanel, //message
							     "Gift Exchange", //title
							     JOptionPane.YES_NO_OPTION,
							     JOptionPane.QUESTION_MESSAGE,
							     null, //no custom icon
							     nextDone, //title of buttons
							     textField //default button
							    );
	    
	    // Add the person to the list whilst checking for errors
	    String person; 
	    if (optionPressed == JOptionPane.YES_OPTION) {
		person = textField.getText();
		if (person.length() == 0) {
		    //if Next was pushed but nothing was entered
		    JLabel errMessage = new JLabel("You need to input a person\'s name");

		    errMessage.setFont(new Font("Arial",Font.BOLD,32));
		    
		    JOptionPane.showMessageDialog(null,
						  errMessage,
						  "No Person inputted",
						  JOptionPane.WARNING_MESSAGE
						  );
		}
		else {
		    count++;
		    names.add(person);
		    inputNames = true;
		}
	    }
	    else if (optionPressed == JOptionPane.NO_OPTION){
		// Done pushed - If nothing entered on last person, ignore that entry
		person = textField.getText();
		if (person.length() == 0) {
		    inputNames = false;
		}
		else {
		    names.add(person);
		    inputNames = false;
		}
	    }
	    else if (optionPressed == JOptionPane.CLOSED_OPTION) {
		//exiting by closing the window
		System.exit(0);
	    }

	    // Check the names in the list
	    if (!inputNames) {
		Object[] confirmList = {"Yes","No"};
		
		JPanel listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(400,400));
		JLabel listLabel = new JLabel("The names in the list are: ");
		listLabel.setPreferredSize(new Dimension(400,50));
		listLabel.setFont(new Font("Arial",Font.BOLD,24));
		
		JLabel correctLabel = new JLabel("Is this correct?");
		correctLabel.setPreferredSize(new Dimension(400,50));
		correctLabel.setFont(new Font("Arial",Font.BOLD,24));	     

		JTextArea listOfPeople = new JTextArea(25,25); //Where the names are listed

		listOfPeople.setFont(new Font("Arial",Font.PLAIN,24));
		
		for (int i = 0; i < names.size(); i++) {
		    listOfPeople.append(names.get(i)+"\n");
		}

		listOfPeople.setEditable(false); //make the list read-only

		JScrollPane scrollPane = new JScrollPane(listOfPeople);
		scrollPane.setPreferredSize(new Dimension(100,250));
		listPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;

		//format the window
		listPanel.add(listLabel,gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy++;
		listPanel.add(scrollPane,gbc);
		gbc.fill= GridBagConstraints.HORIZONTAL;
		gbc.gridy++;
		listPanel.add(correctLabel,gbc);
				
		
		int listPressed = JOptionPane.showOptionDialog(null,
							       listPanel, //message
							       "Gift Exchange", //title
							       JOptionPane.YES_NO_OPTION,
							       JOptionPane.INFORMATION_MESSAGE,
							       null, //no custom icon
							       confirmList, //title of buttons
							       confirmList[0] //default button
							       );

		if (listPressed == JOptionPane.YES_OPTION) {
		    //if yes was pushed, just continue on
		}
		else if (listPressed == JOptionPane.NO_OPTION){
		    //start from scratch if no was pushed
		    inputNames = true;
		    names.clear();
		    count = 1;
		}
		else if (listPressed == JOptionPane.CLOSED_OPTION) {
		    System.exit(0);
		}
		
	    }

	} while (inputNames);
	
	//make an arrayList of integers to map people to
	ArrayList<Integer> exchange = new ArrayList<Integer>();
	for (int i = 0; i<names.size(); i++) {
	    exchange.add(i);
	}

	//Don't want people to get themselves
	boolean getSelf = true;
	do {
	    getSelf = false;
	    //shuffles the array of integers
	    Collections.shuffle(exchange);
	    for (int i = 0; i<names.size(); i++) {
		if (exchange.get(i) == i) {
		    getSelf = true;
		}
	    }
	} while (getSelf);

	JLabel doneLabel = new JLabel("Done. File names are the buyer\n File contains who buyer is buying for.");

	doneLabel.setFont(new Font("Arial",Font.PLAIN,34));
	JOptionPane.showMessageDialog(null,
				      doneLabel,
				      "Done Matching",
				      JOptionPane.INFORMATION_MESSAGE
				      );
       	
	//write files
	try {
	    String pathToJar = GiftExchangeGUI.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	    String path = new File(pathToJar).getParentFile().getPath();
	    
	    for (int i = 0; i<names.size(); i++) {
		PrintWriter output = new PrintWriter(path+"/"+names.get(i)+".txt");
		//Map number in shuffled integer array to original names array.
		output.print("You are buying for "+names.get(exchange.get(i))+".");
		output.close();
	    }
	}
	catch (IOException ioe) {
	    System.out.println("There was an IO exception.");
	}
    }
    
}
	

