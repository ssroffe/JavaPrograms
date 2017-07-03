/** written by
    Seth Roffe

    Spell Class for Dungeons and Dragons 5e
*/

package dnd;

import java.util.*;
import java.io.*;

public class Spell {
    private String school;
    private String name;
    private String level;
    private String ritual;
    private String casting_time;
    private ArrayList<String> clss;
    private String range;
    private String components;
    private String duration;
    private String concentration;
    private String page;

    // Constructors
    public Spell(String school, String name, String level, String ritual, String casting_time, ArrayList<String> clss,
            String range, String components, String duration, String concentration, String page) {
        setSchool(school);
        setName(name);
        setLevel(level);
        setRitual(ritual);
        setCastingTime(casting_time);
        setClss(clss);
        setRange(range);
        setComponents(components);
        setDuration(duration);
        setConcentration(concentration);
        setPage(page);
    }

    public Spell(String name) {
        setName(name);
        setSchool("");
        setLevel("");
        setRitual("");
        setCastingTime("");
        setClss(new ArrayList<String>());
        setRange("");
        setComponents("");
        setDuration("");
        setConcentration("");
        setPage("");
    }

    public Spell() {
        setSchool("");
        setName("");
        setLevel("");
        setRitual("");
        setCastingTime("");
        setClss(new ArrayList<String>());
        setRange("");
        setComponents("");
        setDuration("");
        setConcentration("");
        setPage("");
    }
    public Spell(Spell s) {
        setSchool(s.getSchool());
        setName(s.getName());
        setLevel(s.getLevel());
        setRitual(s.getRitual());
        setCastingTime(s.getCastingTime());
        setClss(s.getClss());
        setRange(s.getRange());
        setComponents(s.getComponents());
        setDuration(s.getDuration());
        setConcentration(s.getConcentration());
        setPage(s.getPage());
    }

    // Set and Get Methods
    public void setSchool(String s) {
        this.school = s;
    }
    public String getSchool() {
        return school;
    }

    public void setName(String n) {
        this.name = n;
    }
    public String getName() {
        return name;
    }

    public void setLevel(String l) {
        this.level = l;
    }
    public String getLevel() {
        return level;
    }

    public int getLevelInt() {
        String lvl = this.level;
        
        if (lvl.equalsIgnoreCase("Cantrip")) {
            return 0;
        }
        else if (lvl.equalsIgnoreCase("1st")) {
            return 1;
        }
        else if (lvl.equalsIgnoreCase("2nd")) {
            return 2;
        }
        else if (lvl.equalsIgnoreCase("3rd")) {
            return 3;
        }
        else if (lvl.equalsIgnoreCase("4th")) {
            return 4;
        }
        else if (lvl.equalsIgnoreCase("5th")) {
            return 5;
        }
        else if (lvl.equalsIgnoreCase("6th")) {
            return 6;
        }
        else if (lvl.equalsIgnoreCase("7th")) {
            return 7;
        }
        else {
            return 8;
        }
    }

    public void setRitual(String r) {
        this.ritual = r;
    }
    public String getRitual() {
        return ritual;
    }
    public boolean getRitualTruth() {
        if (this.ritual.equalsIgnoreCase("yes")) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setCastingTime(String ct) {
        this.casting_time = ct;
    }
    public String getCastingTime() {
        return casting_time;
    }

    public void setClss(ArrayList<String> c) {
        this.clss = c;
    }
    public ArrayList<String> getClss() {
        return clss;
    }

    public void setRange(String r) {
        this.range = r;
    }
    public String getRange() {
        return range;
    }

    public void setComponents(String c) {
        this.components = c;
    }
    public String getComponents() {
        return components;
    }

    public void setDuration(String d) {
        this.duration = d;
    }
    public String getDuration() {
        return duration;
    }

    public void setConcentration(String c) {
        this.concentration = c;
    }
    public String getConcentration() {
        return concentration;
    }
    public boolean getConcentrationTruth() {
        if (this.concentration.equalsIgnoreCase("yes")) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setPage(String p) {
        this.page = p;
    }
    public String getPage() {
        return page;
    }


    public String printSpell() {
        return "Spell: " + name + "level: " + level + " " + getLevelInt() + "\n" + 
}
