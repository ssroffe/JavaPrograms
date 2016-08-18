import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class TestWindow {
    
    public static void main(String args[]) {
	
	JFrame frame = new JFrame("Testing Window");
	//JPanel panel = new JPanel();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	JPanel panel = new JPanel();
	panel.setLayout(new FlowLayout());
	JButton button = new JButton();
	button.setText("I am a button!");
	button.setFont(new Font("Serif",Font.PLAIN,34));
	button.setPreferredSize(new Dimension(500,300));
	JLabel label = new JLabel("I am a label!\n");
	label.setPreferredSize(new Dimension(500,300));
	label.setFont(new Font("Serif",Font.PLAIN,34));

	panel.add(button);

	button.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		label.setText("The button was pressed!");
	    }
	    });
	String testIn = JOptionPane.showInputDialog(frame,"This is a test of input: ");

	JTextField testTextField = new JTextField(5);
	panel.add(testTextField);
	panel.add(label);	
	frame.add(panel);
	
	frame.setSize(900,900);
	frame.setVisible(true);
    }
}
