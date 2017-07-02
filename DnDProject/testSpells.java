
import com.google.gson.*;
import java.util.*;
import java.io.*;

public class testSpells {

    public static void main(String[] args) {

       Gson gson = new Gson();

       try {
           BufferedReader br = new BufferedReader(new FileReader("spells_data.json"));
           DataObject obj = gson.fromJson(br,DataObject.class);
           System.out.println(obj);

       }
       catch (IOException e) {
           e.printStackTrace();
       }
    }
}
        
