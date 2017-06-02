/* Written by
 * Seth Roffe
*/

package dnd;

import java.util.*;
import java.io.*;
import java.util.regex.*;
import java.util.logging.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.*;

public class CharacterMgr {
    /** Main screen for managing a character in dnd.*/

    public static void main(String[] args) {

        JFrame inputFrame = new JFrame("DND Character Manager");
        inputFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int newLoad = NewOrLoad(); // 0 for load, 1 for new

        String fileName = new String("")
        if (newLoad == 0) {
            // Load a Character file
            fileName = loadCharacterFile();
        }
        else if (newLoad == 1) {
            // New Character
        }

    }


    // Panel for load or new character question
    public static int NewOrLoad() {

        String[] loadNew = {"Load Character", "New Character"};
        JPanel panel = new JPanel();

        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("ARIAL",Font.PLAIN,36)));

        JLabel label = new JLabel("Would you like to create a new character or load an old one?");
        label.setFont(new Font("Arial", Font.PLAIN, 30));

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
    }

    public static String loadCharacterFile() {
       JFileChooser fc = new JFileChoose(".");

       int returnVal = fc.showOpenDialog(CharacterMgr.this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            String fileName = file.getName();
			return fileName;
        }

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

    //Used

}

