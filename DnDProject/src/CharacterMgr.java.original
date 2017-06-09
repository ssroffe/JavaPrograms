/* Written by
 * Seth Roffe
*/

//package dnd;

import java.util.*;
import java.util.regex.*;
import java.util.logging.*;
import java.io.File;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.plaf.*;

public class CharacterMgr {
    /** Main screen for managing a character in dnd.*/

    public static void main(String[] args) {

        JFrame inputFrame = new JFrame("DND Character Manager");
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int newLoad = NewOrLoad(); // 0 for load, 1 for new

        Character character = new Character();

        //Get the Character File
        String fileName = new String("");
        boolean goodFileName = false;
        do {
            if (newLoad == 0) {
                // Load a Character file and make sure it exists
                fileName = loadCharacterFile();
                if (fileName.isEmpty()) {
                    goodFileName = false;
                    newLoad = NewOrLoad();
                }
                else {
					character = Character.loadCharacter(fileName);
                    goodFileName = true;
                }
            }
            else if (newLoad == 1) {
                // New Character
                goodFileName = true;
            }
        } while (!goodFileName);

		

    }


    // Panel for load or new character question
    public static int NewOrLoad() {

        String[] loadNew = {"Load Character", "New Character"};
        JPanel panel = new JPanel();

        //Button font
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,30)));

        JLabel label = new JLabel("Would you like to create a new character or load an old one?");
        label.setFont(new Font("Arial", Font.PLAIN, 30)); //label font

        panel.setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.gridx = 0; grid.gridy = 0;
        grid.anchor = GridBagConstraints.WEST;

        panel.add(label,grid);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridy++;
        panel.add(label,grid);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridy++;


        int btn = makeQuestionButton(panel, "Load or New Character", loadNew);

        if (btn == JOptionPane.YES_OPTION) {
            //Load Character
            return 0;
        }
        else if (btn == JOptionPane.NO_OPTION) {
            //New Character;
            return 1;
        }
        else if (btn == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
        return -1;
    }

    // Load a previously made character file
    public static String loadCharacterFile() {
        JFileChooser fc = new JFileChooser(".");
        
        //show only json files
        FileFilter filter = new FileNameExtensionFilter("JSON Files", "json");
        fc.addChoosableFileFilter(filter);
        fc.setAcceptAllFileFilterUsed(false);
       
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            String fileName = file.getPath();
			return fileName;
        }
        else {
            return "";
        }

    }

	public static void CharacterPage(Character c) {

		JPanel panel = new JPanel();
		
		JLabel name = new JLabel(c.getName());
		JLabel race = new JLabel(c.getRace() + " " + c.getClss());

	}

    // Make a button for questions
    public static int makeQuestionButton(JPanel panel, String title, String[] btnNames) {
        int btn = JOptionPane.showOptionDialog(null,
                                               panel,
                                               title,
                                               JOptionPane.YES_NO_OPTION,
                                               JOptionPane.QUESTION_MESSAGE,
                                               null,
                                               btnNames,
                                               JOptionPane.YES_OPTION
                                               );

        return btn;
    }

}

