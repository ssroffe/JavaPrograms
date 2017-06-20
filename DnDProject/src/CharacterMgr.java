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
import javafx.beans.value.*;
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


        BorderPane border = new BorderPane();
        border.setPadding(new Insets(20));

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(border);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(25,25,25,25));

        Scene scene = new Scene(scroll,600,800);
        
        scene.getStylesheets().add(this.getClass().getResource("CharacterSheet.css").toExternalForm());

        stage.setScene(scene);
        int row = 1;
        Text characterName = new Text(c.getName());
        characterName.setId("characterName");
        //grid.add(characterName,1,0,2,1);


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
        
        ////////////// Exp ////////////////
        Label expLabel = new Label("Exp:");
        grid.add(expLabel,0,row);
        TextField expTf = new TextField();
        expTf.setId("locked-tf");
        expTf.setText(Integer.toString(c.getExp()));
        expTf.setEditable(false);
        grid.add(expTf,1,row);
        final int expRow = row;

        ToggleButton expbtn = new ToggleButton("edit");
        expbtn.setOnAction(new EventHandler<ActionEvent>() {
            //for checking valid new max HP
            int previousExp = c.getExp();
            Text errMsg = new Text("New Exp was not an integer!");
            @Override
            public void handle(ActionEvent e) {
                if (expbtn.isSelected()) {
                    previousExp = c.getExp();
                    expTf.setEditable(true);
                    expTf.setId("unlocked-tf");
                }
                else {
                    previousExp = c.getExp();
                    expTf.setEditable(false);
                    expTf.setId("locked-tf");
                    boolean isInt = isInteger(expTf.getText());
                    if (isInt) {
                        c.setExp(Integer.parseInt(expTf.getText()));
                        grid.getChildren().remove(errMsg);
                    }
                    else {
                        errMsg.setFill(Color.FIREBRICK);
                        grid.getChildren().remove(errMsg);
                        grid.add(errMsg,3,expRow);
                        expTf.setText(Integer.toString(previousExp));
                    }
                }
            }
        });
        grid.add(expbtn,2,row);

        row++;

        //////////// Class ////////////////
        Label clssLabel = new Label("Class:");
        grid.add(clssLabel,0,row);
        TextField clssTf = new TextField();
        clssTf.setId("locked-tf");
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
        raceTf.setId("locked-tf");
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
        subRaceTf.setId("locked-tf");
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

        ///////////// Background //////////////
        Label backgroundLabel = new Label("Background:");
        grid.add(backgroundLabel,0,row);
        TextField backgroundTf = new TextField();
        backgroundTf.setId("locked-tf");
        backgroundTf.setText(c.getBackground());
        backgroundTf.setEditable(false);
        grid.add(backgroundTf,1,row);

        ToggleButton backgroundbtn = new ToggleButton("edit");
        backgroundbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (backgroundbtn.isSelected()) {
                    backgroundTf.setEditable(true);
                    backgroundTf.setId("unlocked-tf");
                }
                else {
                    backgroundTf.setEditable(false);
                    backgroundTf.setId("locked-tf");
                    c.setBackground(backgroundTf.getText());
                }
            }
        });
        grid.add(backgroundbtn,2,row);
	row++;

    stage.show();

    ///////////// Weapon //////////////
    Label weaponLabel = new Label("Weapon:");
    grid.add(weaponLabel,0,row);
    TextField weaponTf = new TextField();
    weaponTf.setId("locked-tf");
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
    Label gold = new Label(Double.toString(c.getGold()));
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
        Text errMsg = new Text("Inputted Value was not a number!");
        @Override
        public void handle(ActionEvent e) {
            boolean isDbl = isDouble(pmGoldTf.getText());
            if (isDbl) {
                double addGoldVal = Double.parseDouble(pmGoldTf.getText()) + c.getGold();
                c.setGold(addGoldVal);
                gold.setText(Double.toString(c.getGold()));
                grid.getChildren().remove(errMsg);
            }
            else {
                errMsg.setFill(Color.FIREBRICK);
                grid.add(errMsg,3,addGoldRow);
            }

        }
    });

    minusGold.setOnAction(new EventHandler<ActionEvent>() {
        Text errMsg = new Text("Inputted Value was not a number!");
        @Override
        public void handle(ActionEvent e) {
            boolean isDbl = isDouble(pmGoldTf.getText());
            if (isDbl) {
                double loseGoldVal = c.getGold() - Double.parseDouble(pmGoldTf.getText());
                if (loseGoldVal > 0) {
                    c.setGold(loseGoldVal);
                    gold.setText(Double.toString(c.getGold()));
                }
                else {
                    c.setGold(0);
                    gold.setText(Double.toString(c.getGold()));
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
    maxHealthTf.setId("locked-tf");
	maxHealthTf.setText(Integer.toString(c.getMaxHP()));
	maxHealthTf.setEditable(false);
	grid.add(maxHealthTf,1,row);
	final int maxHealthRow = row;
	
	ToggleButton maxHealthbtn = new ToggleButton("edit");
	maxHealthbtn.setOnAction(new EventHandler<ActionEvent>() {
		//for checking valid new max HP
		int previousMaxHP = c.getMaxHP();
		Text errMaxHP = new Text("New Max HP was not an integer!");
		@Override
		public void handle(ActionEvent e) {
		    if (maxHealthbtn.isSelected()) {
                previousMaxHP = c.getMaxHP();
                maxHealthTf.setEditable(true);
                maxHealthTf.setId("unlocked-tf");
		    }
		    else {
                previousMaxHP = c.getMaxHP();
				maxHealthTf.setEditable(false);
				maxHealthTf.setId("locked-tf");
				boolean isInt = isInteger(maxHealthTf.getText());
				if (isInt) {
					c.setMaxHP(Integer.parseInt(maxHealthTf.getText()));
					grid.getChildren().remove(errMaxHP);
				}
				else {
					errMaxHP.setFill(Color.FIREBRICK);
					grid.getChildren().remove(errMaxHP);
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
    
    //////////////////////////////////////////
    //////////////////////////////////////////
    //////////////////////////////////////////
    
    // Give some space before stats
    row++;
    row++;
    row++;

    //////////////////////////////////////////
    //////////////// STATS ///////////////////
    //////////////////////////////////////////
    
    Label insp = new Label("Inspiration");
    Label prof = new Label("Proficiency");
    Label str = new Label("Strength");
    Label con = new Label("Constitution");
    Label dex = new Label("Dexterity");
    Label intel = new Label("Intelligence");
    Label wis = new Label("Wisdom");
    Label cha = new Label("Chararisma");

    TextField inspTf = new TextField();
    inspTf.setId("locked-tf");
    inspTf.setText(Integer.toString(c.getInspiration()));
    inspTf.setEditable(false);
    inspTf.setPrefWidth(40);

    TextField profTf = new TextField();
    profTf.setId("locked-tf");
    profTf.setText(Integer.toString(c.getProficiencyBonus()));
    profTf.setEditable(false);
    profTf.setPrefWidth(40);

    TextField strTf = new TextField();
    strTf.setId("locked-tf");
    strTf.setText(Integer.toString(c.getStr()));
    strTf.setEditable(false);
    strTf.setPrefWidth(40);

    TextField conTf = new TextField();
    conTf.setId("locked-tf");
    conTf.setText(Integer.toString(c.getCons()));
    conTf.setEditable(false);
    conTf.setPrefWidth(40);

    TextField dexTf = new TextField();
    dexTf.setId("locked-tf");
    dexTf.setText(Integer.toString(c.getDex()));
    dexTf.setEditable(false);
    dexTf.setPrefWidth(40);

    TextField intelTf = new TextField();
    intelTf.setId("locked-tf");
    intelTf.setText(Integer.toString(c.getInt()));
    intelTf.setEditable(false);
    intelTf.setPrefWidth(40);

    TextField wisTf = new TextField();
    wisTf.setId("locked-tf");
    wisTf.setText(Integer.toString(c.getWis()));
    wisTf.setEditable(false);
    wisTf.setPrefWidth(40);

    TextField chaTf = new TextField();
    chaTf.setId("locked-tf");
    chaTf.setText(Integer.toString(c.getChar()));
    chaTf.setEditable(false);
    chaTf.setPrefWidth(40);


    VBox statvb1 = new VBox(10);
    statvb1.getChildren().addAll(insp,inspTf,prof,profTf,str,strTf,con,conTf,dex,dexTf,intel,intelTf,wis,wisTf,cha,chaTf);


    ToggleButton statbtn = new ToggleButton("Edit Stats");
    statvb1.getChildren().add(statbtn);

    statbtn.setOnAction(new EventHandler<ActionEvent>() {
        int previousStr = c.getStr();
        int previousCon = c.getCons();
        int previousDex = c.getDex();
        int previousInt = c.getInt();
        int previousWis = c.getWis();
        int previousCha = c.getChar();
        int previousProf = c.getProficiencyBonus();
        int previousInsp = c.getInspiration();
        Text errMsg = new Text("One of the Stats was not an integer!");
        @Override
        public void handle(ActionEvent e) {
           if (statbtn.isSelected()) {
                previousStr = c.getStr();
                previousCon = c.getCons();
                previousDex = c.getDex();
                previousInt = c.getInt();
                previousWis = c.getWis();
                previousCha = c.getChar();
                previousProf = c.getProficiencyBonus();
                previousInsp = c.getInspiration();

                strTf.setEditable(true);
                conTf.setEditable(true);
                dexTf.setEditable(true);
                intelTf.setEditable(true);
                wisTf.setEditable(true);
                chaTf.setEditable(true);
                profTf.setEditable(true);
                inspTf.setEditable(true);

                strTf.setId("unlocked-tf");
                conTf.setId("unlocked-tf");
                dexTf.setId("unlocked-tf");
                intelTf.setId("unlocked-tf");
                wisTf.setId("unlocked-tf");
                chaTf.setId("unlocked-tf");
                profTf.setId("unlocked-tf");
                inspTf.setId("unlocked-tf");

            }
            else {
                strTf.setEditable(false);
                conTf.setEditable(false);
                dexTf.setEditable(false);
                intelTf.setEditable(false);
                wisTf.setEditable(false);
                chaTf.setEditable(false);
                profTf.setEditable(false);
                inspTf.setEditable(false);

                strTf.setId("locked-tf");
                conTf.setId("locked-tf");
                dexTf.setId("locked-tf");
                intelTf.setId("locked-tf");
                wisTf.setId("locked-tf");
                chaTf.setId("locked-tf");
                profTf.setId("locked-tf");
                inspTf.setId("locked-tf");

                boolean strIsInt = isInteger(strTf.getText());
                boolean conIsInt = isInteger(conTf.getText());
                boolean dexIsInt = isInteger(dexTf.getText());
                boolean intelIsInt = isInteger(intelTf.getText());
                boolean wisIsInt = isInteger(wisTf.getText());
                boolean chaIsInt = isInteger(chaTf.getText());
                boolean profIsInt = isInteger(profTf.getText());
                boolean inspIsInt = isInteger(inspTf.getText());

                if (strIsInt && conIsInt && dexIsInt && intelIsInt && wisIsInt && chaIsInt && profIsInt && inspIsInt) {
                    c.setStr(Integer.parseInt(strTf.getText()));
                    c.setCons(Integer.parseInt(conTf.getText()));
                    c.setDex(Integer.parseInt(dexTf.getText()));
                    c.setInt(Integer.parseInt(intelTf.getText()));
                    c.setWis(Integer.parseInt(wisTf.getText()));
                    c.setChar(Integer.parseInt(chaTf.getText()));
                    c.setProficiencyBonus(Integer.parseInt(profTf.getText()));
                    c.setInspiration(Integer.parseInt(inspTf.getText()));
                    statvb1.getChildren().remove(errMsg);
                }
                else {
                    errMsg.setFill(Color.FIREBRICK);
                    statvb1.getChildren().remove(errMsg);
                    statvb1.getChildren().add(errMsg);
                    strTf.setText(Integer.toString(previousStr));
                    conTf.setText(Integer.toString(previousCon));
                    dexTf.setText(Integer.toString(previousDex));
                    intelTf.setText(Integer.toString(previousInt));
                    wisTf.setText(Integer.toString(previousWis));
                    chaTf.setText(Integer.toString(previousCon));
                    profTf.setText(Integer.toString(previousProf));
                    inspTf.setText(Integer.toString(previousInsp));

                }
            }
        }
    });
    
    ///////////////////////////////////////
    ////////// OTHER STATS ////////////////
    ///////////////////////////////////////
    Label armor = new Label("Armor:");
    Label init = new Label("Initiative:");
    Label speed = new Label("Speed:");
    Label hitdice = new Label("Hit Dice:");

    TextField armorTf = new TextField();
    armorTf.setId("locked-tf");
    armorTf.setText(Integer.toString(c.getArmor()));
    armorTf.setEditable(false);
    armorTf.setPrefWidth(40);

    TextField initTf = new TextField();
    initTf.setId("locked-tf");
    initTf.setText(Integer.toString(c.getInitiative()));
    initTf.setEditable(false);
    initTf.setPrefWidth(40);

    TextField speedTf = new TextField();
    speedTf.setId("locked-tf");
    speedTf.setText(Integer.toString(c.getSpeed()));
    speedTf.setEditable(false);
    speedTf.setPrefWidth(40);

    TextField hdTf = new TextField();
    hdTf.setId("locked-tf");
    hdTf.setText(c.getHitDice());
    hdTf.setEditable(false);
    hdTf.setPrefWidth(60);

    HBox stathb0 = new HBox(10);
    stathb0.getChildren().addAll(armor,armorTf,init,initTf,speed,speedTf,hitdice,hdTf);

    grid.add(stathb0,1,row);

    final int statsRow = row;

    ToggleButton otherStatbtn = new ToggleButton("edit");
    statvb1.getChildren().add(otherStatbtn);
    grid.add(otherStatbtn,2,row);

    otherStatbtn.setOnAction(new EventHandler<ActionEvent>() {
        int previousArmor = c.getArmor();
        int previousInit = c.getInitiative();
        int previousSpeed = c.getSpeed();
        String previousHitDice = c.getHitDice();
        Text errMsg = new Text("One of the Stats was not an integer!");
        @Override
        public void handle(ActionEvent e) {
           if (otherStatbtn.isSelected()) {
                previousArmor = c.getArmor();
                previousInit = c.getInitiative();
                previousSpeed = c.getSpeed();
                previousHitDice = c.getHitDice();
                armorTf.setEditable(true);
                initTf.setEditable(true);
                speedTf.setEditable(true);
                hdTf.setEditable(true);

                armorTf.setId("unlocked-tf");
                initTf.setId("unlocked-tf");
                speedTf.setId("unlocked-tf");
                hdTf.setId("unlocked-tf");
           }
           else {
               armorTf.setEditable(false);
               initTf.setEditable(false);
               speedTf.setEditable(false);
               hdTf.setEditable(false);
               armorTf.setId("locked-tf");
               speedTf.setId("locked-tf");
               initTf.setId("locked-tf");
               hdTf.setId("locked-tf");

               boolean armorIsInt = isInteger(armorTf.getText());
               boolean speedIsInt = isInteger(speedTf.getText());
               boolean initIsInt = isInteger(initTf.getText());
               if (armorIsInt && speedIsInt && initIsInt) {
                   c.setArmor(Integer.parseInt(armorTf.getText()));
                   c.setInitiative(Integer.parseInt(initTf.getText()));
                   c.setSpeed(Integer.parseInt(speedTf.getText()));
                   c.setHitDice(hdTf.getText());
                   grid.getChildren().remove(errMsg);
               }
               else {
                   errMsg.setFill(Color.FIREBRICK);
                   grid.getChildren().remove(errMsg);
                   grid.add(errMsg,3,statsRow);
                   armorTf.setText(Integer.toString(previousArmor));
                   speedTf.setText(Integer.toString(previousSpeed));
                   initTf.setText(Integer.toString(previousInit));
                   hdTf.setText(previousHitDice);
               }
           }
        }
    });
    row++;
    row++;

    ///////////////////////////////////////////
    ///////////////////////////////////////////

    Button skillsBtn = new Button("Skills");
    Button spellsBtn = new Button("Spells");
    Button inventoryBtn = new Button("Inventory"); 
    Button idealsBtn = new Button("Ideals");
    Button descriptionBtn = new Button("Description");
    Button featuresBtn = new Button("Features");
    Button languagesBtn = new Button("Languages");
    Button bondsBtn = new Button("Bonds");
    Button flawsBtn = new Button("Flaws");
    Button notesBtn = new Button("Notes");


    HBox hbbtns1 = new HBox(10);
    hbbtns1.setAlignment(Pos.CENTER);
    hbbtns1.getChildren().addAll(skillsBtn,spellsBtn,inventoryBtn,languagesBtn);
    HBox hbbtns2 = new HBox(10);
    hbbtns2.setAlignment(Pos.CENTER);
    hbbtns2.getChildren().addAll(featuresBtn,idealsBtn,bondsBtn,flawsBtn,descriptionBtn);

    HBox hbbtns3 = new HBox(10);
    hbbtns3.setAlignment(Pos.CENTER);
    hbbtns3.getChildren().addAll(notesBtn);

    VBox vbbtns = new VBox(10);
    vbbtns.setAlignment(Pos.CENTER);
    vbbtns.getChildren().addAll(hbbtns1,hbbtns2,hbbtns3);
    grid.add(vbbtns,1,row);

    row++;

    ///////////// Save Button /////////////////
    Button save = new Button("Save Character");
    save.setId("saveBtn");
    HBox hbsave = new HBox(10);
    hbsave.getChildren().add(save);
    hbsave.setAlignment(Pos.BOTTOM_RIGHT);

    grid.add(hbsave,1,row);
    int saveRow = row;

    save.setOnAction(new EventHandler<ActionEvent>() {
        Text notice = new Text("Character was saved to "+c.getName()+".json");
        @Override
        public void handle(ActionEvent e) {
            Character.SaveCharacter(c);
            notice.setFill(Color.FIREBRICK);
            grid.getChildren().remove(notice);
            grid.add(notice,2,saveRow);
        }
    });


    ///////////////////////////////
    ///////// SKILLS PAGE /////////
    ///////////////////////////////

    skillsBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Stage skillsStage = new Stage();
            skillsStage.setTitle("Skills Page");

            Label skillsTitle = new Label("Skills");
            skillsTitle.setId("title");
            VBox vbSkills = new VBox(10);

            HashSet<String> skillsList = c.getSkills();
            Iterator<String> itr = skillsList.iterator();

            while (itr.hasNext()) {
                String nxtItem = itr.next();
                Label skillslabel = new Label(nxtItem);
                HBox hbSkillsList = new HBox(10);

                Button rm = new Button("remove");
                hbSkillsList.getChildren().addAll(skillslabel,rm);

                vbSkills.getChildren().add(hbSkillsList);

                ////// Remove button ///////
                rm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Stage confirmRm = new Stage();
                        confirmRm.setTitle("Are you sure?");
                        GridPane rmgrid = new GridPane();
                        rmgrid.setAlignment(Pos.CENTER);
                        rmgrid.setHgap(10);
                        rmgrid.setVgap(10);
                        Scene rmscene = new Scene(rmgrid,400,150);
                        confirmRm.setScene(rmscene);
                        confirmRm.show();

                        Label rmLabel = new Label("remove " + nxtItem + ". Are you sure?");
                        rmgrid.add(rmLabel,0,0);
                        Button yesRm = new Button("Yes");
                        Button noRm = new Button("Cancel");
                        HBox hbynrm = new HBox(10);
                        hbynrm.getChildren().addAll(yesRm,noRm);
                        rmgrid.add(hbynrm,0,1);

                        yesRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                vbSkills.getChildren().remove(hbSkillsList);
                                skillsList.remove(skillslabel.getText());
                                c.setSkills(skillsList);
                                confirmRm.close();
                            }
                        });
                        noRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                confirmRm.close();
                            }
                        });
                    }
                });

            }

	    TextField addSkillsTf = new TextField();
        addSkillsTf.setPromptText("Add a Skill");
        Button addSkills = new Button("Add Skill");
        Button doneSkills = new Button("Done");
        HBox hbaddSkills = new HBox(10);
        hbaddSkills.getChildren().addAll(addSkillsTf,addSkills);
	    VBox vbskillsBtns = new VBox(10);
	    vbskillsBtns.getChildren().addAll(hbaddSkills,doneSkills);

        ScrollPane skillsSp = new ScrollPane();
        skillsSp.setContent(vbSkills);


        BorderPane bpSkills = new BorderPane();
        bpSkills.setPadding(new Insets(20));
        bpSkills.setMargin(skillsTitle,new Insets(12,12,12,12));
        bpSkills.setMargin(skillsSp,new Insets(10,10,10,10));
        bpSkills.setTop(skillsTitle);
        bpSkills.setCenter(skillsSp);
        bpSkills.setBottom(vbskillsBtns);


        Scene skillsscene = new Scene(bpSkills);
        skillsscene.getStylesheets().add(this.getClass().getResource("ListPage.css").toExternalForm());

        skillsStage.setScene(skillsscene);

        //////// Add Skills //////////
        addSkills.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                skillsList.add(addSkillsTf.getText());
                c.setSkills(skillsList);
                Label newSkills = new Label(addSkillsTf.getText());
                vbSkills.getChildren().add(newSkills);
                addSkillsTf.clear();

            }
        });




        doneSkills.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                c.setSkills(skillsList);
                skillsStage.close();
            }
        });

        skillsStage.show();

        }
    });


    //////////////////////////////////////
    ////////// Inventory Page ////////////
    //////////////////////////////////////

    inventoryBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Stage inventoryStage = new Stage();
            inventoryStage.setTitle("Inventory Page");



            Label inventoryTitle = new Label("Inventory");
            inventoryTitle.setId("title");
            VBox vbInventory = new VBox(10);

            ArrayList<String> inventoryList = c.getInventory();
            Iterator<String> itr = inventoryList.iterator();

            while (itr.hasNext()) {
                String nxtItem = itr.next();
                Label inventorylabel = new Label(nxtItem);
                HBox hbInventoryList = new HBox(10);

                Button rm = new Button("remove");
                hbInventoryList.getChildren().addAll(inventorylabel,rm);

                vbInventory.getChildren().add(hbInventoryList);

                ////// Remove button ///////
                rm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Stage confirmRm = new Stage();
                        confirmRm.setTitle("Are you sure?");
                        GridPane rmgrid = new GridPane();
                        rmgrid.setAlignment(Pos.CENTER);
                        rmgrid.setHgap(10);
                        rmgrid.setVgap(10);
                        Scene rmscene = new Scene(rmgrid,400,150);
                        confirmRm.setScene(rmscene);
                        confirmRm.show();

                        Label rmLabel = new Label("remove " + nxtItem + ". Are you sure?");
                        rmgrid.add(rmLabel,0,0);
                        Button yesRm = new Button("Yes");
                        Button noRm = new Button("Cancel");
                        HBox hbynrm = new HBox(10);
                        hbynrm.getChildren().addAll(yesRm,noRm);
                        rmgrid.add(hbynrm,0,1);

                        yesRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                vbInventory.getChildren().remove(hbInventoryList);
                                inventoryList.remove(inventorylabel.getText());
                                c.setInventory(inventoryList);
                                confirmRm.close();
                            }
                        });
                        noRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                confirmRm.close();
                            }
                        });
                    }
                });

            }

	    TextField addInventoryTf = new TextField();
	    addInventoryTf.setPromptText("Add an Item");
        Button addInventory = new Button("Add Item");
        Button doneInventory = new Button("Done");
        HBox hbaddInventory = new HBox(10);
        hbaddInventory.getChildren().addAll(addInventoryTf,addInventory);
        VBox vbinventoryBtns = new VBox(10);
        vbinventoryBtns.getChildren().addAll(hbaddInventory,doneInventory);

        ScrollPane inventorySp = new ScrollPane();
        inventorySp.setContent(vbInventory);

        BorderPane bpInventory = new BorderPane();
        bpInventory.setPadding(new Insets(20));
        bpInventory.setMargin(inventoryTitle,new Insets(12,12,12,12));
        bpInventory.setMargin(inventorySp,new Insets(10,10,10,10));
        bpInventory.setTop(inventoryTitle);
        bpInventory.setCenter(inventorySp);
        bpInventory.setBottom(vbinventoryBtns);


        Scene inventoryscene = new Scene(bpInventory);
        inventoryscene.getStylesheets().add(this.getClass().getResource("ListPage.css").toExternalForm());

        inventoryStage.setScene(inventoryscene);

        //////// Add Inventory //////////
        addInventory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                inventoryList.add(addInventoryTf.getText());
                c.setInventory(inventoryList);
                Label newInventory = new Label(addInventoryTf.getText());
                vbInventory.getChildren().add(newInventory);
                addInventoryTf.clear();

            }
        });




        doneInventory.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                c.setInventory(inventoryList);
                inventoryStage.close();
            }
        });

        inventoryStage.show();

        }
    });

    //////////////////////////////////
    ///////// LANGUAGES PAGE /////////
    //////////////////////////////////

    languagesBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Stage languagesStage = new Stage();
            languagesStage.setTitle("Languages Page");



            Label languagesTitle = new Label("Languages");
            languagesTitle.setId("title");
            VBox vbLanguages = new VBox(10);

            HashSet<String> languagesList = c.getLanguages();
            Iterator<String> itr = languagesList.iterator();

            while (itr.hasNext()) {
                String nxtItem = itr.next();
                Label languageslabel = new Label(nxtItem);
                HBox hbLanguagesList = new HBox(10);

                Button rm = new Button("remove");
                hbLanguagesList.getChildren().addAll(languageslabel,rm);

                vbLanguages.getChildren().add(hbLanguagesList);

                ////// Remove button ///////
                rm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Stage confirmRm = new Stage();
                        confirmRm.setTitle("Are you sure?");
                        GridPane rmgrid = new GridPane();
                        rmgrid.setAlignment(Pos.CENTER);
                        rmgrid.setHgap(10);
                        rmgrid.setVgap(10);
                        Scene rmscene = new Scene(rmgrid,400,150);
                        confirmRm.setScene(rmscene);
                        confirmRm.show();

                        Label rmLabel = new Label("remove " + nxtItem + ". Are you sure?");
                        rmgrid.add(rmLabel,0,0);
                        Button yesRm = new Button("Yes");
                        Button noRm = new Button("Cancel");
                        HBox hbynrm = new HBox(10);
                        hbynrm.getChildren().addAll(yesRm,noRm);
                        rmgrid.add(hbynrm,0,1);

                        yesRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                vbLanguages.getChildren().remove(hbLanguagesList);
                                languagesList.remove(languageslabel.getText());
                                c.setLanguages(languagesList);
                                confirmRm.close();
                            }
                        });
                        noRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                confirmRm.close();
                            }
                        });
                    }
                });

            }

	    TextField addLanguagesTf = new TextField();
	    addLanguagesTf.setPromptText("Add a Language");
        Button addLanguages = new Button("Add Language");
        Button doneLanguages = new Button("Done");
        HBox hbaddLanguages = new HBox(10);
        hbaddLanguages.getChildren().addAll(addLanguagesTf,addLanguages);
        VBox vblanguagesBtns = new VBox(10);
        vblanguagesBtns.getChildren().addAll(hbaddLanguages,doneLanguages);

        ScrollPane languagesSp = new ScrollPane();
        languagesSp.setContent(vbLanguages);

        BorderPane bpLanguages = new BorderPane();
        bpLanguages.setPadding(new Insets(20));
        bpLanguages.setMargin(languagesTitle,new Insets(12,12,12,12));
        bpLanguages.setMargin(languagesSp,new Insets(10,10,10,10));
        bpLanguages.setTop(languagesTitle);
        bpLanguages.setCenter(languagesSp);
        bpLanguages.setBottom(vblanguagesBtns);


        Scene languagesscene = new Scene(bpLanguages);
        languagesscene.getStylesheets().add(this.getClass().getResource("ListPage.css").toExternalForm());

        languagesStage.setScene(languagesscene);

        //////// Add Languages //////////
        addLanguages.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                languagesList.add(addLanguagesTf.getText());
                c.setLanguages(languagesList);
                Label newLanguages = new Label(addLanguagesTf.getText());
                vbLanguages.getChildren().add(newLanguages);
                addLanguagesTf.clear();

            }
        });




        doneLanguages.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                c.setLanguages(languagesList);
                languagesStage.close();
            }
        });

        languagesStage.show();

        }
    });

    //////////////////////////////////
    ///////// IDEALS PAGE ////////////
    //////////////////////////////////

    idealsBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Stage idealsStage = new Stage();
            idealsStage.setTitle("Ideals Page");



            Label idealsTitle = new Label("Ideals");
            idealsTitle.setId("title");
            VBox vbIdeals = new VBox(10);

            HashSet<String> idealsList = c.getIdeals();
            Iterator<String> itr = idealsList.iterator();

            while (itr.hasNext()) {
                String nxtItem = itr.next();
                Label idealslabel = new Label(nxtItem);
                HBox hbIdealsList = new HBox(10);

                Button rm = new Button("remove");
                hbIdealsList.getChildren().addAll(idealslabel,rm);

                vbIdeals.getChildren().add(hbIdealsList);

                ////// Remove button ///////
                rm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Stage confirmRm = new Stage();
                        confirmRm.setTitle("Are you sure?");
                        GridPane rmgrid = new GridPane();
                        rmgrid.setAlignment(Pos.CENTER);
                        rmgrid.setHgap(10);
                        rmgrid.setVgap(10);
                        Scene rmscene = new Scene(rmgrid,400,150);
                        confirmRm.setScene(rmscene);
                        confirmRm.show();

                        Label rmLabel = new Label("remove " + nxtItem + ". Are you sure?");
                        rmgrid.add(rmLabel,0,0);
                        Button yesRm = new Button("Yes");
                        Button noRm = new Button("Cancel");
                        HBox hbynrm = new HBox(10);
                        hbynrm.getChildren().addAll(yesRm,noRm);
                        rmgrid.add(hbynrm,0,1);

                        yesRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                vbIdeals.getChildren().remove(hbIdealsList);
                                idealsList.remove(idealslabel.getText());
                                c.setIdeals(idealsList);
                                confirmRm.close();
                            }
                        });
                        noRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                confirmRm.close();
                            }
                        });
                    }
                });

            }

	    TextField addIdealsTf = new TextField();
	    addIdealsTf.setPromptText("Add a Language");
        Button addIdeals = new Button("Add Language");
        Button doneIdeals = new Button("Done");
        HBox hbaddIdeals = new HBox(10);
        hbaddIdeals.getChildren().addAll(addIdealsTf,addIdeals);
        VBox vbidealsBtns = new VBox(10);
        vbidealsBtns.getChildren().addAll(hbaddIdeals,doneIdeals);

        ScrollPane idealsSp = new ScrollPane();
        idealsSp.setContent(vbIdeals);

        BorderPane bpIdeals = new BorderPane();
        bpIdeals.setPadding(new Insets(20));
        bpIdeals.setMargin(idealsTitle,new Insets(12,12,12,12));
        bpIdeals.setMargin(idealsSp,new Insets(10,10,10,10));
        bpIdeals.setTop(idealsTitle);
        bpIdeals.setCenter(idealsSp);
        bpIdeals.setBottom(vbidealsBtns);


        Scene idealsscene = new Scene(bpIdeals);
        idealsscene.getStylesheets().add(this.getClass().getResource("ListPage.css").toExternalForm());

        idealsStage.setScene(idealsscene);

        //////// Add Ideals //////////
        addIdeals.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                idealsList.add(addIdealsTf.getText());
                c.setIdeals(idealsList);
                Label newIdeals = new Label(addIdealsTf.getText());
                vbIdeals.getChildren().add(newIdeals);
                addIdealsTf.clear();

            }
        });




        doneIdeals.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                c.setIdeals(idealsList);
                idealsStage.close();
            }
        });

        idealsStage.show();

        }
    });

    //////////////////////////////////
    ///////// FLAWS PAGE /////////////
    //////////////////////////////////

    flawsBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Stage flawsStage = new Stage();
            flawsStage.setTitle("Flaws Page");



            Label flawsTitle = new Label("Flaws");
            flawsTitle.setId("title");
            VBox vbFlaws = new VBox(10);

            HashSet<String> flawsList = c.getFlaws();
            Iterator<String> itr = flawsList.iterator();

            while (itr.hasNext()) {
                String nxtItem = itr.next();
                Label flawslabel = new Label(nxtItem);
                HBox hbFlawsList = new HBox(10);

                Button rm = new Button("remove");
                hbFlawsList.getChildren().addAll(flawslabel,rm);

                vbFlaws.getChildren().add(hbFlawsList);

                ////// Remove button ///////
                rm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Stage confirmRm = new Stage();
                        confirmRm.setTitle("Are you sure?");
                        GridPane rmgrid = new GridPane();
                        rmgrid.setAlignment(Pos.CENTER);
                        rmgrid.setHgap(10);
                        rmgrid.setVgap(10);
                        Scene rmscene = new Scene(rmgrid,400,150);
                        confirmRm.setScene(rmscene);
                        confirmRm.show();

                        Label rmLabel = new Label("remove " + nxtItem + ". Are you sure?");
                        rmgrid.add(rmLabel,0,0);
                        Button yesRm = new Button("Yes");
                        Button noRm = new Button("Cancel");
                        HBox hbynrm = new HBox(10);
                        hbynrm.getChildren().addAll(yesRm,noRm);
                        rmgrid.add(hbynrm,0,1);

                        yesRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                vbFlaws.getChildren().remove(hbFlawsList);
                                flawsList.remove(flawslabel.getText());
                                c.setFlaws(flawsList);
                                confirmRm.close();
                            }
                        });
                        noRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                confirmRm.close();
                            }
                        });
                    }
                });

            }

	    TextField addFlawsTf = new TextField();
	    addFlawsTf.setPromptText("Add a Language");
        Button addFlaws = new Button("Add Language");
        Button doneFlaws = new Button("Done");
        HBox hbaddFlaws = new HBox(10);
        hbaddFlaws.getChildren().addAll(addFlawsTf,addFlaws);
        VBox vbflawsBtns = new VBox(10);
        vbflawsBtns.getChildren().addAll(hbaddFlaws,doneFlaws);

        ScrollPane flawsSp = new ScrollPane();
        flawsSp.setContent(vbFlaws);

        BorderPane bpFlaws = new BorderPane();
        bpFlaws.setPadding(new Insets(20));
        bpFlaws.setMargin(flawsTitle,new Insets(12,12,12,12));
        bpFlaws.setMargin(flawsSp,new Insets(10,10,10,10));
        bpFlaws.setTop(flawsTitle);
        bpFlaws.setCenter(flawsSp);
        bpFlaws.setBottom(vbflawsBtns);


        Scene flawsscene = new Scene(bpFlaws);
        flawsscene.getStylesheets().add(this.getClass().getResource("ListPage.css").toExternalForm());

        flawsStage.setScene(flawsscene);

        //////// Add Flaws //////////
        addFlaws.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                flawsList.add(addFlawsTf.getText());
                c.setFlaws(flawsList);
                Label newFlaws = new Label(addFlawsTf.getText());
                vbFlaws.getChildren().add(newFlaws);
                addFlawsTf.clear();

            }
        });




        doneFlaws.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                c.setFlaws(flawsList);
                flawsStage.close();
            }
        });

        flawsStage.show();

        }
    });

    //////////////////////////////////
    ///////// BONDS PAGE /////////////
    //////////////////////////////////

    bondsBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Stage bondsStage = new Stage();
            bondsStage.setTitle("Bonds Page");



            Label bondsTitle = new Label("Bonds");
            bondsTitle.setId("title");
            VBox vbBonds = new VBox(10);

            HashSet<String> bondsList = c.getBonds();
            Iterator<String> itr = bondsList.iterator();

            while (itr.hasNext()) {
                String nxtItem = itr.next();
                Label bondslabel = new Label(nxtItem);
                HBox hbBondsList = new HBox(10);

                Button rm = new Button("remove");
                hbBondsList.getChildren().addAll(bondslabel,rm);

                vbBonds.getChildren().add(hbBondsList);

                ////// Remove button ///////
                rm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Stage confirmRm = new Stage();
                        confirmRm.setTitle("Are you sure?");
                        GridPane rmgrid = new GridPane();
                        rmgrid.setAlignment(Pos.CENTER);
                        rmgrid.setHgap(10);
                        rmgrid.setVgap(10);
                        Scene rmscene = new Scene(rmgrid,400,150);
                        confirmRm.setScene(rmscene);
                        confirmRm.show();

                        Label rmLabel = new Label("remove " + nxtItem + ". Are you sure?");
                        rmgrid.add(rmLabel,0,0);
                        Button yesRm = new Button("Yes");
                        Button noRm = new Button("Cancel");
                        HBox hbynrm = new HBox(10);
                        hbynrm.getChildren().addAll(yesRm,noRm);
                        rmgrid.add(hbynrm,0,1);

                        yesRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                vbBonds.getChildren().remove(hbBondsList);
                                bondsList.remove(bondslabel.getText());
                                c.setBonds(bondsList);
                                confirmRm.close();
                            }
                        });
                        noRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                confirmRm.close();
                            }
                        });
                    }
                });

            }

	    TextField addBondsTf = new TextField();
	    addBondsTf.setPromptText("Add a Language");
        Button addBonds = new Button("Add Language");
        Button doneBonds = new Button("Done");
        HBox hbaddBonds = new HBox(10);
        hbaddBonds.getChildren().addAll(addBondsTf,addBonds);
        VBox vbbondsBtns = new VBox(10);
        vbbondsBtns.getChildren().addAll(hbaddBonds,doneBonds);

        ScrollPane bondsSp = new ScrollPane();
        bondsSp.setContent(vbBonds);

        BorderPane bpBonds = new BorderPane();
        bpBonds.setPadding(new Insets(20));
        bpBonds.setMargin(bondsTitle,new Insets(12,12,12,12));
        bpBonds.setMargin(bondsSp,new Insets(10,10,10,10));
        bpBonds.setTop(bondsTitle);
        bpBonds.setCenter(bondsSp);
        bpBonds.setBottom(vbbondsBtns);


        Scene bondsscene = new Scene(bpBonds);
        bondsscene.getStylesheets().add(this.getClass().getResource("ListPage.css").toExternalForm());

        bondsStage.setScene(bondsscene);

        //////// Add Bonds //////////
        addBonds.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                bondsList.add(addBondsTf.getText());
                c.setBonds(bondsList);
                Label newBonds = new Label(addBondsTf.getText());
                vbBonds.getChildren().add(newBonds);
                addBondsTf.clear();

            }
        });




        doneBonds.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                c.setBonds(bondsList);
                bondsStage.close();
            }
        });

        bondsStage.show();

        }
    });
    
    /////////////////////////////////////
    ///////// FEATURES PAGE /////////////
    /////////////////////////////////////

    featuresBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Stage featuresStage = new Stage();
            featuresStage.setTitle("Features Page");



            Label featuresTitle = new Label("Features");
            featuresTitle.setId("title");
            VBox vbFeatures = new VBox(10);

            HashSet<String> featuresList = c.getFeatures();
            Iterator<String> itr = featuresList.iterator();

            while (itr.hasNext()) {
                String nxtItem = itr.next();
                Label featureslabel = new Label(nxtItem);
                HBox hbFeaturesList = new HBox(10);

                Button rm = new Button("remove");
                hbFeaturesList.getChildren().addAll(featureslabel,rm);

                vbFeatures.getChildren().add(hbFeaturesList);

                ////// Remove button ///////
                rm.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        Stage confirmRm = new Stage();
                        confirmRm.setTitle("Are you sure?");
                        GridPane rmgrid = new GridPane();
                        rmgrid.setAlignment(Pos.CENTER);
                        rmgrid.setHgap(10);
                        rmgrid.setVgap(10);
                        Scene rmscene = new Scene(rmgrid,400,150);
                        confirmRm.setScene(rmscene);
                        confirmRm.show();

                        Label rmLabel = new Label("remove " + nxtItem + ". Are you sure?");
                        rmgrid.add(rmLabel,0,0);
                        Button yesRm = new Button("Yes");
                        Button noRm = new Button("Cancel");
                        HBox hbynrm = new HBox(10);
                        hbynrm.getChildren().addAll(yesRm,noRm);
                        rmgrid.add(hbynrm,0,1);

                        yesRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                vbFeatures.getChildren().remove(hbFeaturesList);
                                featuresList.remove(featureslabel.getText());
                                c.setFeatures(featuresList);
                                confirmRm.close();
                            }
                        });
                        noRm.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                confirmRm.close();
                            }
                        });
                    }
                });

            }

	    TextField addFeaturesTf = new TextField();
	    addFeaturesTf.setPromptText("Add a Language");
        Button addFeatures = new Button("Add Language");
        Button doneFeatures = new Button("Done");
        HBox hbaddFeatures = new HBox(10);
        hbaddFeatures.getChildren().addAll(addFeaturesTf,addFeatures);
        VBox vbfeaturesBtns = new VBox(10);
        vbfeaturesBtns.getChildren().addAll(hbaddFeatures,doneFeatures);

        ScrollPane featuresSp = new ScrollPane();
        featuresSp.setContent(vbFeatures);

        BorderPane bpFeatures = new BorderPane();
        bpFeatures.setPadding(new Insets(20));
        bpFeatures.setMargin(featuresTitle,new Insets(12,12,12,12));
        bpFeatures.setMargin(featuresSp,new Insets(10,10,10,10));
        bpFeatures.setTop(featuresTitle);
        bpFeatures.setCenter(featuresSp);
        bpFeatures.setBottom(vbfeaturesBtns);


        Scene featuresscene = new Scene(bpFeatures);
        featuresscene.getStylesheets().add(this.getClass().getResource("ListPage.css").toExternalForm());

        featuresStage.setScene(featuresscene);

        //////// Add Features //////////
        addFeatures.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                featuresList.add(addFeaturesTf.getText());
                c.setFeatures(featuresList);
                Label newFeatures = new Label(addFeaturesTf.getText());
                vbFeatures.getChildren().add(newFeatures);
                addFeaturesTf.clear();

            }
        });




        doneFeatures.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                c.setFeatures(featuresList);
                featuresStage.close();
            }
        });

        featuresStage.show();

        }
    });
    
    ////////////////////////////////////
    ////////////// NOTES ///////////////
    ////////////////////////////////////
    
    notesBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Stage notesStage = new Stage();
            notesStage.setTitle("Notes Page");
	    
	    
	    
            Label notesTitle = new Label("Notes");
            notesTitle.setId("title");
            VBox vbNotes = new VBox(10);
	    
            String notesList = c.getNotes();
	    
	    TextArea notes = new TextArea(c.getNotes());
	    Button saveNotes = new Button("Save Notes");
	    Button doneNotes = new Button("Done");
	    HBox hbNotes = new HBox(10);
	    hbNotes.getChildren().addAll(saveNotes,doneNotes);


        ScrollPane notesSp = new ScrollPane();
        notesSp.setContent(vbNotes);

        BorderPane bpNotes = new BorderPane();
        bpNotes.setPadding(new Insets(20));
        bpNotes.setMargin(notesTitle,new Insets(12,12,12,12));
        bpNotes.setMargin(notes,new Insets(10,10,10,10));
        bpNotes.setTop(notesTitle);
        bpNotes.setCenter(notes);
        bpNotes.setBottom(hbNotes);


        Scene notesscene = new Scene(bpNotes);
        notesscene.getStylesheets().add(this.getClass().getResource("TextAreaPage.css").toExternalForm());

        notesStage.setScene(notesscene);

        //////// Save Notes //////////
        saveNotes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
		c.setNotes(notes.getText());
		Text msg = new Text("Notes Saved");
		msg.setFill(Color.FIREBRICK);
		hbNotes.getChildren().remove(msg);
		hbNotes.getChildren().add(msg);

            }
        });


        doneNotes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                c.setNotes(notes.getText());
                notesStage.close();
            }
        });

        notesStage.show();

        }
    });
    
    //////////////////////////////////////////
    ////////////// DESCRIPTION ///////////////
    //////////////////////////////////////////
    
    descriptionBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Stage descriptionStage = new Stage();
            descriptionStage.setTitle("Description Page");
	    
	    
	    
            Label descriptionTitle = new Label("Description");
            descriptionTitle.setId("title");
            VBox vbDescription = new VBox(10);
	    
            String descriptionList = c.getDescription();
	    
	    TextArea description = new TextArea(c.getDescription());
	    Button saveDescription = new Button("Save Description");
	    Button doneDescription = new Button("Done");
	    HBox hbDescription = new HBox(10);
	    hbDescription.getChildren().addAll(saveDescription,doneDescription);


        ScrollPane descriptionSp = new ScrollPane();
        descriptionSp.setContent(vbDescription);

        BorderPane bpDescription = new BorderPane();
        bpDescription.setPadding(new Insets(20));
        bpDescription.setMargin(descriptionTitle,new Insets(12,12,12,12));
        bpDescription.setMargin(description,new Insets(10,10,10,10));
        bpDescription.setTop(descriptionTitle);
        bpDescription.setCenter(description);
        bpDescription.setBottom(hbDescription);


        Scene descriptionscene = new Scene(bpDescription);
        descriptionscene.getStylesheets().add(this.getClass().getResource("TextAreaPage.css").toExternalForm());

        descriptionStage.setScene(descriptionscene);

        //////// Save Description //////////
        saveDescription.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
		c.setDescription(description.getText());
		Text msg = new Text("Description Saved");
		msg.setFill(Color.FIREBRICK);
		hbDescription.getChildren().remove(msg);
		hbDescription.getChildren().add(msg);

            }
        });


        doneDescription.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                c.setDescription(description.getText());
                descriptionStage.close();
            }
        });

        descriptionStage.show();

        }
    });


    ////////////////////////////////////
    /////////// SPELLS PAGE ////////////
    ////////////////////////////////////
    
    spellsBtn.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            Stage spellsStage = new Stage();
            spellsStage.setTitle("Spells Page");

            Label spellsTitle = new Label("Spells");
            descriptionTitle.setId("title");

            BorderPane spellsBp = new BorderPane();
            spellsBp.setPadding(new Insets(20));
            spellsBp.setMargin(spellsTitle,new Insets(12,12,12,12));

            ScrollPane spellsSp = new ScrollPane();
            spellsSp.setContent(spellsBp);
            
            Scene spellsscene = new scene(spellsSp);

            spellsStage.setScene(spellsscene);
            

        }
    });
       


    ////////////////////////////////////////////
    ////////////////////////////////////////////
    
    ////////////////////////////////////////////
    ///////////// Border Setup /////////////////
    ////////////////////////////////////////////
    

    border.setMargin(characterName, new Insets(10,10,10,10));
    
    border.setTop(characterName);
    border.setCenter(grid);
    border.setBottom(hbsave);
    border.setLeft(statvb1);

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

    public static boolean isDouble(String s) {
	try {
	    Double.parseDouble(s);
	    return true;
	}
	catch (NumberFormatException ex) {
	    return false;
	}
    }
    
}
