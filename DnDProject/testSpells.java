
import com.google.gson.*;
import dnd.Spell;
import java.util.*;
import java.io.*;

public class testSpells {

    public static void main(String[] args) {

        Gson gson = new Gson();
        Spell spell = new Spell();

       
        File f = new File("spell_data.json");
        
        try (Reader reader = new FileReader("spell_data.json")) {
            spell = gson.fromJson(reader, Spell.class);
            spell.printSpell();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
}
        
