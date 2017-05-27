/** written by
    Seth Roffe

    Character class for a Dungeons and dragons game

    v0.1: Basic stuff. character stats 
*/
package com.mkyong.json;

import java.util.*;
import java.io.*;

import com.google.gson.Gson;


public class Character {
    //about character
    private String name;
    private String race;
    private String subrace;
    private String clss; //class
    private char gender;
    private String weapon;

    //health and magic
    private int maxHP;
    private int currentHP;
    private int maxMP;
    private int currentMP;

    private int level;
    private int exp;

    //stats
    private int armor;
    private int initiative;
    private int speed;
    private int hitdice;
    
    private int strength;
    private int constitution;
    private int dexterity;
    private int intelligence;
    private int wisdom;
    private int charisma;
    

    //HashSets
    private HashSet<String> cantrips;
    private HashSet<String> spells;
    private HashSet<String> skills;

    private HashSet<String> features;

    private ArrayList<String> inventory;

    //////////////////////////
    /////// INSTANCES ////////
    //////////////////////////
    
    public Character(String name, String clss, String race, String subrace, char gender,
		     String weapon, HashSet<String> cantrips, HashSet<String> spells, HashSet<String> skills,
		     int maxHealth, int maxMP, int armor, int initiative, int speed, int hitdice,
		     int str, int cons, int dex, int intel, int wis, int charis, int level, int exp,
		     ArrayList<String> inventory, HashSet<String> features) {
	
	setName(name);
	setClss(clss);
	setRace(race);
	setSubrace(subrace);
	setGender(gender);
	setWeapon(weapon);
	setCantrips(cantrips);
	setSpells(spells);
	setSkills(skills);
	setMaxHP(maxHealth);
	setMaxMP(maxMP);
	setArmor(armor);
	setInitiative(initiative);
	setSpeed(speed);
	setStr(str);
	setCons(cons);
	setDex(dex);
	setInt(intel);
	setWis(wis);
	setChar(charis);
	setInventory(inventory);
	setCurrentHP(maxHealth);
	setHitDice(hitdice);

	setLevel(level);
	setExp(exp);

	setFeatures(features);
}
    
        public Character() {
	setName("");
	setClss("");
	
	setStr(0);
	setCons(0);
	setDex(0);
	setInt(0);
	setWis(0);
	setChar(0);

	setInitiative(0);
	setArmor(0);
	setSpeed(0);
	
	setMaxHP(0);
	setMaxMP(0);

	setHitDice(0);
	
	setRace("");
	setSubrace("");
	setGender('m');
	setWeapon("");

	setSkills(new HashSet<String>());

	setCantrips(new HashSet<String>());
	setSpells(new HashSet<String>());
	setFeatures(new HashSet<String>());

	setInventory(new ArrayList<String>());

	setLevel(1);
	setExp(0);
	
	setCurrentHP(0);
	
    }

    //just a name
    public Character(String name,String clss) {
	setName(name);
	setClss(clss);
	
	setStr(0);
	setCons(0);
	setDex(0);
	setInt(0);
	setWis(0);
	setChar(0);

	setInitiative(0);
	setArmor(0);
	setSpeed(0);

	setHitDice(0);
	setLevel(1);
	setExp(0);
	
	setMaxHP(0);
	setMaxMP(0);

	setRace("");
	setSubrace("");
	setGender('m');
	setWeapon("");

	setSkills(new HashSet<String>());
	
	setCantrips(new HashSet<String>());
	setSpells(new HashSet<String>());
	setFeatures(new HashSet<String>());

	setInventory(new ArrayList<String>());

	setCurrentHP(0);
    }

    // Copy a character instance
    public Character(Character c) {
	setName(c.getName());
	setRace(c.getRace());
	setClss(c.getClss());
	setSubrace(c.getSubrace());
	setGender(c.getGender());
	setWeapon(c.getWeapon());
	setArmor(c.getArmor());
	setCantrips(c.getCantrips());
	setSpells(c.getSpells());
	setSkills(c.getSkills());
	setMaxHP(c.getMaxHP());
	setMaxMP(c.getMaxMP());
	setInitiative(c.getInitiative());
	setSpeed(c.getSpeed());
	setHitDice(c.getHitDice());
	setStr(c.getStr());
	setCons(c.getCons());
	setDex(c.getDex());
	setInt(c.getInt());
	setWis(c.getWis());
	setChar(c.getChar());
	setLevel(c.getLevel());
	setExp(c.getExp());
	setInventory(c.getInventory());
	setCurrentHP(c.getCurrentHP());

	setFeatures(c.getFeatures());
    }
	
    public Character(String name, String clss, int maxHealth, int armor, int initiative, int speed,
		     int str, int cons, int dex, int intel, int wis, int charis) {
	setName(name);
	setClss(clss);
	setMaxHP(maxHealth);
	setArmor(armor);
	setInitiative(initiative);
	setSpeed(speed);
	setStr(str);
	setCons(cons);
	setDex(dex);
	setInt(intel);
	setWis(wis);
	setChar(charis);

	setRace("");
	setSubrace("");
	setGender('m');
	
	setCantrips(new HashSet<String>());
	setSpells(new HashSet<String>());
	setSkills(new HashSet<String>());
	setFeatures(new HashSet<String>());
	
	setWeapon("");

	setLevel(1);
	setExp(0);
	
	setMaxMP(0);
	setHitDice(0);
	setInventory(new ArrayList<String>());
	setCurrentHP(maxHealth);
    }


    /////////////////////////////////
    ////// SET AND GET METHODS //////
    /////////////////////////////////
    
    public void setName(String n) {
	this.name = n;
    }

    public String getName() {
	return name;
    }

    public void setLevel(int l) {
	this.level = l;
    }
    public int getLevel() {
	return level;
    }

    public void setExp(int e) {
	this.exp = e;
    }
    public int getExp() {
	return exp;
    }
    
    public void setClss(String c) {
	this.clss = c;
    }
    public String getClss() {
	return clss;
    }
    
    public void setRace(String r) {
	this.race = r;
    }
    public String getRace() {
	return race;
    }

    public void setSubrace(String sr) {
	this.subrace = sr;
    }
    public String getSubrace() {
	return subrace;
    }

    public void setGender(char g) {
	this.gender = g;
    }
    public char getGender() {
	return gender;
    }

    public void setWeapon(String w) {
	this.weapon = w;
    }
    public String getWeapon() {
	return weapon;
    }

    public void setMaxHP(int mHP) {
	this.maxHP = mHP;
    }
    public int getMaxHP() {
	return maxHP;
    }

    public void setCurrentHP(int cHP) {
	this.currentHP = cHP;
    }
    public int getCurrentHP() {
	return currentHP;
    }

    public void setMaxMP(int mMP) {
	this.maxMP = mMP;
    }
    public int getMaxMP() {
	return maxMP;
    }

    public void setCurrentMP(int cMP) {
	this.currentMP = cMP;
    }
    public int getCurrentMP() {
	return currentMP;
    }

    public void setArmor(int a) {
	this.armor = a;
    }
    public int getArmor() {
	return armor;
    }

    public void setInitiative(int i) {
	this.initiative = i;
    }
    public int getInitiative() {
	return initiative;
    }

    public void setSpeed(int s) {
	this.speed = s;
    }
    public int getSpeed() {
	return speed;
    }

    public void setHitDice(int d) {
	this.hitdice = d;
    }
    public int getHitDice() {
	return hitdice;
    }

    public void setStr(int s) {
	this.strength = s;
    }
    public int getStr() {
	return strength;
    }

    public void setCons(int c) {
	this.constitution = c;
    }
    public int getCons() {
	return constitution;
    }

    public void setDex(int d) {
	this.dexterity = d;
    }
    public int getDex() {
	return dexterity;
    }

    public void setInt(int i) {
	this.intelligence = i;
    }
    public int getInt() {
	return intelligence;
    }

    public void setWis(int w) {
	this.wisdom = w;
    }
    public int getWis() {
	return wisdom;
    }

    public void setChar(int c) {
	this.charisma = c;
    }
    public int getChar() {
	return charisma;
    }

    public void setCantrips(HashSet<String> c) {
	this.cantrips = c;
    }
    public HashSet<String> getCantrips() {
	return cantrips;
    }

    public void setSpells(HashSet<String> s) {
	this.spells = s;
    }
    public HashSet<String> getSpells() {
	return spells;
    }

    public void setSkills(HashSet<String> s) {
	this.skills = s;
    }
    public HashSet<String> getSkills() {
	return skills;
    }

    public void setFeatures(HashSet<String> f) {
	this.features = f;
    }
    public HashSet<String> getFeatures() {
	return features;
    }
    
    public void setInventory(ArrayList<String> i) {
	this.inventory = i;
    }
    public ArrayList<String> getInventory() {
	return inventory;
    }
    
    public void clearInventory() {
	this.inventory = new ArrayList<String>();
    }

    ///////////////////////////////
    /////// OTHER FUNCTIONS ///////
    ///////////////////////////////
    
    public void addToInventory(String item) {
	this.inventory.add(item);
    }

    public void removeFromInventory(String item) {
	this.inventory.remove(item);
    }
    public int inventorySize() {
	return inventory.size();
    }

    public void addLevel() {
	this.level++;
    }
    
    public void addFeature(String item) {
	this.features.add(item);
    }

    public void removeFeature(String item) {
	this.features.remove(item);
    }

    public void addExp(int n) {
	this.exp = this.exp + n;
    }
    
    
    
    ///////////////////////////////////////
    ///////////////////////////////////////
    
    public String printCharacter() {
	return "Character: " + name + "level " + level + " [Class=" + clss + ", race=" + race + ", subrace=" + subrace + ", gender=" + gender +
	    ", weapon=" + weapon + ", maxHP=" + maxHP + ", currentHP=" + currentHP + ", maxMP=" + maxMP +
	    ", currentMP=" + currentMP + ", armor class=" + armor + ", initiative=" + initiative +
	    ", speed=" + speed + ", hitdice=" + hitdice + ", strength=" + strength + ", constitution=" + constitution +
	    ", dexterity=" + dexterity + ", intelligence=" + intelligence + ", wisdom=" + wisdom +
	    ", charisma=" + charisma + ", cantrips =" + cantrips + ", spells=" + spells +
	    ", inventory=" + Arrays.toString(inventory.toArray()) + "]";
    }


    /////////////////////////////
    /////// SAVE FUNCTION ///////
    /////////////////////////////

    //public void Save Function() {
}
