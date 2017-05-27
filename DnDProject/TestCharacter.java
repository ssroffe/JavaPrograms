/** Test Character saving
 */


import java.util.*;
import dnd.Character;

public class TestCharacter {

    public static void main(String[] args) {

	Character milo = Character.LoadCharacter("milo");

	System.out.println(milo.getName());
	System.out.println(milo.getClss());
	
    }
}
