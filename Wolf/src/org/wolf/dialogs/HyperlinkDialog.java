package org.wolf.dialogs;

import java.awt.Frame;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class HyperlinkDialog 
{
	String hyperlink = "";
	
	public HyperlinkDialog(String oldHyperlink)
	{
		Frame frame = JOptionPane.getRootFrame();
		String hyperlink = JOptionPane.showInputDialog(frame, "Enter a hyperlink (or leave field blank)", oldHyperlink);
		if (hyperlink == null || hyperlink.isEmpty())
		{
			JOptionPane.showMessageDialog(frame, "Previous Hyperlink erased");
			return;
		}
		else 
		{
			hyperlink = verifyHyperlink(hyperlink);
			if (hyperlink.isEmpty())
			{
				JOptionPane.showMessageDialog(frame, "Invalid hyperlink");
				throw new IllegalArgumentException();
			}
		}
		this.hyperlink = hyperlink;
	}
	    
	public String getHyperlink()
	{
		return hyperlink;
	}

    /** Verify if string ends with a valid hyperlink. Return null if no. */
    private String verifyHyperlink(String target)
    {
    	String http = "((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)";
    	Pattern pattern = Pattern.compile(http);
    	Matcher matcher = pattern.matcher(target);
    	if (matcher.find()) 
    	{
    		int start = matcher.start();
    		int end = matcher.end();
    		if (end != target.length())  return "";
    		if (start != 0) return "";
    		return target.substring(start);
    	}
    	return "";
    }
}
