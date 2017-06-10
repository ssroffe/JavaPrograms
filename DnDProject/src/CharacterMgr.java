/** Written by
 * Seth Roffe
*/
package dnd;

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
import javafx.stage.FileChooser;
import javafx.fxml.FXML;
import javafx.geometry.*;

import dnd.Character;

public class CharacterMgr extends Application {

    private String fileName;
    //start the first page - new or load character
    @Override
    public void start(Stage primaryStage) {
        NewLoadScreen();
    }

    public void NewLoadScreen() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("DnD Character Manager -- New or Load");

        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(25,25,25,25));

        Scene scene1 = new Scene(grid, 600, 300);
        primaryStage.setScene(scene1);

        scene1.getStylesheets().add(this.getClass().getResource("NewLoadScreen.css").toExternalForm());

        Text welcome = new Text("Welcome to DnD!");
        grid.add(welcome, 0,0,2,1);
        welcome.setId("welcome");

        Label loadLabel = new Label("Would you like to Load a Character or Make a New one?");
        grid.add(loadLabel,0,1,1,2);

        Button lbtn = new Button("Load Character");
        Button nbtn = new Button("New Character");

        HBox nlbtn = new HBox(10);
        nlbtn.setAlignment(Pos.CENTER);
        nlbtn.getChildren().addAll(lbtn,nbtn);

        grid.add(nlbtn,0,3);

        //Load Old Character
        lbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                fileName = LoadCharacterFile(primaryStage);
                if (!fileName.isEmpty()) {
                    primaryStage.close();
                    Character c = Character.LoadCharacter(fileName);
                    CharacterSheet(c);
                }
                else {
                    final Text noCharSel = new Text("No Character Selected");
                    noCharSel.setFill(Color.FIREBRICK);
                    grid.add(noCharSel,0,4);
                }
            }
        });

        //Make a new Character
        nbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();
                NewCharacter();
            }
        });

        primaryStage.show();

    }
    
    public void NewCharacter() {
        
        Stage stage = new Stage();
        stage.setTitle("Make a new Character");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));

        Scene scene = new Scene(grid, 500, 300);

        //scene.getStylesheets().add(this.getClass().getResource("NewCharacter.css"));
        stage.setScene(scene);

        Text insertName = new Text("Please insert the name of your character (Class name optional");
        grid.add(insertName,0,0);

        TextField nameTf = new TextField();
        nameTf.setPromptText("Input Character Name");
        grid.add(nameTf,0,1);
        

        Label clss = new Label("Class name:");
        TextField clssTf = new TextField();
        clssTf.setPromptText("Input Class Name");

        HBox hbClss = new HBox(10);
        hbClss.setAlignment(Pos.CENTER);
        hbClss.getChildren().addAll(clss,clssTf);
        grid.add(hbClss,0,2);
        
        Button submit = new Button("Submit");
        Button cancel = new Button("Cancel");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().addAll(submit,cancel);
        grid.add(hbBtn,0,3);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (nameTf.getText() == null || nameTf.getText().isEmpty()) {
                    final Text noName = new Text("No name was inputted!");
                    noName.setFill(Color.FIREBRICK);
                    grid.add(noName,0,4);
                }
                else if (clss.getText() == null || clssTf.getText().isEmpty()) {
                    Character c = new Character(nameTf.getText());
                    stage.close();
                    CharacterSheet(c);
                }
                else {
                    Character c = new Character(nameTf.getText(),clssTf.getText());
                    stage.close();
                    CharacterSheet(c);
                }
            }
        });

        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                stage.close();
                NewLoadScreen();
            }
        });
        stage.show();
    }  
        

        


    public void CharacterSheet(Character c) {

        Stage stage = new Stage();
        stage.setTitle(c.getName() + " -- Character page");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(25,25,25,25));

        Scene scene = new Scene(grid, 600, 800);
        
        //scene.getStylesheets().add(this.getClass().getResource("CharacterSheet.css").toExternalForm());

        stage.setScene(scene);
	int row = 1;
        Text characterName = new Text(c.getName());
        characterName.setId("characterName");
        grid.add(characterName,0,0,2,1);

        //////////// Class ////////////////
        Label clssLabel = new Label("Class:");
        grid.add(clssLabel,0,row);
        TextField clssTf = new TextField();
        clssTf.setText(c.getClss());
        clssTf.setEditable(false);
        grid.add(clssTf,1,row);

        ToggleButton clssbtn = new ToggleButton("edit");
        clssbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (clssbtn.isSelected()) {
                    clssTf.setEditable(true);
                    clssTf.setId("unlocked-tf");
                }
                else {
                    clssTf.setEditable(false);
                    clssTf.setId("locked-tf");
                    c.setClss(clssTf.getText());
                }
            }
        });
        grid.add(clssbtn,2,row);
	row++;

        ///////////// Race //////////////
        Label raceLabel = new Label("Race:");
        grid.add(raceLabel,0,row);
        TextField raceTf = new TextField();
        raceTf.setText(c.getRace());
        raceTf.setEditable(false);
        grid.add(raceTf,1,row);

        ToggleButton racebtn = new ToggleButton("edit");
        racebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (racebtn.isSelected()) {
                    raceTf.setEditable(true);
                    raceTf.setId("unlocked-tf");
                }
                else {
                    raceTf.setEditable(false);
                    raceTf.setId("locked-tf");
                    c.setRace(raceTf.getText());
                }
            }
        });
        grid.add(racebtn,2,row);
	row++;

        ///////////// SubRace //////////////
        Label subRaceLabel = new Label("SubRace:");
        grid.add(subRaceLabel,0,row);
        TextField subRaceTf = new TextField();
        subRaceTf.setText(c.getSubrace());
        subRaceTf.setEditable(false);
        grid.add(subRaceTf,1,row);

        ToggleButton subRacebtn = new ToggleButton("edit");
        subRacebtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (subRacebtn.isSelected()) {
                    subRaceTf.setEditable(true);
                    subRaceTf.setId("unlocked-tf");
                }
                else {
                    subRaceTf.setEditable(false);
                    subRaceTf.setId("locked-tf");
                    c.setSubrace(subRaceTf.getText());
                }
            }
        });
        grid.add(subRacebtn,2,row);
	row++;

        stage.show();

        ///////////// Weapon //////////////
        Label weaponLabel = new Label("Weapon:");
        grid.add(weaponLabel,0,row);
        TextField weaponTf = new TextField();
        weaponTf.setText(c.getWeapon());
        weaponTf.setEditable(false);
        grid.add(weaponTf,1,row);

        ToggleButton weaponbtn = new ToggleButton("edit");
        weaponbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (weaponbtn.isSelected()) {
                    weaponTf.setEditable(true);
                    weaponTf.setId("unlocked-tf");
                }
                else {
                    weaponTf.setEditable(false);
                    weaponTf.setId("locked-tf");
                    c.setWeapon(weaponTf.getText());
                }
            }
        });
        grid.add(weaponbtn,2,row);
	row++;

        stage.show();	

	
	//////////// Health //////////////
	Label maxHealthLabel = new Label("Max HP:");
	grid.add(maxHealthLabel,0,row);
	TextField maxHealthTf = new TextField();
	maxHealthTf.setText(Integer.toString(c.getMaxHP()));
	maxHealthTf.setEditable(false);
	grid.add(maxHealthTf,1,row);
	final int maxHealthRow = row;
	
	ToggleButton maxHealthbtn = new ToggleButton("edit");
	maxHealthbtn.setOnAction(new EventHandler<ActionEvent>() {
		//for checking valid new max HP
		int previousMaxHP = Integer.parseInt(maxHealthTf.getText());
		Text errMaxHP = new Text("New Max HP was not an integer!");
		@Override
		public void handle(ActionEvent e) {
		    if (maxHealthbtn.isSelected()) {
			previousMaxHP = Integer.parseInt(maxHealthTf.getText());
			maxHealthTf.setEditable(true);
			maxHealthTf.setId("unlocked-tf");
		    }
		    else {
				maxHealthTf.setEditable(false);
				maxHealthTf.setId("locked-tf");
				boolean isInt = isInteger(maxHealthTf.getText());
				if (isInt) {
					c.setMaxHP(Integer.parseInt(maxHealthTf.getText()));
					grid.getChildren().remove(errMaxHP);
				}
				else {
					errMaxHP.setFill(Color.FIREBRICK);
					grid.add(errMaxHP,3,maxHealthRow);
					maxHealthTf.setText(Integer.toString(previousMaxHP));
				}
			}
		}
	});
	grid.add(maxHealthbtn,2,row);

	}

    
    //Load a previously made character file
    public static String LoadCharacterFile(Stage primaryStage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Character Sheet");
        File initDir = new File("."); //Start in current directory
        fc.setInitialDirectory(initDir);


        //show only json files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)","*.json");
        fc.getExtensionFilters().add(extFilter);
        File file = fc.showOpenDialog(primaryStage);
        if (file != null) {
            String filePath = file.getPath();
            return filePath;
        }
        else {
            return "";
        }
    }

    public static boolean isInteger(String s) {
	try {
	    Integer.parseInt(s);
	    return true;
	}
	catch (NumberFormatException ex) {
	    return false;
	}
    }
    
}
