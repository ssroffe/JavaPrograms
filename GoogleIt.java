import java.net.*;
import java.awt.Desktop;
import java.util.*;

public class GoogleIt
{
    public static void main(String[] args)
    {
        String[] input = new String[args.length];
	if (args.length == 0){
	    String str = "https://www.google.com/";
	    openWebpage(buildUri(str));
		}
	else {
	    input[0] = args[0];
	    for(int i = 1; i < args.length; i++)
		{
		    input[i] = input[i-1] + ' ' + args[i];
		}
	    String str = "https://www.google.com/search?q=" + scrubString(input[args.length-1]);
	    openWebpage(buildUri(str));
	}
    }
    
    public static String scrubString(String input)
    {
        List<String> url = new ArrayList<String>();
        url.add(input);
        url.add(url.get(url.size() - 1).replace("%","%25"));
        url.add(url.get(url.size() - 1).replace("\"","%22"));
        url.add(url.get(url.size() - 1).replace("!","%21"));
        url.add(url.get(url.size() - 1).replace("#","%23"));
        url.add(url.get(url.size() - 1).replace("$","%24"));
        url.add(url.get(url.size() - 1).replace("&","%26"));
        url.add(url.get(url.size() - 1).replace("\'","%27"));
        url.add(url.get(url.size() - 1).replace("(","%28"));
        url.add(url.get(url.size() - 1).replace(")","%29"));
        url.add(url.get(url.size() - 1).replace("*","%2A"));
        url.add(url.get(url.size() - 1).replace("+","%2B"));
        url.add(url.get(url.size() - 1).replace(",","%2C"));
        url.add(url.get(url.size() - 1).replace("-","%2D"));
        url.add(url.get(url.size() - 1).replace(".","%2E"));
        url.add(url.get(url.size() - 1).replace("/","%2F"));
        url.add(url.get(url.size() - 1).replace(":","%3A"));
        url.add(url.get(url.size() - 1).replace(";","%3B"));
        url.add(url.get(url.size() - 1).replace("<","%3C"));
        url.add(url.get(url.size() - 1).replace("=","%3D"));
        url.add(url.get(url.size() - 1).replace(">","%3E"));
        url.add(url.get(url.size() - 1).replace("?","%3F"));
        url.add(url.get(url.size() - 1).replace("@","%40"));
        url.add(url.get(url.size() - 1).replace("[","%5B"));
        url.add(url.get(url.size() - 1).replace("\\","%5C"));
        url.add(url.get(url.size() - 1).replace("]","%5D"));
        url.add(url.get(url.size() - 1).replace("^","%5E"));
        url.add(url.get(url.size() - 1).replace("_","%5F"));
        url.add(url.get(url.size() - 1).replace("`","%60"));
        url.add(url.get(url.size() - 1).replace("{","%7B"));
        url.add(url.get(url.size() - 1).replace("|","%7C"));
        url.add(url.get(url.size() - 1).replace("}","%7D"));
        url.add(url.get(url.size() - 1).replace("~","%7E"));
        url.add(url.get(url.size() - 1).replace(" ","+"));
        
        return url.get(url.size()-1);
    }
    
    public static URI buildUri(String str)
    {
        URL url = null;
        URI uri = null;
        try{
            url = new URL(str);
            }
        catch (Exception e)
        {
            System.out.println("Error 1");
        }
        try{
            uri = url.toURI();
            }
        catch (Exception e)
        {
            System.out.println("Error 2");
        }
        return uri;

    }
    
    public static void openWebpage(URI uri) {
    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
        try {
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
