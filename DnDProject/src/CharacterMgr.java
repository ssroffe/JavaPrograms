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
        
        scene.getStylesheets().add(this.getClass().getResource("CharacterSheet.css").toExternalForm());

        stage.setScene(scene);
        int row = 1;
        Text characterName = new Text(c.getName());
        characterName.setId("characterName");
        grid.add(characterName,1,0,2,1);

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

    //////////////// Stats ///////////////////
    Label str = new Label("Str:");
    Label con = new Label("Cons:");
    Label dex = new Label("Dex:");
    Label intel = new Label("Int:");
    Label wis = new Label("Wis:");
    Label cha = new Label("Char:");
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

    HBox stathb0 = new HBox(10);
    HBox stathb1 = new HBox(10);
    HBox stathb2 = new HBox(10);
    stathb0.getChildren().addAll(armor,armorTf,init,initTf,speed,speedTf,hitdice,hdTf);
    stathb1.getChildren().addAll(str,strTf,con,conTf,dex,dexTf);
    stathb2.getChildren().addAll(intel,intelTf,wis,wisTf,cha,chaTf);



    VBox statvb = new VBox(10);
    statvb.setAlignment(Pos.CENTER);
    statvb.getChildren().addAll(stathb0,stathb1,stathb2);

    grid.add(statvb,1,row);

    row++;

    int statsRow = row;

    ToggleButton statbtn = new ToggleButton("Edit Stats");
    grid.add(statbtn,1,row);
    statbtn.setOnAction(new EventHandler<ActionEvent>() {
        int previousStr = c.getStr();
        int previousCon = c.getCons();
        int previousDex = c.getDex();
        int previousInt = c.getInt();
        int previousWis = c.getWis();
        int previousCha = c.getChar();
        int previousArmor = c.getArmor();
        int previousInit = c.getInitiative();
        int previousSpeed = c.getSpeed();
        String previousHitDice = c.getHitDice();
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
                previousArmor = c.getArmor();
                previousInit = c.getInitiative();
                previousSpeed = c.getSpeed();
                previousHitDice = c.getHitDice();
                strTf.setEditable(true);
                conTf.setEditable(true);
                dexTf.setEditable(true);
                intelTf.setEditable(true);
                wisTf.setEditable(true);
                chaTf.setEditable(true);
                armorTf.setEditable(true);
                initTf.setEditable(true);
                speedTf.setEditable(true);
                hdTf.setEditable(true);
                strTf.setId("unlocked-tf");
                conTf.setId("unlocked-tf");
                dexTf.setId("unlocked-tf");
                intelTf.setId("unlocked-tf");
                wisTf.setId("unlocked-tf");
                chaTf.setId("unlocked-tf");
                armorTf.setId("unlocked-tf");
                initTf.setId("unlocked-tf");
                speedTf.setId("unlocked-tf");
                hdTf.setId("unlocked-tf");
            }
            else {
                strTf.setEditable(false);
                conTf.setEditable(false);
                dexTf.setEditable(false);
                intelTf.setEditable(false);
                wisTf.setEditable(false);
                chaTf.setEditable(false);
                armorTf.setEditable(false);
                initTf.setEditable(false);
                speedTf.setEditable(false);
                hdTf.setEditable(false);
                strTf.setId("locked-tf");
                conTf.setId("locked-tf");
                dexTf.setId("locked-tf");
                intelTf.setId("locked-tf");
                wisTf.setId("locked-tf");
                chaTf.setId("locked-tf");
                armorTf.setId("locked-tf");
                speedTf.setId("locked-tf");
                initTf.setId("locked-tf");
                hdTf.setId("locked-tf");
                boolean strIsInt = isInteger(strTf.getText());
                boolean conIsInt = isInteger(conTf.getText());
                boolean dexIsInt = isInteger(dexTf.getText());
                boolean intelIsInt = isInteger(intelTf.getText());
                boolean wisIsInt = isInteger(wisTf.getText());
                boolean chaIsInt = isInteger(chaTf.getText());
                boolean armorIsInt = isInteger(armorTf.getText());
                boolean speedIsInt = isInteger(speedTf.getText());
                boolean initIsInt = isInteger(initTf.getText());
                if (strIsInt && conIsInt && dexIsInt && intelIsInt && wisIsInt && chaIsInt && armorIsInt && speedIsInt && initIsInt) {
                    c.setStr(Integer.parseInt(strTf.getText()));
                    c.setCons(Integer.parseInt(conTf.getText()));
                    c.setDex(Integer.parseInt(dexTf.getText()));
                    c.setInt(Integer.parseInt(intelTf.getText()));
                    c.setWis(Integer.parseInt(wisTf.getText()));
                    c.setChar(Integer.parseInt(chaTf.getText()));
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
                    strTf.setText(Integer.toString(previousStr));
                    conTf.setText(Integer.toString(previousCon));
                    dexTf.setText(Integer.toString(previousDex));
                    intelTf.setText(Integer.toString(previousInt));
                    wisTf.setText(Integer.toString(previousWis));
                    chaTf.setText(Integer.toString(previousCon));
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
    Button featsBtn = new Button("Features");

    HBox hbbtns1 = new HBox(10);
    hbbtns1.setAlignment(Pos.CENTER);
    hbbtns1.getChildren().addAll(skillsBtn,spellsBtn,inventoryBtn);
    HBox hbbtns2 = new HBox(10);
    hbbtns2.setAlignment(Pos.CENTER);
    hbbtns2.getChildren().addAll(idealsBtn,descriptionBtn,featsBtn);

    VBox vbbtns = new VBox(10);
    vbbtns.setAlignment(Pos.CENTER);
    vbbtns.getChildren().addAll(hbbtns1,hbbtns2);
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
            skillsTitle.setId("skillsTitle");
            VBox vbSkills = new VBox(10);

            HashSet<String> skillsList = c.getSkills();
            Iterator<String> itr = skillsList.iterator();
            int skrow = 1;
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
                        Scene rmscene = new Scene(rmgrid);
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



                skrow++;
            }
            
            Button addSkills = new Button("Add Skill");
            Button doneSkills = new Button("Done");
            HBox hbskills = new HBox(10);
            hbskills.getChildren().addAll(addSkills,doneSkills);
            BorderPane bpSkills = new BorderPane();
            bpSkills.setPadding(new Insets(20));
            bpSkills.setMargin(skillsTitle,new Insets(12,12,12,12));
            bpSkills.setMargin(vbSkills,new Insets(10,10,10,10));
            bpSkills.setTop(skillsTitle);
            bpSkills.setCenter(vbSkills);
            bpSkills.setBottom(hbskills);


            Scene skillsscene = new Scene(bpSkills);
            skillsscene.getStylesheets().add(this.getClass().getResource("SkillsPage.css").toExternalForm());

            skillsStage.setScene(skillsscene);

            //////// Add Skills //////////
            addSkills.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Stage addSkillsStage = new Stage();
                    addSkillsStage.setTitle("Add Skill");
                    GridPane addgrid = new GridPane();
                    addgrid.setAlignment(Pos.CENTER);
                    addgrid.setHgap(10);
                    addgrid.setVgap(10);
                    Scene addscene = new Scene(addgrid);
                    addSkillsStage.setScene(addscene);
                    
                    addSkillsStage.show();

                    Label addLabel = new Label("Add a skill?");
                    addgrid.add(addLabel,0,0);
                    TextField addTf = new TextField();
                    addTf.setPromptText("Input Skill Name");
                    addgrid.add(addTf,0,1);

                    Button addOk = new Button("Add");
                    Button noOk = new Button("Cancel");
                    HBox hbadd= new HBox(10);
                    hbadd.getChildren().addAll(addOk,noOk);
                    addgrid.add(hbadd,0,2);
                    
                    addOk.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            skillsList.add(addTf.getText());
                            c.setSkills(skillsList);
                            addSkillsStage.close();
                            Label newSkills = new Label(addTf.getText());
                            vbSkills.getChildren().add(newSkills);
                            addSkillsStage.close();
                        }
                    });
                    noOk.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            addSkillsStage.close();
                        }
                    });
                        


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
            inventoryTitle.setId("inventoryTitle");
            VBox vbInventory = new VBox(10);

            ArrayList<String> inventoryList = c.getInventory();
            Iterator<String> itr = inventoryList.iterator();
            int skrow = 1;
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
                        Scene rmscene = new Scene(rmgrid);
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



                skrow++;
            }
            
            Button addItem = new Button("Add Item");
            Button doneItem = new Button("Done");
            HBox hbinventory = new HBox(10);
            hbinventory.getChildren().addAll(addItem,doneItem);
            BorderPane bpInventory = new BorderPane();
            bpInventory.setPadding(new Insets(20));
            bpInventory.setMargin(inventoryTitle,new Insets(12,12,12,12));
            bpInventory.setMargin(vbInventory,new Insets(10,10,10,10));
            bpInventory.setTop(inventoryTitle);
            bpInventory.setCenter(vbInventory);
            bpInventory.setBottom(hbinventory);


            Scene inventoryscene = new Scene(bpInventory);
            inventoryscene.getStylesheets().add(this.getClass().getResource("InventoryPage.css").toExternalForm());

            inventoryStage.setScene(inventoryscene);

            //////// Add Skill //////////
            addItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Stage addInventoryStage = new Stage();
                    addInventoryStage.setTitle("Add Item");
                    GridPane addgrid = new GridPane();
                    addgrid.setAlignment(Pos.CENTER);
                    addgrid.setHgap(10);
                    addgrid.setVgap(10);
                    Scene addscene = new Scene(addgrid);
                    addInventoryStage.setScene(addscene);
                    
                    addInventoryStage.show();

                    Label addLabel = new Label("Add an tem?");
                    addgrid.add(addLabel,0,0);
                    TextField addTf = new TextField();
                    addTf.setPromptText("Input Item Name");
                    addgrid.add(addTf,0,1);

                    Button addOk = new Button("Add");
                    Button noOk = new Button("Cancel");
                    HBox hbadd= new HBox(10);
                    hbadd.getChildren().addAll(addOk,noOk);
                    addgrid.add(hbadd,0,2);
                    
                    addOk.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            inventoryList.add(addTf.getText());
                            c.setInventory(inventoryList);
                            addInventoryStage.close();
                            Label newInv = new Label(addTf.getText());
                            vbInventory.getChildren().add(newInv);
                            addInventoryStage.close();
                        }
                    });
                    noOk.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            addInventoryStage.close();
                        }
                    });
                        


                }
            });

            doneItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    c.setInventory(inventoryList);
                    inventoryStage.close();
                }
            });

            inventoryStage.show();

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
