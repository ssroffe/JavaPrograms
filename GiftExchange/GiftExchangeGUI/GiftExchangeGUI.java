/* Written by
   Seth Roffe
*/

package GiftExchangeGUI;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;

public class GiftExchangeGUI {
    
    /** Performs a gift exchange pairing. Inputs include the number of people
	participating which must be greater than 2 people and the names of
	those people. The outputs are files that are named for the buyer
	containing a string that explains who that person is buying for.
    */
    public static void main(String[] args) throws FileNotFoundException {	
	
	Scanner input = new Scanner(System.in);

	JFrame inputFrame = new JFrame("Gift Exchange");
	inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//get the list of people's names and store it into an arraylist of strings
	ArrayList<String> names = new ArrayList<String>();

	//enlarge buttons on inputdialog
	UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,36)));
	//UIManager.put("OptionPane.inputBox", new FontUIResource(new Font("ARIAL",Font.PLAIN,36)));	

	
	//input.nextLine();
	boolean inputNames = true;
	int count = 1;
	//boolean confirm = false;
	do {
	    JLabel messageLabel = new JLabel("Person "+(count)+" is:");
	    messageLabel.setPreferredSize(new Dimension(250,300));
	    messageLabel.setFont(new Font("Arial",Font.BOLD,36));

	    JPanel panel = new JPanel();

	    panel.add(messageLabel);
	    
	    Object[] nextDone = {"Next","Done"};

	    JTextField textField = new JTextField(30);
	    textField.setPreferredSize(new Dimension(100,50));
	    textField.setFont(new Font("Arial",Font.PLAIN,30));
	    panel.add(textField);
	    
	    int optionPressed = JOptionPane.showOptionDialog(null,
							    panel, //message
							    "Gift Exchange", //title
							    JOptionPane.YES_NO_OPTION,
							    JOptionPane.QUESTION_MESSAGE,
							    null, //no custom icon
							    nextDone, //title of buttons
							    textField //default button
							    );

	    String person; 
	    if (optionPressed == JOptionPane.YES_OPTION) {
		person = textField.getText();
		if (person.length() == 0) {
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
		System.exit(0);
	    }

	    
	    if (!inputNames) {
		Object[] confirmList = {"Yes","No"};
		
		JPanel listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(400,400));
		JLabel listLabel = new JLabel("The names in the list are: ");
		listLabel.setPreferredSize(new Dimension(400,50));
		listLabel.setFont(new Font("Arial",Font.BOLD,24));		
		

		JTextArea listOfPeople = new JTextArea(25,25);

		listOfPeople.setFont(new Font("Arial",Font.PLAIN,24));
		
		for (int i = 0; i < names.size(); i++) {
		    listOfPeople.append(names.get(i)+"\n");
		}

		listOfPeople.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(listOfPeople);
		scrollPane.setPreferredSize(new Dimension(100,250));
		listPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;

		listPanel.add(listLabel,gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy++;
		listPanel.add(scrollPane,gbc);
		
		
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
		    
		}
		else if (listPressed == JOptionPane.NO_OPTION){
		    //confirm = true;
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
	    for (int i = 0; i<names.size(); i++) {
		PrintWriter output = new PrintWriter(names.get(i)+".txt");
		//Map number in shuffled integer array to original names array.
		output.print("You are buying for "+names.get(exchange.get(i))+".");
		output.close();
	    }
	}
	catch (IOException ioe) {
	    System.out.println("There was an IO exception.");
	}
	System.exit(0);
    }
}
