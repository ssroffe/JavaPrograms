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

        VBox vbWelcome = new VBox(10);
        vbWelcome.setAlignment(Pos.CENTER);

        grid.setPadding(new Insets(25,25,25,25));

        Scene scene1 = new Scene(grid, 600, 300);
        scene1.getStylesheets().add(this.getClass().getResource("NewLoadScreen.css").toExternalForm());
        primaryStage.setScene(scene1);


        Text welcome = new Text("Welcome to DnD!");
        //grid.add(welcome, 0,0,3,1);
        //grid.add(welcome,0,0);
        welcome.setId("welcome");

        Label loadLabel = new Label("Would you like to Load a Character or Make a New one?");
        //grid.add(loadLabel,0,1,1,2);

        Button lbtn = new Button("Load Character");
        Button nbtn = new Button("New Character");

        HBox nlbtn = new HBox(10);
        nlbtn.setAlignment(Pos.CENTER);
        nlbtn.getChildren().addAll(lbtn,nbtn);

        //grid.add(nlbtn,0,3);

        vbWelcome.getChildren().addAll(welcome,loadLabel,nlbtn);
        grid.add(vbWelcome,0,0);

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

        Text insertName = new Text("Please insert the name of your character (Class name optional).");
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

        //////////// Level ////////////////

        Label levelLabel = new Label("Level:");
        grid.add(levelLabel,0,row);
        Label level = new Label(Integer.toString(c.getLevel()));
        grid.add(level,1,row);
        Button addLevel = new Button("Add Level");
        Button lowLevel = new Button("Lower Level");
        HBox hblvl = new HBox(10);
        hblvl.getChildren().addAll(addLevel,lowLevel);
        grid.add(hblvl,2,row);

        addLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                c.setLevel(c.getLevel() + 1);
                level.setText(Integer.toString(c.getLevel()));
            }
        });
 
        lowLevel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                c.setLevel(c.getLevel() - 1);
                level.setText(Integer.toString(c.getLevel()));
            }
        });       

        row++;

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

        /*
        Button testbtn = new Button("test");
        grid.add(testbtn,3,row);
        testbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println(c.getClss());
            }
        });
        */
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

    ///////////// Gold /////////////////
    Label goldLabel = new Label("Gold:");
    grid.add(goldLabel,0,row);
    Label gold = new Label(Integer.toString(c.getGold()));
    grid.add(gold,1,row);
    gold.setId("gold");

    row++;

    ////////// ADD/LOSE GOLD ///////////
    TextField pmGoldTf = new TextField();
    pmGoldTf.setPromptText("Add or Subtract Gold");
    grid.add(pmGoldTf,1,row);

    Button plusGold = new Button("Add Gold");
    Button minusGold = new Button("Spend Gold");
    HBox hbpmGold = new HBox(10);
    hbpmGold.getChildren().addAll(plusGold,minusGold);
    grid.add(hbpmGold,2,row);

    final int addGoldRow = row;

    plusGold.setOnAction(new EventHandler<ActionEvent>() {
        Text errMsg = new Text("Inputted Value was not an Integer!");
        @Override
        public void handle(ActionEvent e) {
            boolean isInt = isInteger(pmGoldTf.getText());
            if (isInt) {
                int addGoldVal = Integer.parseInt(pmGoldTf.getText()) + c.getGold();
                c.setGold(addGoldVal);
                gold.setText(Integer.toString(c.getGold()));
                grid.getChildren().remove(errMsg);
            }
            else {
                errMsg.setFill(Color.FIREBRICK);
                grid.add(errMsg,3,addGoldRow);
            }

        }
    });

    minusGold.setOnAction(new EventHandler<ActionEvent>() {
        Text errMsg = new Text("Inputted Value was not an Integer!");
        @Override
        public void handle(ActionEvent e) {
            boolean isInt = isInteger(pmGoldTf.getText());
            if (isInt) {
                int loseGoldVal = c.getGold() - Integer.parseInt(pmGoldTf.getText());
                if (loseGoldVal > 0) {
                    c.setGold(loseGoldVal);
                    gold.setText(Integer.toString(c.getGold()));
                }
                else {
                    c.setGold(0);
                    gold.setText(Integer.toString(c.getGold()));
                }
                grid.getChildren().remove(errMsg);
            }
            else {
                errMsg.setFill(Color.FIREBRICK);
                grid.add(errMsg,3,addGoldRow);
            }
        }
    });

    // Separate Descriptions from Health
    row++;
    row++;
    row++;

	////////////////////////////////////////////////
	////////////////////////////////////////////////
	
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

    row++;

    Label currHealthLabel = new Label("Current HP:");
    grid.add(currHealthLabel, 0, row);
    Label currHealth = new Label();
    currHealth.setText(Integer.toString(c.getCurrentHP()));
    grid.add(currHealth,1,row);

    row++;

    /////// ADD/LOSE HP ////////

    TextField pmHPTf = new TextField();
    pmHPTf.setPromptText("Add or Subtract HP");
    grid.add(pmHPTf,1,row);

    Button plusHP = new Button("add HP");
    Button minusHP = new Button("lose HP");
    HBox hbpmHP = new HBox(10);
    hbpmHP.getChildren().addAll(plusHP,minusHP);
    grid.add(hbpmHP,2,row);

    final int addHPRow = row;

    plusHP.setOnAction(new EventHandler<ActionEvent>() {
        Text errMsg = new Text("Inputted Value was not an Integer!");
        @Override
        public void handle(ActionEvent e) {
            boolean isInt = isInteger(pmHPTf.getText());
            if (isInt) {
                int addHPVal = Integer.parseInt(pmHPTf.getText()) + c.getCurrentHP();
                if (addHPVal >= c.getMaxHP()) {
                    c.setCurrentHP(c.getMaxHP());
                    currHealth.setText(Integer.toString(c.getMaxHP()));
                }
                else {
                    c.setCurrentHP(addHPVal);
                    currHealth.setText(Integer.toString(c.getCurrentHP()));
                }
                grid.getChildren().remove(errMsg);
            }
            else {
                errMsg.setFill(Color.FIREBRICK);
                grid.add(errMsg,3,addHPRow);
            }

        }
    });

    minusHP.setOnAction(new EventHandler<ActionEvent>() {
        Text errMsg = new Text("Inputted Value was not an Integer!");
        @Override
        public void handle(ActionEvent e) {
            boolean isInt = isInteger(pmHPTf.getText());
            if (isInt) {
                int loseHPVal = c.getCurrentHP() - Integer.parseInt(pmHPTf.getText());
                if (loseHPVal > 0) {
                    c.setCurrentHP(loseHPVal);
                    currHealth.setText(Integer.toString(c.getCurrentHP()));
                }
                else {
                    c.setCurrentHP(0);
                    currHealth.setText(Integer.toString(c.getCurrentHP()));
                }
                grid.getChildren().remove(errMsg);
            }
            else {
                errMsg.setFill(Color.FIREBRICK);
                grid.add(errMsg,3,addHPRow);
            }
        }
    });

    row++;

    Button setmaxhp = new Button("Refill HP");
    grid.add(setmaxhp,1,row);
    setmaxhp.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            c.setCurrentHP(c.getMaxHP());
            currHealth.setText(Integer.toString(c.getCurrentHP()));
        }
    });
    

	stage.show();	
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
